package com.nab.ecommerce.repositories;

import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

  Category findByBrandName(String brandName);

}
