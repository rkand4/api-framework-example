package net.nosegrind.apiframework

import java.io.Serializable;
import groovy.transform.ToString
import org.bson.types.ObjectId

import org.apache.commons.lang.builder.HashCodeBuilder
import org.springframework.http.HttpMethod

//@ToString(cache=true, includeNames=true, includePackage=false)
class RequestMap implements Serializable {

    static mapWith = "mongo"

    // MongoDB
    //private static final long serialVersionUID = 1

    //ObjectId id
    String configAttribute
    HttpMethod httpMethod
    String url


    static constraints = {
        configAttribute blank: false
        httpMethod nullable: true
        url blank: false, unique: 'httpMethod'
    }

    static mapping = {
        //datasource 'user'
        //cache true
    }
}
