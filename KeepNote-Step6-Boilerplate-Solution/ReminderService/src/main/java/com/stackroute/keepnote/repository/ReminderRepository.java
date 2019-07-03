package com.stackroute.keepnote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Reminder;

/*
* This class is implementing the MongoRepository interface for User.
* Annotate this class with @Repository annotation
* */

@Repository
public interface ReminderRepository extends MongoRepository<Reminder, String> {
	

	List<Reminder> findByReminderCreatedBy(String reminderCreatedBy);
	void deleteByReminderCreatedBy(String reminderCreatedBy);

}
