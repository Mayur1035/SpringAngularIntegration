package com.stackroute.keepnote.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

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
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	CategoryRepository categoryRepository;
	
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
	public Category updateCategory(Category category) throws CategoryDoesNoteExistsException {
		Date updatedDate = new Date();
		//String date = LocalDateTime.now().toString();
		if(null != category) {
			category.setCategoryCreationDate(updatedDate);
			Category categoryUpdated = categoryRepository.save(category);
			return categoryUpdated;
		}else {
			throw new CategoryDoesNoteExistsException("Not found");
		}
	}

	@Override
	public Category getCategoryByCategoryId(String id) throws CategoryDoesNoteExistsException {
		// TODO Auto-generated method stub
		return categoryRepository.findById(id).get();
	}

	@Override
	public List<Category> getAllCategoryByUserId(String userId) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryCreatedBy(userId);
	}
}
