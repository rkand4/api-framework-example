package net.nosegrind

import groovy.transform.ToString
import org.bson.types.ObjectId

@ToString(includeNames = true, includeFields = true)
class Status {

	static mapWith = "mongo"
	static hasMany = [posts: Post ]

	String statName
	
    static constraints = {
		statName(size:2..255, nullable:false, blank:false,unique:true)
    }

	static mapping = {
		cache true
	}
}
