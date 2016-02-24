package net.nosegrind.apiframework

import org.springframework.dao.DataIntegrityViolationException
import net.nosegrind.apitoolkit.*

class HookController {

	def springSecurityService

	static defaultAction = 'list'
	

	def list() {
		if(!springSecurityService.isLoggedIn()){
			redirect(uri: "/login/auth")
		}

		def webhookList = isSuperuser() ? Hook.list(params) : Hook.findAllByUser(user, [max:params.max, sort:params.sort, order:params.order, offset:params.offset] )
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		return ['hook':webhookList]
	}

	def create() {
		if(!springSecurityService.isLoggedIn()){
			redirect(uri: "/login/auth")
		}
		return ['hook': new Hook(params)]
	}

	def save() {
		if(!springSecurityService.isLoggedIn()){
			redirect(uri: "/login/auth")
		}
		def formats = ['JSON','XML']
		Hook webhookInstance = Hook.findByUrlAndService(params.url,params.service)
		if(webhookInstance){
			flash.message = "URL EXISTS: PLEASE CHECK YOUR REGISTERED WEBHOOKS TO MAKE SURE THIS IS NOT A DUPLICATE."
			render(view:"create",model:[params:params])
		}

		if(!HookService.validateUrl(params.url)){
			flash.message = "BAD PROTOCOL: URL MUST BE FULLY QUALIFIED DOMAIN NAME (OR IP ADDRESS) FORMATTED WITH HTTP/HTTPS. PLEASE TRY AGAIN."
			render(view:"create",model:[params:params,service:grailsApplication.config.apitoolkit.services,format:formats])
		}

		webhookInstance = new Hook(params)
		webhookInstance.user=user

		if (webhookInstance.save(flush: true)) {
			flash.message = message(code: 'default.created.message', args: [message(code: 'webhook.label', default: 'Hook'), webhookInstance.id])
			redirect(action:"show", id: webhookInstance.id)
		}

		flash.message = "INVALID/MALFORMED DATA: PLEASE SEE DOCS FOR 'JSON' FORMED STRING AND PLEASE TRY AGAIN."
		render(view:"create",model:[params:params,service:grailsApplication.config.apitoolkit.webhook.services,format:formats])
	}

	def edit() {
		if(!springSecurityService.isLoggedIn()){
			redirect(uri: "/login/auth")
		}
		return ['hook': Hook.get(params.id)]
	}

	def update() {
		def user = loggedInUser()
		if(!user){
			redirect(uri: "/login/auth")
			return
		}

		Hook webhookInstance = Hook.findByIdAndPerson(params.id, user)
		if(!webhookInstance){
			flash.message = "WEBHOOK NOT FOUND: NO WEBHOOK WITH THAT ID FOUND BELONGING TO CURRENT USER."
			render(view:"create",model:[params:params])
		}

		webhookInstance.name = params.name
		webhookInstance.url = params.url
		webhookInstance.format = params.format
		webhookInstance.service = params.service

		if (webhookInstance.save(flush: true)) {
			flash.message = message(code: 'default.updated.message', args: [message(code: 'webhook.label', default: 'Hook'), webhookInstance.id])
			redirect(action:"show", id: webhookInstance.id)
		}

		flash.message = "INVALID/MALFORMED DATA: PLEASE SEE DOCS FOR 'JSON' FORMED STRING AND PLEASE TRY AGAIN."
		render(view:"create",model:[params:params])
	}

	def show() {
		def user = loggedInUser()
		if(!user){
			redirect(uri: "/login/auth")
		}

		def webhookInstance = isSuperuser() ? Hook.get(params.id) : Hook.findByPersonAndId(user,params.id.toLong())

		if (webhookInstance) {
			render(view:"show",model:[webhookInstance: webhookInstance])
		}

		flash.message = message(code: 'default.not.found.message', args: [message(code: 'webhook.label', default: 'Hook'), params.id])
		redirect(action: "list")
	}

	def delete() {
		def user = loggedInUser()
		if(!user){
			redirect(uri: "/login/auth")
		}

		def webhookInstance = isSuperuser() ? Hook.get(params.id) : Hook.findByPersonAndId(user,params.id.toLong())

		if (!webhookInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'webhook.label', default: 'Hook'), params.id])
			redirect(action: "list")
		}

		try {
			webhookInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'webhook.label', default: 'Hook'), params.id])
			redirect(action: "list")
		}catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'webhook.label', default: 'Hook'), params.id])
			redirect(action: "show", id: params.id)
		}
	}

	def reset() {
		def user = loggedInUser()
		if(!user){
			redirect(uri: "/login/auth")
		}

		Hook webhookInstance = Hook.findByIdAndUser(params.id, user)
		if(!webhookInstance){
			flash.message = "WEBHOOK NOT FOUND: NO WEBHOOK WITH THAT ID FOUND BELONGING TO CURRENT USER."
			render(view:"list",model:[params:params])
		}

		webhookInstance.attempts = 0

		if (webhookInstance.save(flush: true)) {
			flash.message = message(code: 'default.reset.message', args: [message(code: 'webhook.label', default: 'Hook'), webhookInstance.id])
			redirect(action:"list")
		}
	}

	protected loggedInUser() {
		springSecurityService.isLoggedIn() ? Person.load(springSecurityService.principal.id) : null
	}

	protected boolean isSuperuser() {
		springSecurityService.principal.authorities*.authority.any { grailsApplication.config.apitoolkit.admin.roles.containsKey(it) }
	}
}
