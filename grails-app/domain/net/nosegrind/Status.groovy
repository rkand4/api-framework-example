package net.nosegrind

//import org.bson.types.ObjectId

//@Typed(TypePolicy.MIXED)
class Status {

	//static mapWith = "mongo"
	static hasMany = [posts: Post ]



	//ObjectId id
	String statName
	
    static constraints = {
		statName(size:2..255, nullable:false, blank:false,unique:true)
    }
}
