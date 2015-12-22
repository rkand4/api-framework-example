import net.nosegrind.apiframework.ApiCacheService;
import net.nosegrind.apiframework.ApiObjectService;

//import grails.plugins.GrailsPluginManager
//import grails.plugins.GrailsPlugin
import net.nosegrind.apiframework.*

class BootStrap {

    def passwordEncoder
	def grailsApplication
	//ApiObjectService apiObjectService
	//ApiCacheService apiCacheService
	
    def init = { servletContext ->

        grailsApplication.config.apitoolkit.roles.each(){
            String currRole = it.toString()[0..-2]
            Role role = Role.findByAuthority(currRole)
            if(!role){
                role = new Role(authority:it.toString()[0..-2])
                role.save(flush:true,failOnError:true)
            }
        }

        Person user = Person.findByUsername("${grailsApplication.config.root.login}")
        PersonRole.withTransaction(){ status ->
            Role adminRole = Role.findByAuthority("ROLE_ADMIN")
            if(!user?.id){
                user = new Person(username:"${grailsApplication.config.root.login}",password:"${grailsApplication.config.root.password}",email:"${grailsApplication.config.root.email}")
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
