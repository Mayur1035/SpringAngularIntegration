package com.stackroute.keepnote.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.CategoryRepository;
import com.stackroute.keepnote.repository.NoteListRepository;
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
public class NoteServiceImpl implements NoteService {

	private static final Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

	/*
	 * Autowiring should be implemented for the NoteRepository and MongoOperation.
	 * (Use Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	NoteListRepository noteListRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ReminderRepository reminderRepository;

	/*
	 * This method should be used to save a new note.
	 */
	public Note createNote(Note note) {
		Date currentDate = new Date();
		String date = LocalDateTime.now().toString();
		note.setCreatedAt(currentDate);
		note.setUpdatedAt(currentDate);
		note.setCreatedOn(date);
		note.setUpdatedOn(date);
		return noteListRepository.insert(note);
	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(String noteId) {
		boolean noteDeleted = false;
		try {
			noteListRepository.deleteById(noteId);
			noteDeleted = true;
		} catch (Exception e) {
			logger.error("Exception while deleting Note by NoteId", e);
		}
		return noteDeleted;
	}

	/* This method should be used to delete all notes with specific userId. */

	public boolean deleteAllNotes(String userId) {
		boolean noteDeleted = false;
		try {
			noteListRepository.deleteByCreatedBy(userId);
			noteDeleted = true;
		} catch (Exception e) {
			logger.error("Exception while deleting notes for User", e);
		}
		return noteDeleted;
	}

	/*
	 * This method should be used to update a existing note.
	 */
	public Note updateNote(Note note) throws NoteNotFoundExeption{
		Date updatedDate = new Date();
		String date = LocalDateTime.now().toString();
		if(null != note) {
			note.setUpdatedAt(updatedDate);
			note.setUpdatedOn(date);
			Note updatedNote = noteListRepository.save(note);
			return updatedNote;
		}else {
			throw new NoteNotFoundExeption("Note not found");
		}
	}

	/*
	 * This method should be used to get a note by noteId created by specific user
	 */
	public Note getNoteByNoteId(String noteId) {
		return noteListRepository.findById(noteId).get();
	}

	/*
	 * This method should be used to get all notes with specific userId.
	 */
	public List<Note> getAllNoteByUserId(String userId) {
		return noteListRepository.findByCreatedBy(userId);
	}

	@Override
	public Category createCategory(Category category) {
		Date currentDate = new Date();
		//String date = LocalDateTime.now().toString();
		category.setCategoryCreationDate(currentDate);
		return categoryRepository.insert(category);
	}

	@Override
	public boolean deleteCategory(String id) {
		boolean isDeleted = false;
		try {
			categoryRepository.deleteById(id);
			isDeleted = true;
		} catch (Exception e) {
			logger.error("Exception ", e);
		}
		return isDeleted;
	}

	@Override
	public Category updateCategory(Category category) throws NoteNotFoundExeption {
		Date updatedDate = new Date();
		//String date = LocalDateTime.now().toString();
		if(null != category) {
			category.setCategoryCreationDate(updatedDate);
			Category categoryUpdated = categoryRepository.save(category);
			return categoryUpdated;
		}else {
			throw new NoteNotFoundExeption("Not found");
		}
	}

	@Override
	public Category getCategoryByCategoryId(String id) throws NoteNotFoundExeption {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id).get();
	}

	@Override
	public List<Category> getAllCategoryByUserId(String userId) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryCreatedBy(userId);
	}

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
	public Reminder updateReminder(Reminder reminder) throws NoteNotFoundExeption {
		Date updatedDate = new Date();
		//String date = LocalDateTime.now().toString();
		if(null != reminder) {
			reminder.setReminderCreationDate(updatedDate);
			Reminder reminderUpdated = reminderRepository.save(reminder);
			return reminderUpdated;
		}else {
			throw new NoteNotFoundExeption("Not found");
		}
	}

	@Override
	public Reminder getReminderByReminderId(String id) throws NoteNotFoundExeption {
		// TODO Auto-generated method stub
		return reminderRepository.findById(id).get();
	}

	@Override
	public List<Reminder> getAllReminderByUserId(String userId) {
		// TODO Auto-generated method stub
		return reminderRepository.findByReminderCreatedBy(userId);
	}

}
