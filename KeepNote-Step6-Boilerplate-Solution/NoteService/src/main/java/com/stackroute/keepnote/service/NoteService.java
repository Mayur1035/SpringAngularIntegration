package com.stackroute.keepnote.service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;

import java.util.List;


public interface NoteService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */


    Note createNote(Note note);

    boolean deleteNote(String noteId);

    boolean deleteAllNotes(String userId) throws NoteNotFoundExeption;

    Note updateNote(Note note) throws NoteNotFoundExeption;

    Note getNoteByNoteId(String noteId) throws NoteNotFoundExeption;

    List<Note> getAllNoteByUserId(String userId);
    

    Category createCategory(Category category);

    boolean deleteCategory(String id);

    Category updateCategory(Category category) throws NoteNotFoundExeption;

    Category getCategoryByCategoryId(String id) throws NoteNotFoundExeption;

    List<Category> getAllCategoryByUserId(String userId);
    

    Reminder createReminder(Reminder reminder);

    boolean deleteReminder(String id);

    Reminder updateReminder(Reminder reminder) throws NoteNotFoundExeption;

    Reminder getReminderByReminderId(String id) throws NoteNotFoundExeption;

    List<Reminder> getAllReminderByUserId(String userId);


}
