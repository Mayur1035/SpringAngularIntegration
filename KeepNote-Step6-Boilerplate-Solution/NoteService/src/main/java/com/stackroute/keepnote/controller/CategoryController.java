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
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.NoteService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
public class CategoryController {

	private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

	@Autowired
	NoteService noteService;

	public CategoryController(NoteService noteService) {
		this.noteService = noteService;
	}

	@PostMapping(path = "/api/v1/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category,
			@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		category.setCategoryCreatedBy(userId);
		Category createdCategory = noteService.createCategory(category);
		if (null != createdCategory) {
			return new ResponseEntity<Category>(createdCategory, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Category>(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping(path = "/api/v1/category/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable String id) {
		if (noteService.deleteCategory(id)) {
			return new ResponseEntity<Category>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(path = "/api/v1/category/{id}")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		try {
			return new ResponseEntity<Category>(noteService.updateCategory(category), HttpStatus.OK);
		} catch (NoteNotFoundExeption e) {
			logger.error("Error in updating Note", e);
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping(path = "/api/v1/category")
	public ResponseEntity<List<Category>> getCategoryByuser(@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		List<Category> categories = new ArrayList<Category>();
		categories = noteService.getAllCategoryByUserId(userId);
		if (!categories.isEmpty()) {
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Category>>(categories, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/api/v1/category/{id}")
	public ResponseEntity<Category> getCategoryByuserandId(@PathVariable String id) {
		try {
			return new ResponseEntity<Category>(noteService.getCategoryByCategoryId(id), HttpStatus.OK);
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
