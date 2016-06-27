package net.nosegrind.apiframework

import groovy.transform.ToString

@ToString(includeNames = true, includeFields = true)
class AuthenticationToken implements Serializable{

    static mapWith = "mongo"

    String tokenValue
    String username

    static mapping = {
        cache true
        version false
    }

}
