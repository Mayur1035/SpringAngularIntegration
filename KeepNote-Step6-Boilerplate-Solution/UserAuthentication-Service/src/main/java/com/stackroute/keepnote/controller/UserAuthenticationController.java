package com.stackroute.keepnote.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.UserAlreadyExistsException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.Token;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.service.UserAuthenticationService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * As in this assignment, we are working on creating RESTful web service, hence annotate
 * the class with @RestController annotation. A class annotated with the @Controller annotation
 * has handler methods which return a view. However, if we use @ResponseBody annotation along
 * with @Controller annotation, it will return the data directly in a serialized 
 * format. Starting from Spring 4 and above, we can use @RestController annotation which 
 * is equivalent to using @Controller and @ResposeBody annotation
 */
@RestController
public class UserAuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);
	/*
	 * Autowiring should be implemented for the UserAuthenticationService. (Use
	 * Constructor-based autowiring) Please note that we should not create an object
	 * using the new keyword
	 */
	@Autowired
	UserAuthenticationService userAuthenticationService;

	public UserAuthenticationController(UserAuthenticationService authicationService) {
		this.userAuthenticationService = authicationService;
	}

	/*
	 * Define a handler method which will create a specific user by reading the
	 * Serialized object from request body and save the user details in the
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 201(CREATED) - If the user created
	 * successfully. 2. 409(CONFLICT) - If the userId conflicts with any existing
	 * user
	 * 
	 * This handler method should map to the URL "/api/v1/auth/register" using HTTP
	 * POST method
	 */

	@PostMapping(path = "/api/v1/auth/register")
	public ResponseEntity<User> saveUser(@RequestBody User user, HttpSession session) {
		try {
			User newUser = userAuthenticationService.saveUser(user);
			if (null != newUser) {
				return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
			} else {
				logger.error("Error in registering user");
				return new ResponseEntity<User>(HttpStatus.CONFLICT);
			}

		} catch (UserAlreadyExistsException e) {
			logger.error("Error in registering user", e);
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
	}

	/*
	 * Define a handler method which will authenticate a user by reading the
	 * Serialized user object from request body containing the username and
	 * password. The username and password should be validated before proceeding
	 * ahead with JWT token generation. The user credentials will be validated
	 * against the database entries. The error should be return if validation is not
	 * successful. If credentials are validated successfully, then JWT token will be
	 * generated. The token should be returned back to the caller along with the API
	 * response. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If login is successful 2.
	 * 401(UNAUTHORIZED) - If login is not successful
	 * 
	 * This handler method should map to the URL "/api/v1/auth/login" using HTTP
	 * POST method
	 */

	@PostMapping(path = "/api/v1/auth/login")
	public ResponseEntity<Token> authenticateUser(@RequestBody User user) {
		Token tokenObj = new Token();
		try {
			User authorizedUser = userAuthenticationService.findByUserIdAndPassword(user.getUserId(),
					user.getUserPassword());
			if (null != authorizedUser) {
				String token = getToken(user.getUserId(), user.getUserPassword());
				tokenObj.setToken(token);
				return new ResponseEntity<>(tokenObj, HttpStatus.OK);
			} else {
				logger.error("Authorization Failed");
			}
		} catch (Exception e) {
			logger.error("Authorization Issue", e);
		}
		tokenObj.setMessage("Authorization Failed");
		return new ResponseEntity<>(tokenObj, HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(path = "/api/v1/auth/login/userDetails")
	public ResponseEntity<User> getUser(@RequestHeader(value = "Authorization") String authHeader) {
		String userId = getUserIdOfToken(authHeader);
		try {
			User user = userAuthenticationService.findByUserId(userId);
			user.setUserPassword("**********");
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (UserNotFoundException e) {
			logger.error("Error in registering user", e);
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
	}

	// Generate JWT token
	public String getToken(String username, String password) throws Exception {
		Date issueDate = new Date();
		Date expirationDate = new Date(issueDate.getTime() + (10 * 60000));
		return Jwts.builder().setId(username).setIssuedAt(issueDate).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS256, "secretkey").compact();

	}

	@PostMapping(path = "/api/v1/auth/login/isAuthenticated")
	public ResponseEntity<Token> isAuthenticated(@RequestHeader(value = "Authorization") String authHeader) {
		return new ResponseEntity<>(validateToken(authHeader), HttpStatus.OK);
	}

	// Validate JWT token
	public Token validateToken(String authHeader) {
		Token tokenObj = new Token();
		Boolean validToken = false;
		try {
			if (authHeader != null && authHeader.startsWith("Bearer")) {
				final String token = authHeader.substring(7);
				final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
				if (claims.getIssuedAt().getTime() < claims.getExpiration().getTime()) {
					validToken = true;
				}
			}

		} catch (Exception e) {
			logger.error("Exception while validating token", e);
		}
		tokenObj.setAuthenticated(validToken);
		return tokenObj;
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
