package com.nab.ecommerce.services;

import com.nab.ecommerce.contants.Constants;
import com.nab.ecommerce.dto.product.ProductDto;
import com.nab.ecommerce.exception.BadRequestException;
import com.nab.ecommerce.exception.ProductNotExistException;
import com.nab.ecommerce.models.Brand;
import com.nab.ecommerce.models.Category;
import com.nab.ecommerce.models.Product;
import com.nab.ecommerce.payload.request.SearchRequest;
import com.nab.ecommerce.payload.response.PagedResponse;
import com.nab.ecommerce.repositories.ProductRepository;
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
  @PersistenceContext
  private EntityManager em;


  public PagedResponse<ProductDto> listProducts(UserPrincipal user, ProductDto productDto) {

    try {

      validatePageSize(productDto.searchRequest);

      Pageable pageable = PageRequest.of(productDto.searchRequest.page, productDto.searchRequest.size,
          Sort.Direction.fromString(productDto.searchRequest.sort), productDto.searchRequest.sortValue);

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
      logger.error("list product error");
      return null;
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
      if (productDto.getBrandId() > 0) {
        listParams.add(builder.equal(root.get("brandId"), productDto.getBrandId()));
      }
      if (productDto.getCategoryId() > 0) {
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

  public PagedResponse<ProductDto> searchProducts(UserPrincipal user, ProductDto productDto) {

    try {

      CriteriaQuery<Product> query = getParamsFromRequest(productDto);
      TypedQuery<Product> typedQuery = em.createQuery(query);
      typedQuery.setFirstResult(productDto.searchRequest.page * productDto.searchRequest.size);
      typedQuery.setMaxResults(productDto.searchRequest.size);
      List<Product> products = typedQuery.getResultList();

      List<ProductDto> productDtos = new ArrayList<>();
      for (Product product : products) {
        ProductDto tmp = getDtoFromProduct(product);
        productDtos.add(tmp);
      }
      return new PagedResponse<>(productDtos, productDto.searchRequest.page, productDto.searchRequest.size,
          products.size(), products.size() / productDto.searchRequest.size + 1, false);

    } catch (BadRequestException ex) {
      return null;
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

  public static Product getProductFromDto(ProductDto productDto, Category category) {
    Product product = new Product(productDto, category);
    return product;
  }

  public void addProduct(ProductDto productDto, Category category, Brand brand) {
    Product product = getProductFromDto(productDto, category, brand);
    productRepository.save(product);
  }

  public void updateProduct(Integer productID, ProductDto productDto, Category category) {
    Product product = getProductFromDto(productDto, category);
    product.setId(productID);
    productRepository.save(product);
  }


  public Product getProductById(Integer productId) throws ProductNotExistException {
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (!optionalProduct.isPresent()) {
      throw new ProductNotExistException("Product id is invalid " + productId);
    }
    return optionalProduct.get();
  }


  private void validatePageSize(SearchRequest searchRequest) {
    if (searchRequest.page < 0) {
      throw new BadRequestException("Page can not be smaller than 0");
    }

    if (searchRequest.size > Constants.MAX_PAGE_SIZE) {
      throw new BadRequestException("Size can not be > MAX");
    }

    if (!searchRequest.sort.equals(Sort.Direction.DESC.name()) && !searchRequest.sort
        .equals(Sort.Direction.ASC.name())) {

      throw new BadRequestException("Sort is invalid");
    }

  }

}
