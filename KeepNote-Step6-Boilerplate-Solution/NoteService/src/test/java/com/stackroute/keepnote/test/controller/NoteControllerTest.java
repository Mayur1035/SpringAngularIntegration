package com.stackroute.keepnote.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.keepnote.controller.NoteController;
import com.stackroute.keepnote.exception.NoteNotFoundExeption;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.model.Note;
import com.stackroute.keepnote.model.Reminder;
import com.stackroute.keepnote.service.NoteService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Note note;
    @MockBean
    private Category category;
    @MockBean
    private Reminder reminder;
    @MockBean
    private NoteService noteService;
    @InjectMocks
    private NoteController noteController;
    private List<Note> noteList;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(noteController).build();
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
       // note.setNoteId(1);
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

    }



    @Test
    public void deleteNoteFailure() throws Exception {

        when(noteService.deleteNote(note.getId())).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/note/Jhon123/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void deleteAllNotesFailure() throws Exception {

        when(noteService.deleteAllNotes("Jhon123")).thenThrow(NoteNotFoundExeption.class);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/note/Jhon123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());

    }



    @Test
    public void updateNoteFailure() throws Exception {

        when(noteService.updateNote(any())).thenThrow(NoteNotFoundExeption.class);
        note.setText("Mumbai Indians vs RCB match scheduled  for 6 PM");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/note/Jhon123/" + note.getNoteId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(note)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }



    @Test
    public void getNoteByIdFailure() throws Exception {

        when(noteService.getNoteByNoteId("1")).thenThrow(NoteNotFoundExeption.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/Jhon123/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllNotesByUserIdSuccess() throws Exception {
        when(noteService.getAllNoteByUserId("Jhon123")).thenReturn(noteList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/Jhon123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getAllNotesByUserIdFailure() throws Exception {
        when(noteService.getAllNoteByUserId("Jhon123")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/note/Jhon123")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
