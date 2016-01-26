package net.nosegrind

import java.io.Serializable;
import groovy.transform.ToString
import org.bson.types.ObjectId

//@ToString(includeNames = true, includeFields = true)
class Section implements Serializable{

	static mapWith = "mongo"
	static hasMany = [ posts : Post ]

	String sectionName
	Boolean commentsAllowed = true
	
    static constraints = {
		sectionName(size:2..65, nullable:false, blank:false,unique:true)
		commentsAllowed(nullable:false, blank:false)
    }

	static mapping = {
		cache true
	}
}
