package com.stackroute.keepnote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Note;

@Repository
public interface NoteListRepository extends MongoRepository<Note, String>{
	
	List<Note> findByCreatedBy(String createdBy);
	void deleteByCreatedBy(String createdBy);
}
