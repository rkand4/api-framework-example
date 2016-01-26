package net.nosegrind

import groovy.transform.ToString
import org.bson.types.ObjectId

//@ToString(includeNames = true, includeFields = true)
class PostTopic implements Serializable{

	static mapWith = "mongo"
	static belongsTo = [Post,Topic]

	Post post
	Topic topic
	
    static constraints = {
		post(nullable:false, blank:false)
		topic(nullable:false, blank:false)
    }

	static mapping = {
		cache true
	}
}
