package net.nosegrind

import java.io.Serializable;
import groovy.transform.ToString
import org.bson.types.ObjectId

//@ToString(includeNames = true, includeFields = true)
class Topic implements Serializable{

	static mapWith = "mongo"
	static hasMany = [ posts : PostTopic ]

	String topicName
	
    static constraints = {
		topicName(size:2..65, nullable:false, blank:false,unique:true)
    }
}
