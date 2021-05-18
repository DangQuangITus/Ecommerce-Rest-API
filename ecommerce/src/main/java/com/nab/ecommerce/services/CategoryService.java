package com.nab.ecommerce.services;

import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.repositories.CategoryRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository categoryrepository;

	public List<Category> listCategories() {
		return categoryrepository.findAll();
	}

	public void createCategory(Category category) {
		categoryrepository.save(category);
	}

	public Category readCategory(String categoryName) {
		return categoryrepository.findByCategoryName(categoryName);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryrepository.findById(categoryId);
	}
//
//	public void updateCategory(Integer categoryID, Category newCategory) {
//		Category category = categoryrepository.findById(categoryID).get();
//		category.setCategoryName(newCategory.getCategoryName());
//		category.setDescription(newCategory.getDescription());
//		category.setProducts(newCategory.getProducts());
//		category.setImageUrl(newCategory.getImageUrl());
//
//		categoryrepository.save(category);
//	}
}
