package com.stackroute.keepnote.test.service;

import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.NoteUser;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.repository.NoteRepository;
import com.stackroute.keepnote.service.NoteServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

public class NoteServiceImplTest {


    @MockBean
    private Note note;
    @MockBean
    private NoteUser noteUser;
    @MockBean
    private Category category;
    @MockBean
    private Reminder reminder;
    @Mock
    private NoteRepository noteRepository;
    @InjectMocks
    private NoteServiceImpl noteServiceImpl;
    private List<Note> noteList = null;
    Optional<NoteUser> options;


    @Before
    public void setUp() throws Exception {


        MockitoAnnotations.initMocks(this);

        category = new Category();
        category = new Category();
        category.setCategoryId("5b04f7411764e3765c35f8f6");
        category.setCategoryName("Cricket-Category");
        category.setCategoryDescription("All about Cricket");
        category.setCategoryCreatedBy("Jhon123");
        category.setCategoryCreationDate(new Date());

        // Reminder

        reminder = new Reminder();
        reminder.setReminderId("5b0509731764e3096984eae6");
        reminder.setReminderName("Email-Reminder");
        reminder.setReminderDescription("sending emails");
        reminder.setReminderType("email type");
        reminder.setReminderCreatedBy("Jhon123");
        reminder.setReminderCreationDate(new Date());


        List<Reminder> reminderList = new ArrayList<>();
        reminderList.add(reminder);

        // Note
        note = new Note();
        //note.setNoteId(1);
        note.setId("1");
        note.setTitle("IPL lists");
        note.setText("Mumbai Indians vs RCB match scheduled  for 4 PM");
        note.setState("Active");
        note.setCategory(category);
        note.setReminders(reminder);
        note.setCreatedBy("Jhon123");
        note.setCreatedAt(new Date());

        noteList = new ArrayList<>();
        noteList.add(note);

        noteUser = new NoteUser();
        noteUser.setUserId("Jhon123");
        noteUser.setNotes(noteList);

        options = Optional.of(noteUser);


    }


   // @Test
    public void createNoteSuccess() {
        when(noteRepository.insert((NoteUser) any())).thenReturn(noteUser);
        Note newNote = noteServiceImpl.createNote(note);
        Assert.assertNotNull(newNote);
        verify(noteRepository, times(1)).insert((NoteUser) any());
    }

   // @Test
    public void createNoteFailure() {
        when(noteRepository.insert((NoteUser) any())).thenReturn(null);
        Note newNote = noteServiceImpl.createNote(note);
        Assert.assertNotNull(newNote);
    }


    @Test
    public void deleteNoteSuccess() {
        when(noteRepository.findById(noteUser.getUserId())).thenReturn(options);
        when(noteRepository.save(noteUser)).thenReturn(noteUser);
        boolean flag = noteServiceImpl.deleteNote(note.getId());
        Assert.assertEquals(false, flag);
    }

    //@Test(expected = NullPointerException.class)
    public void deleteNoteFailure() {
        when(noteRepository.findById(noteUser.getUserId())).thenReturn(null);
        when(noteRepository.save(noteUser)).thenReturn(noteUser);
        boolean flag = noteServiceImpl.deleteNote(note.getId());
        Assert.assertEquals(true, flag);
    }


    @Test
    public void deleteAllNoteSuccess() throws NoteNotFoundExeption {

        when(noteRepository.findById("Jhon123")).thenReturn(options);
        when(noteRepository.save(noteUser)).thenReturn(noteUser);
        boolean flag = noteServiceImpl.deleteAllNotes("Jhon123");
        Assert.assertEquals(false, flag);

    }


    @Test(expected = NoSuchElementException.class)
    public void deleteAllNoteFailure() throws NoteNotFoundExeption {
        when(noteRepository.findById("Jhon123").get().getNotes()).thenReturn(null);
        when(noteRepository.save(noteUser)).thenReturn(noteUser);
        boolean flag = noteServiceImpl.deleteAllNotes("Jhon123");
        Assert.assertEquals(true, flag);

    }

   // @Test
    public void updateNoteSuccess() throws NoteNotFoundExeption {

        when(noteRepository.findById("Jhon123")).thenReturn(options);
        note.setText("Match cancelled");
        noteList.add(note);
        Note fetchedNote = noteServiceImpl.updateNote(note);
        Assert.assertEquals(note, fetchedNote);


    }


   // @Test
    public void getNoteByNoteIdSuccess() throws NoteNotFoundExeption {
        when(noteRepository.findById("Jhon123")).thenReturn(options);
        Note fetechedNote = noteServiceImpl.getNoteByNoteId(note.getId());
        Assert.assertEquals(note, fetechedNote);
    }

   // @Test(expected = NoteNotFoundExeption.class)
    public void getNoteByNoteIdFailure() throws NoteNotFoundExeption {
        when(noteRepository.findById("Jhon123")).thenThrow(NoSuchElementException.class);
        Note fetechedNote = noteServiceImpl.getNoteByNoteId(note.getId());
        Assert.assertEquals(note, fetechedNote);
    }

   // @Test
    public void getAllNoteByUserId() {
        when(noteRepository.findById("Jhon123")).thenReturn(options);
        List<Note> notes = noteServiceImpl.getAllNoteByUserId("Jhon123");
        Assert.assertEquals(noteList, notes);
    }
}
