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

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.NoteService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
public class ReminderController {

	private static final Logger logger = LoggerFactory.getLogger(ReminderController.class);

	@Autowired
	NoteService noteService;

	public ReminderController(NoteService noteService) {
		this.noteService = noteService;

	}

	@PostMapping(path = "/api/v1/reminder")
	public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder,
			@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		reminder.setReminderCreatedBy(userId);
		Reminder createdReminder = noteService.createReminder(reminder);
		if (null != createdReminder) {
			return new ResponseEntity<Reminder>(createdReminder, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Reminder>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping(path = "/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> deleteReminder(@PathVariable String id) {
		if (noteService.deleteReminder(id)) {
			return new ResponseEntity<Reminder>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> updateReminder(@RequestBody Reminder reminder) {
		try {
			return new ResponseEntity<Reminder>(noteService.updateReminder(reminder), HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			logger.error("Error in updating Note", e);
			return new ResponseEntity<Reminder>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/api/v1/reminder")
	public ResponseEntity<List<Reminder>> getReminderByuser(@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		List<Reminder> reminders = new ArrayList<Reminder>();
		reminders = noteService.getAllReminderByUserId(userId);
		if (!reminders.isEmpty()) {
			return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/api/v1/reminder/{id}")
	public ResponseEntity<Reminder> getReminderByuserandId(@PathVariable String id) {
		try {
			return new ResponseEntity<Reminder>(noteService.getReminderByReminderId(id), HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
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
