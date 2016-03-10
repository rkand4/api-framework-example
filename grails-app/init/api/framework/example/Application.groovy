package api.framework.example

import grails.boot.GrailsApp
import grails.boot.config.GrailsAutoConfiguration

import org.springframework.web.servlet.DispatcherServlet
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration
//import org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration

//@EnableAutoConfiguration(exclude = [SecurityFilterAutoConfiguration])
class Application extends GrailsAutoConfiguration {
    static void main(String[] args) {
        GrailsApp.run(Application)
    }
}
