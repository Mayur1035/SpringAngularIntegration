package com.stackroute.keepnote.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.ReminderService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/*
 * As in this assignment, we are working with creating RESTful web service, hence annotate
 * the class with @RestController annotation.A class annotated with @Controller annotation
 * has handler methods which returns a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class ReminderController {

	private static final Logger logger = LoggerFactory.getLogger(ReminderController.class);

	/*
	 * From the problem statement, we can understand that the application
	 * requires us to implement five functionalities regarding reminder. They
	 * are as following:
	 * 
	 * 1. Create a reminder 2. Delete a reminder 3. Update a reminder 4. Get all
	 * reminders by userId 5. Get a specific reminder by id.
	 * 
	 */

	/*
	 * Autowiring should be implemented for the ReminderService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */

	@Autowired
	ReminderService reminderService;

	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	@PostMapping(path = "/api/v1/reminder")
	public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder,
			@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		reminder.setReminderCreatedBy(userId);
		Reminder createdReminder = reminderService.createReminder(reminder);
		if (null != createdReminder) {
			return new ResponseEntity<Reminder>(createdReminder, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Reminder>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping(path = "/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> deleteReminder(@PathVariable String id) {
		if (reminderService.deleteReminder(id)) {
			return new ResponseEntity<Reminder>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> updateReminder(@RequestBody Reminder reminder) {
		try {
			return new ResponseEntity<Reminder>(reminderService.updateReminder(reminder), HttpStatus.OK);
		} catch (ReminderNotFoundException e) {
			logger.error("Error in updating Note", e);
			return new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/api/v1/reminder")
	public ResponseEntity<List<Reminder>> getReminderByuser(@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		List<Reminder> reminders = new ArrayList<Reminder>();
		reminders = reminderService.getAllReminderByUserId(userId);
		if (!reminders.isEmpty()) {
			return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> getReminderByuserandId(@PathVariable String id) {
		try {
			return new ResponseEntity<Reminder>(reminderService.getReminderByReminderId(id), HttpStatus.OK);
		} catch (ReminderNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Get UserId from JWT token
	public String getUserIdOfToken(String authHeader) {
		String userId = "";
		try {
			if (authHeader != null && authHeader.startsWith("Bearer")) {
				final String token = authHeader.substring(7);
				final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
				userId = claims.getId();
			}
		} catch (Exception e) {
			logger.error("Exception while validating token", e);
		}
		return userId;
	}

}
