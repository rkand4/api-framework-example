package net.nosegrind.apiframework

import java.io.Serializable;
import groovy.transform.ToString
import org.bson.types.ObjectId

//@ToString(includeNames = true, includeFields = true)
class Role{

	static mapWith = "mongo"

	// MongoDB
	//private static final long serialVersionUID = 1
	//ObjectId id
	String authority

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		//datasource 'user'
		cache true
	}

}
