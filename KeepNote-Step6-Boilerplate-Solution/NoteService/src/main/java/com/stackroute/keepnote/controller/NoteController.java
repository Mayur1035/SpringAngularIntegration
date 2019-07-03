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
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.service.NoteService;

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
public class NoteController {

	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	/*
	 * Autowiring should be implemented for the NoteService. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword
	 */
	@Autowired
	NoteService noteService;

	public NoteController(NoteService noteService) {
		this.noteService = noteService;

	}

	/*
	 * Define a handler method which will create a specific note by reading the
	 * Serialized object from request body and save the note details in the
	 * database.This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the note created
	 * successfully. 2. 409(CONFLICT) - If the noteId conflicts with any existing
	 * user.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP POST
	 * method
	 */
	@PostMapping(path = "/api/v1/note")
	public ResponseEntity<Note> createNote(@RequestBody Note note,
			@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		note.setCreatedBy(userId);
		Note createdNote = noteService.createNote(note);
		if (null != createdNote) {
			return new ResponseEntity<Note>(createdNote, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Note>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will delete a note from a database. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 *
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid noteId without {}
	 */
	@DeleteMapping(path = "/api/v1/note/{id}")
	public ResponseEntity<Note> deleteNote(@PathVariable String id) {
		if (noteService.deleteNote(id)) {
			return new ResponseEntity<Note>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/api/v1/note")
	public ResponseEntity<Note> deleteAllNote(@RequestHeader(value = "Authorization") String authHeader) {
		try {
			String userId = getUserIdOfToken(authHeader);
			noteService.deleteAllNotes(userId);
			return new ResponseEntity<Note>(HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			logger.error("Error in delete all note", e);
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will update a specific note by reading the
	 * Serialized object from request body and save the updated note details in a
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the note updated successfully.
	 * 2. 404(NOT FOUND) - If the note with specified noteId is not found.
	 * 
	 * This handler method should map to the URL "/api/v1/note/{id}" using HTTP PUT
	 * method.
	 */
	@PutMapping(path = "/api/v1/note/{id}")
	public ResponseEntity<Note> updateNote(@RequestBody Note note) {
		try {
			return new ResponseEntity<Note>(noteService.updateNote(note), HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			logger.error("Error in updating Note", e);
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		}

	}

	/*
	 * Define a handler method which will get us the all notes by a userId. This
	 * handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the note found successfully.
	 * 
	 * This handler method should map to the URL "/api/v1/note" using HTTP GET
	 * method
	 */
	@GetMapping(path = "/api/v1/note")
	public ResponseEntity<List<Note>> getNoteByuser(@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		List<Note> notes = new ArrayList<Note>();
		notes = noteService.getAllNoteByUserId(userId);
		if (!notes.isEmpty()) {
			return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Note>>(notes, HttpStatus.NOT_FOUND);
		}
	}

	/*
	 * Define a handler method which will show details of a specific note created by
	 * specific user. This handler method should return any one of the status
	 * messages basis on different situations: 1. 200(OK) - If the note found
	 * successfully. 2. 404(NOT FOUND) - If the note with specified noteId is not
	 * found. This handler method should map to the URL
	 * "/api/v1/note/{userId}/{noteId}" using HTTP GET method where "id" should be
	 * replaced by a valid reminderId without {}
	 * 
	 */
	@GetMapping(path = "/api/v1/note/{noteId}")
	public ResponseEntity<Note> getNoteByuserandId(@PathVariable String noteId) {
		try {
			return new ResponseEntity<Note>(noteService.getNoteByNoteId(noteId), HttpStatus.OK);
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
