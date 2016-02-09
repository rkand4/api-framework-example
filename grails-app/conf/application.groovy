import grails.util.Metadata

String apiVersion = Metadata.current.getApplicationVersion()
// fix for dots not working with spring security pathing
String entryPoint = "/v${apiVersion}".toString()
String batchEntryPoint = "b${apiVersion}"
String chainEntryPoint = "c${apiVersion}"
String metricsEntryPoint = "m${apiVersion}"
String domainEntryPoint = "d${apiVersion}"



// move to RequestMap once stabilized
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = true

//'JOINED_FILTERS,-securityContextPersistenceFilter,-logoutFilter,-authenticationProcessingFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-FilterSecurityInterceptor'

/*
grails.plugin.springsecurity.filterChain.chainMap = [
        '/**' : 'JOINED_FILTERS,-AbstractSecurityInterceptor,-securityContextPersistenceFilter,-logoutFilter,-authenticationProcessingFilter,-securityContextHolderAwareRequestFilter,-rememberMeAuthenticationFilter,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-FilterSecurityInterceptor'
]
*/

/*        "/${batchEntryPoint}/**" : 'none',
        "/${chainEntryPoint}/**" : 'none',
        "/${metricsEntryPoint}/**" : 'none',
        "/${domainEntryPoint}/**" : 'none'
*/


grails.plugin.springsecurity.userLookup.userDomainClassName = 'net.nosegrind.apiframework.Person'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'net.nosegrind.apiframework.PersonRole'
grails.plugin.springsecurity.authority.className = 'net.nosegrind.apiframework.Role'


// grails.plugin.springsecurity.rememberMe.persistent = true		  // grails.plugin.springsecurity.rememberMe.persistent = true
// grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'net.nosegrind.apiframework.PersistentLogin'		  // grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'net.nosegrind.apiframework.PersistentLogin'


grails.plugin.springsecurity.adh.errorPage = null
grails.plugin.springsecurity.adh.errorPage = null


grails.plugin.springsecurity.providerNames = ['daoAuthenticationProvider', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']


grails.plugin.springsecurity.rememberMe.alwaysRemember = true
grails.plugin.springsecurity.rememberMe.cookieName = 'apiTest'
grails.plugin.springsecurity.rememberMe.key = '_grails_'


grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.ui.encodePassword = false
grails.plugin.springsecurity.auth.forceHttps = false
//grails.plugin.springsecurity.auth.loginFormUrl = '/login/auth/'
//grails.plugin.springsecurity.auth.ajaxLoginFormUrl = '/login/authAjax/'

grails.plugin.springsecurity.successHandler.defaultTargetUrl = '/login/ajaxSuccess'
grails.plugin.springsecurity.failureHandler.defaultFailureUrl = '/login/ajaxDenied'
grails.plugin.springsecurity.failureHandler.ajaxAuthFailUrl = '/login/ajaxDenied'

grails.plugin.springsecurity.interceptUrlMap = [
        "/${entryPoint}/**":['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**':              ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/':                ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/error':           ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/index':           ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/index.gsp':       ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/shutdown':        ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/assets/**':       ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/error/**' :       ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/apidoc/**' :      ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/js/**':        ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/css/**':       ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/images/**':    ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/**/favicon.ico':  ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/auth':            ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/auth/**':         ['IS_AUTHENTICATED_ANONYMOUSLY'],
        '/login':           ["permitAll"],
        '/login/**':        ["permitAll"],
        '/logout':          ["permitAll"],
        '/logout/**':       ["permitAll"]
]




