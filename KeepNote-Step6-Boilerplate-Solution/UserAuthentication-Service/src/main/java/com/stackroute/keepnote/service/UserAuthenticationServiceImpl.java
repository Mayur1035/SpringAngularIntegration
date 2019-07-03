package com.stackroute.keepnote.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.stackroute.keepnote.exception.UserAlreadyExistsException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;
import com.stackroute.keepnote.repository.UserAutheticationRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

	private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationServiceImpl.class);

	/*
	 * Autowiring should be implemented for the UserAuthenticationRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	UserAutheticationRepository userAutheticationRepository;

	/*
	 * This method should be used to validate a user using userId and password. Call
	 * the corresponding method of Respository interface.
	 * 
	 */
	@Override
	public User findByUserIdAndPassword(String userId, String password) throws UserNotFoundException {
		if (!StringUtils.isEmpty(userId) && !StringUtils.isEmpty(password)) {
			return userAutheticationRepository.findByUserIdAndUserPassword(userId, password);
		} else {
			throw new UserNotFoundException("Given user not found");
		}

	}

	@Override
	public User findByUserId(String userId) throws UserNotFoundException {
		if (!StringUtils.isEmpty(userId)) {
			return userAutheticationRepository.findByUserId(userId);
		} else {
			throw new UserNotFoundException("Given user not found");
		}
	}
	/*
	 * This method should be used to save a new user.Call the corresponding method
	 * of Respository interface.
	 */

	@Override
	public User saveUser(User user) throws UserAlreadyExistsException {
		/*
		 * Boolean userSaved = false; try { userAutheticationRepository.save(user);
		 * userSaved = true; } catch (Exception e) {
		 * logger.error("Issues while saving user", e); throw new
		 * UserAlreadyExistsException("Cannot Register User"); }
		 */
		if (!StringUtils.isEmpty(user.getFirstName()) && !StringUtils.isEmpty(user.getUserId())
				&& !StringUtils.isEmpty(user.getUserPassword())) {
			if(!userAutheticationRepository.existsById(user.getUserId())) {
				return userAutheticationRepository.save(user);
			}else {
				throw new UserAlreadyExistsException("User already registered");
			}
		} else {
			return null;
		}
	}
}
