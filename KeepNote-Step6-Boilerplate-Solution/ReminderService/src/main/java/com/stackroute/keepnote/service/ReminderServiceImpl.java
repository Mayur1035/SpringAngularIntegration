package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.ReminderRepository;

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
public class ReminderServiceImpl implements ReminderService {

	private static final Logger logger = LoggerFactory.getLogger(ReminderServiceImpl.class);

	/*
	 * Autowiring should be implemented for the ReminderRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	ReminderRepository reminderRepository;

	@Override
	public Reminder createReminder(Reminder reminder) {
		Date currentDate = new Date();
		//String date = LocalDateTime.now().toString();
		reminder.setReminderCreationDate(currentDate);
		return reminderRepository.insert(reminder);
	}

	@Override
	public boolean deleteReminder(String id) {
		boolean isDeleted = false;
		try {
			reminderRepository.deleteById(id);
			isDeleted = true;
		} catch (Exception e) {
			logger.error("Exception ", e);
		}
		return isDeleted;
	}

	@Override
	public Reminder updateReminder(Reminder reminder) throws ReminderNotFoundException {
		Date updatedDate = new Date();
		//String date = LocalDateTime.now().toString();
		if(null != reminder) {
			reminder.setReminderCreationDate(updatedDate);
			Reminder reminderUpdated = reminderRepository.save(reminder);
			return reminderUpdated;
		}else {
			throw new ReminderNotFoundException("Not found");
		}
	}

	@Override
	public Reminder getReminderByReminderId(String id) throws ReminderNotFoundException {
		// TODO Auto-generated method stub
		return reminderRepository.findById(id).get();
	}

	@Override
	public List<Reminder> getAllReminderByUserId(String userId) {
		// TODO Auto-generated method stub
		return reminderRepository.findByReminderCreatedBy(userId);
	}

}
