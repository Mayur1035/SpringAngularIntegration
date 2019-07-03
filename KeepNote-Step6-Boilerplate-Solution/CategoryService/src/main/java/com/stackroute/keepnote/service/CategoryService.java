package com.stackroute.keepnote.service;

import java.util.List;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;

public interface CategoryService {
	
	/*
	 * Should not modify this interface. You have to implement these methods in
	 * corresponding Impl classes
	 */
    
    

    Category createCategory(Category category);

    boolean deleteCategory(String id);

    Category updateCategory(Category category) throws CategoryDoesNoteExistsException;

    Category getCategoryByCategoryId(String id) throws CategoryDoesNoteExistsException;

    List<Category> getAllCategoryByUserId(String userId);

}
