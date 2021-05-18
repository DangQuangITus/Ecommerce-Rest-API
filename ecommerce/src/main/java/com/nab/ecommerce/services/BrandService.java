package com.nab.ecommerce.services;

import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.repositories.BrandRepository;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BrandService {

  @Autowired
  private BrandRepository brandRepository;

  public List<Brand> listBrands() {
    return brandRepository.findAll();
  }

  public void createBrand(Brand brand) {
    brandRepository.save(brand);
  }

  public Category readBrand(String brandName) {
    return brandRepository.findByBrandName(brandName);
  }

  public Optional<Brand> readBrand(Integer brandId) {
    return brandRepository.findById(brandId);
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
