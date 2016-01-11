package net.nosegrind.apiframework

import java.util.Date
import groovy.transform.ToString
import org.bson.types.ObjectId

@ToString(includeNames = true, includeFields = true)
class HookRole {

	static mapWith = "mongo"

	Hook hook
	Role role
	Date dateCreated
	Date lastModified = new Date()

	static mapping = {
		//datasource 'user'
	}
	
	static constraints = {
		hook(nullable:false)
		role(nullable:false)
	}
}