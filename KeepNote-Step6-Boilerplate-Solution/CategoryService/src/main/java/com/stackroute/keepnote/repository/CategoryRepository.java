package com.stackroute.keepnote.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.model.Category;

/*
* This class is implementing the MongoRepository interface for User.
* Annotate this class with @Repository annotation
* */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

	List<Category> findByCategoryCreatedBy(String categoryCreatedBy);
	void deleteByCategoryCreatedBy(String categoryCreatedBy);
}
