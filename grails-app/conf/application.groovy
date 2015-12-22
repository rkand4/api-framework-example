import grails.util.Metadata

String apiVersion = Metadata.current.getApplicationVersion()
// fix for dots not working with spring security pathing
String entryPoint = "/v${apiVersion}".toString()
String batchEntryPoint = "b${apiVersion}"
String chainEntryPoint = "c${apiVersion}"
String tracertEntryPoint = "t${apiVersion}"
String domainEntryPoint = "d${apiVersion}"

// Added by the Spring Security Core plugin:
// grails.plugin.springsecurity.rememberMe.persistent = true
// grails.plugin.springsecurity.rememberMe.persistentToken.domainClassName = 'net.nosegrind.apiframework.PersistentLogin'

grails.plugin.springsecurity.adh.errorPage = null


//grails.plugin.springsecurity.providerNames = ['daoAuthenticationProvider', 'anonymousAuthenticationProvider', 'rememberMeAuthenticationProvider']

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
        "/${tracertEntryPoint}/**" : 'none',
        "/${domainEntryPoint}/**" : 'none'
*/
grails.plugin.springsecurity.interceptUrlMap = [
        "/${entryPoint}/**":'permitAll',
        '/**':              'IS_AUTHENTICATED_FULLY',
        '/':                'permitAll',
        '/error':           'permitAll',
        '/index':           'permitAll',
        '/index.gsp':       'permitAll',
        '/shutdown':        'permitAll',
        '/assets/**':       'permitAll',
        '/error/**' :       'permitAll',
        '/hook/**' :        'permitAll',
        '/apidoc/**' :      'permitAll',
        '/**/js/**':        'permitAll',
        '/**/css/**':       'permitAll',
        '/**/images/**':    'permitAll',
        '/**/favicon.ico':  'permitAll',
        '/login':           'permitAll',
        '/login/**':        'permitAll',
        '/logout':          'permitAll',
        '/logout/**':       'permitAll'
]




