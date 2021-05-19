package com.nab.ecommerce.services;

import com.nab.ecommerce.contants.Constants;
import com.nab.ecommerce.dto.product.ProductDto;
import com.nab.ecommerce.exception.BadRequestException;
import com.nab.ecommerce.exception.ProductNotExistException;
import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.models.product.Product;
import com.nab.ecommerce.models.product.ProductStatus;
import com.nab.ecommerce.payload.request.SearchRequest;
import com.nab.ecommerce.payload.response.PagedResponse;
import com.nab.ecommerce.repositories.ProductRepository;
import com.nab.ecommerce.repositories.ProductStatusRepository;
import com.nab.ecommerce.security.UserPrincipal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private static final Logger logger = LogManager.getLogger(ProductService.class);

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductStatusRepository productStatusRepository;
  @PersistenceContext
  private EntityManager em;
  @Autowired
  CategoryService categoryService;
  @Autowired
  BrandService brandService;

  public PagedResponse<ProductDto> listProducts(ProductDto productDto) {

    try {

      validateMetaDataRequest(productDto.metadataRequest);

      Pageable pageable = PageRequest.of(productDto.metadataRequest.page, productDto.metadataRequest.size,
          Sort.Direction.fromString(productDto.metadataRequest.sort), productDto.metadataRequest.sortValue);

      Page<Product> products = productRepository.findAll(pageable);
      if (products.getTotalElements() == 0) {
        return new PagedResponse<>(Collections.emptyList(), products.getNumber(), products.getSize(),
            products.getTotalElements(), products.getTotalPages(), products.isLast());
      }

      List<ProductDto> productDtos = new ArrayList<>();
      for (Product product : products) {
        ProductDto tmp = getDtoFromProduct(product);
        productDtos.add(tmp);
      }
      return new PagedResponse<>(productDtos, products.getNumber(), products.getSize(),
          products.getTotalElements(), products.getTotalPages(), products.isLast());

    } catch (BadRequestException ex) {
      logger.error("Metadata request invalid");
      return new PagedResponse<>(Collections.emptyList(), 0, 0, 0, 0, false);
    } catch (Exception ex) {
      logger.error(String.format("Get all product error, %s", ex.getMessage()));
      return new PagedResponse<>(Collections.emptyList(), 0, 0, 0, 0, false);
    }

  }

  private CriteriaQuery<Product> getParamsFromRequest(ProductDto productDto) {

    try {

      CriteriaBuilder builder = em.getCriteriaBuilder();
      CriteriaQuery<Product> query = builder.createQuery(Product.class);
      Root<Product> root = query.from(Product.class);

      List<Predicate> listParams = new ArrayList<>();

      if (StringUtils.isNotEmpty(productDto.getName())) {
        listParams.add(builder.like(root.get("name"), String.format("%%%s%%", productDto.getName())));
      }
      if (productDto.getBrandId() != null && productDto.getBrandId() > 0) {
        listParams.add(builder.equal(root.get("brandId"), productDto.getBrandId()));
      }
      if (productDto.getCategoryId() != null && productDto.getCategoryId() > 0) {
        listParams.add(builder.equal(root.get("categoryId"), productDto.getCategoryId()));
      }
      if (StringUtils.isNotEmpty(productDto.getColor())) {
        listParams.add(builder.like(root.get("color"), String.format("%%%s%%", productDto.getColor())));
      }
      if (productDto.getPrice() > 0) {
        listParams.add(builder.greaterThanOrEqualTo(root.get("price"), productDto.getPrice()));
      }

      query.where(listParams.toArray(new Predicate[0]));
      return query;

    } catch (Exception e) {
      logger.error("Cannot get params from request");
      throw new BadRequestException("Cannot get params from request");
    }
  }

  public PagedResponse<ProductDto> searchProducts(ProductDto productDto) {

    try {

      validateMetaDataRequest(productDto.metadataRequest);

      CriteriaQuery<Product> query = getParamsFromRequest(productDto);
      TypedQuery<Product> typedQuery = em.createQuery(query);
      typedQuery.setFirstResult(productDto.metadataRequest.page * productDto.metadataRequest.size);
      typedQuery.setMaxResults(productDto.metadataRequest.size);
      List<Product> products = typedQuery.getResultList();
      if (products.size() == 0) {
        throw new ProductNotExistException("Product not found.");
      }

      List<ProductDto> productDtos = new ArrayList<>();
      for (Product product : products) {
        ProductDto tmp = getDtoFromProduct(product);
        productDtos.add(tmp);
      }

      return new PagedResponse<>(productDtos, productDto.metadataRequest.page, productDto.metadataRequest.size,
          products.size(), products.size() / productDto.metadataRequest.size + 1, false);

    } catch (BadRequestException ex) {
      logger.error("Metadata request invalid");
      return new PagedResponse<>(Collections.emptyList(), 0, 0, 0, 0, false);
    } catch (ProductNotExistException e) {
      return new PagedResponse<>(Collections.emptyList(), 0, 0, 0, 0, false);
    }

  }

  public static ProductDto getDtoFromProduct(Product product) {
    ProductDto productDto = new ProductDto(product);
    return productDto;
  }

  public static Product getProductFromDto(ProductDto productDto, Category category, Brand brand) {
    Product product = new Product(productDto, category, brand);
    return product;
  }


  public Product addProduct(ProductDto productDto, Category category, Brand brand) {
    Product product = getProductFromDto(productDto, category, brand);
    ProductStatus productStatus = new ProductStatus(product);
    product.setProductStatus(productStatus);
    productStatus = productStatusRepository.save(productStatus);
    product.setProductStatus(productStatus);
    return productRepository.save(product);
  }

  public Product updateProduct(Integer productID, ProductDto productDto, Category category, Brand brand) {
    Product product = getProductFromDto(productDto, category, brand);
    product.setId(productID);
    ProductStatus productStatus = new ProductStatus(product);
    productStatus.setProductId(productID);
    productStatusRepository.save(productStatus);
    product.setProductStatus(productStatus);
    return productRepository.save(product);
  }

  public void deleteProduct(Integer productID) {

    try {

      Product product = getProductById(productID);
      product.setId(productID);
      product.getProductStatus().setAvailable(false);
      productRepository.save(product);

    } catch (ProductNotExistException e) {
      logger.error("Product not found.");
      throw new ProductNotExistException("Product id is invalid " + productID);
    }

  }

  public Product getProductById(Integer productId) throws ProductNotExistException {
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (!optionalProduct.isPresent()) {
      throw new ProductNotExistException("Product id is invalid " + productId);
    }
    return optionalProduct.get();
  }


  public boolean validateProductDto(ProductDto productDto) {

    Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
    Optional<Brand> optionalBrand = brandService.readBrand(productDto.getBrandId());

    if (!optionalCategory.isPresent()) {
      logger.error("Category is invalid");
      throw new BadRequestException("Category is invalid");
    }

    if (!optionalBrand.isPresent()) {
      logger.error("Brand is invalid");
      throw new BadRequestException("Brand is invalid");
    }

    if (productDto.getPrice() < 0 || productDto.getStock() < 0) {
      logger.error("Product stock or price is invalid");
      throw new BadRequestException("Product stock or price is invalid");
    }

    return true;
  }

  private void validateMetaDataRequest(SearchRequest metadataRequest) {

    if (metadataRequest.page < 0) {
      throw new BadRequestException("Page can not be smaller than 0");
    }

    if (metadataRequest.size > Constants.MAX_PAGE_SIZE) {
      throw new BadRequestException("Size can not be > MAX");
    }

    if (!metadataRequest.sort.equals(Sort.Direction.DESC.name()) && !metadataRequest.sort
        .equals(Sort.Direction.ASC.name())) {

      throw new BadRequestException("Sort is invalid");
    }

  }

}
