package net.nosegrind

//import java.util.Date;

//import javax.servlet.http.HttpServletResponse


class SectionController {


	static defaultAction = 'list'

	def list(){
		def list = Section.list()
		return ['section':list]
	}
	
	def show(){
		def section = Section.get(params.id.toLong())
		if(section){
			return ['section':section]
		}
		return null
	}
	
	def create(){
		Section sectionInstance = new Section()
		sectionInstance.sectionName = params.sectionName
		sectionInstance.commentsAllowed = true

		if (!sectionInstance.save(flush:true)) {
			sectionInstance.errors.allErrors.each { println it }
			response.status = 400
			response.setHeader('ERROR','Could not save Section. Please check your data and try again.')
		}else{
			Section section = Section.get(sectionInstance.id)
			println section
            return ['section':section]
		}
	}

	def update(){
		Section sectionInstance = Section.get(params.id.toLong())
		long version = sectionInstance.version
		
		if(params?.version){
			if (version!=params.version.toLong()) {
				//render(status:HttpServletResponse.SC_BAD_REQUEST, text: 'Another user has updated this Section while you were editing.')
			}
		}
		sectionInstance.sectionName = params.sectionName
		sectionInstance.sectionName = (!params?.commentsAllowed?.empty)?params.sectionName:true
		
		if (sectionInstance == null){
			//render(status:HttpServletResponse.SC_BAD_REQUEST)
		}

		if (sectionInstance.hasErrors()) {
			render(status:HttpServletResponse.SC_NOT_FOUND)
		}

		if(!sectionInstance.save(flush:true)){
			return false
		}else{
			//apiToolkitService.callHook('test',testInstance,'update')
			def section =  Section.get(sectionInstance.id.toLong())
			return ['section':section]
		}

		return null
	}

	def delete(Section sectionInstance){
		if (sectionInstance == null) {
			//render(status:HttpServletResponse.SC_NOT_FOUND)
		}

		sectionInstance.delete flush:true
		return null
	}
}
