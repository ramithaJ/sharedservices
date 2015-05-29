package com.wiley.gr.ace.authorservices.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wiley.gr.ace.authorservices.model.Service;
import com.wiley.gr.ace.authorservices.services.service.MailSendingUtilityService;

@RestController
@RequestMapping("/v1/notifications")
public class SendEmailController {

	@Autowired
	MailSendingUtilityService mailSendingUtilityService;

	@RequestMapping(value = "/send/{from}/{to}/{subject}/{body}", method = RequestMethod.POST)
	public @ResponseBody Service sendEmail(@PathVariable("from") String from,
			@PathVariable("to") String to,
			@PathVariable("subject") String subject,
			@PathVariable("body") String body) {
		try {
			mailSendingUtilityService.sendEmail(from, to, subject, body);
			System.out.println("Gotcha!!!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println(e.getMessage());
		}
		
		return null;

	}
}
