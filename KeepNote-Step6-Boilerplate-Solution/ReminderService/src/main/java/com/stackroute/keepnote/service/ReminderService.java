package com.stackroute.keepnote.service;

import java.util.List;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

public interface ReminderService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */

	Reminder createReminder(Reminder reminder);

    boolean deleteReminder(String id);

    Reminder updateReminder(Reminder reminder) throws ReminderNotFoundException;

    Reminder getReminderByReminderId(String id) throws ReminderNotFoundException;

    List<Reminder> getAllReminderByUserId(String userId);

}
