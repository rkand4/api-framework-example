package net.nosegrind.apiframework


class Rolee{

	// MongoDB
	//private static final long serialVersionUID = 1

	String authority

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		datasource 'user'
		//cache true
	}

}
