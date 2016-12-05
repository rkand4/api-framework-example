package net.nosegrind.apiframework

import org.grails.core.DefaultGrailsControllerClass
//import net.nosegrind.apiframework.Method
import grails.util.Metadata

class ApidocController {

	def apiToolkitService
	def apiCacheService
	def springSecurityService
	def apiObjectService
	
	def index(){
		redirect(action:'show')
	}
	
	def show(){
		Map docs = [:]
		String authority = springSecurityService.principal.authorities*.authority[0]
		authority = (authority=='ROLE_ANONYMOUS')?'permitAll':authority

		String entryPoint = "v${Metadata.current.getProperty(Metadata.APPLICATION_VERSION, String.class)}"

		grailsApplication.controllerClasses.each { DefaultGrailsControllerClass controllerClass ->
			String controllername = controllerClass.logicalPropertyName

			def cache = apiCacheService.getApiCache(controllername)

			if(cache){
				cache[params.apiObject].each() { it ->
					if (!['deprecated', 'defaultAction', 'currentStable'].contains(it.key)) {
						if(!docs["${controllername}"]){
							docs["${controllername}"] =[:]
						}
						String action = it.key
						// generateDoc with authority as arg

						// TODO: cleanup ApiDescriptor
						//println(cache["${params.apiObject}"]["${action}"])
						//println(apiObjectService.convertApiDescriptor(cache["${params.apiObject}"]["${action}"]))

						docs["${controllername}"]["${action}"] = apiObjectService.convertApiDescriptor(cache["${params.apiObject}"]["${action}"])
					}
				}
			}
		}

		return ['apidoc':docs]
	}

}

