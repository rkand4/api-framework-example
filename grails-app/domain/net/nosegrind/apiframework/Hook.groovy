package net.nosegrind.apiframework

import java.util.Date
import groovy.transform.ToString
import org.bson.types.ObjectId

@ToString(includeNames = true, includeFields = true)
class Hook {

	static mapWith = "mongo"

	Person user
	String name
	String url
	String format = 'JSON'
	String service
	Long attempts = 0
	Boolean isEnabled = true
	Date dateCreated
	Date lastModified = new Date()

	static mapping = {
		//datasource 'user'
	}
	
	static constraints = {
		user(nullable:false)
		name(nullable:false,maxSize:200)
		url(nullable:false)
		format(nullable:false)
		service(nullable:false)
		attempts(nullable:false)
	}
}
