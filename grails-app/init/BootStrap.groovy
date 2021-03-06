import net.nosegrind.apiframework.ApiCacheService;
import net.nosegrind.apiframework.ApiObjectService;

//import grails.plugins.GrailsPluginManager
//import grails.plugins.GrailsPlugin
import net.nosegrind.apiframework.Person
import net.nosegrind.apiframework.Role
import net.nosegrind.apiframework.PersonRole

import net.nosegrind.*
import java.util.Date;

class BootStrap {

    def passwordEncoder
	def grailsApplication
	//ApiObjectService apiObjectService
	//ApiCacheService apiCacheService
	
    def init = { servletContext ->

        grailsApplication.config.apitoolkit.roles.each(){
            String currRole = it.toString()
            Role role = Role.findByAuthority(currRole)
            if(!role){
                //println(currRole)
                role = new Role(authority:currRole)
                role.save(flush:true,failOnError:true)
            }
        }

        Person user = Person.findByUsername("${grailsApplication.config.root.login}")
//Person user = Person.findByUsername("root")

        PersonRole.withTransaction(){ status ->
            Role adminRole = Role.findByAuthority("ROLE_ADMIN")
            if(!user?.id){
                user = new Person(username:"${grailsApplication.config.root.login}",password:"${grailsApplication.config.root.password}",email:"${grailsApplication.config.root.email}")
                //user = new Person(username:"root",password:"password",email:"orubel@nosegrind.net")
                if(!user.save(flush:true,failOnError:true)){
                    user.errors.allErrors.each { log.error it }
                }
            }else{
                if(!passwordEncoder.isPasswordValid(user.password, grailsApplication.config.root.password, null)){
                    log.error "Error: Bootstrapped Root Password was changed in config. Please update"
                }
            }

            if(!user?.authorities?.contains(adminRole)){
                PersonRole pRole = new PersonRole(user,adminRole)
                pRole.save(flush:true,failOnError:true)
            }

            status.isCompleted()
        }
/*
        new Section(sectionName:"News").save(flush:true,failOnError:true)
        new Section(sectionName:"General").save(flush:true,failOnError:true)
        new Section(sectionName:"Update").save(flush:true,failOnError:true)
        new Section(sectionName:"Development").save(flush:true,failOnError:true)

        new Topic(topicName:"Android").save(flush:true,failOnError:true)
        new Topic(topicName:"Spring").save(flush:true,failOnError:true)
        new Topic(topicName:"Grails").save(flush:true,failOnError:true)
        new Topic(topicName:"Groovy").save(flush:true,failOnError:true)


        Post post = new Post();

        post.title = 'Test Post'
        post.teaser = "This is just a test post to see if this works. Testing the api post system."
        post.content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In vel consequat nisl, quis commodo neque. Integer ultrices vitae nulla lacinia rutrum. Duis ut porta arcu, sed gravida tortor. Donec pulvinar elit turpis, ultricies tristique mi auctor ac. Ut elementum ullamcorper risus ac sollicitudin. Morbi semper ultrices enim vel euismod. Proin eleifend orci ac elit mollis tempor. Nulla egestas odio eu volutpat eleifend. Nunc nec massa eget nisl sodales posuere. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nunc accumsan pretium sapien a tincidunt. Sed at fringilla mi."
        post.section = Section.get(1)

        Date now = new Date();
        post.creationDate = now;
        post.modifiedDate = now;
        post.endCommentsDate = null

        post.author = user.id

        post.save(flush:true)



*/

		/*
		def plugins = pluginMngr.getAllPlugins()
		plugins.each{
			println(it)
		}
		*/
		//apiObjectService.initialize()
		//def test = apiCacheService.getCacheNames()

    }
    def destroy = {
    }
}
