package com.moditech.ecommerce.service;

import com.moditech.ecommerce.model.Product;
import com.moditech.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    // public List<TopSoldProductDto> getAllProducts() {
    // // Create a custom query to sum the sold quantities for each product
    // Aggregation aggregation = Aggregation.newAggregation(
    // Aggregation.unwind("productVariationsList"), // Unwind the variations array
    // Aggregation.group("_id")
    // .first("id").as("id")
    // .first("barcode").as("barcode")
    // .first("productName").as("productName")
    // .first("productImage").as("productImage")
    // .first("description").as("description")
    // .first("isAd").as("isAd")
    // .addToSet("productVariationsList").as("productVariationsList")
    // .sum("productVariationsList.sold").as("totalSold"), // Sum the sold
    // quantities
    // Aggregation.match(where("totalSold").gt(0)), // Exclude documents with
    // totalSold of 0
    // Aggregation.sort(Sort.Direction.DESC, "totalSold") // Sort by total sold in
    // descending order
    // );
    //
    // // Execute the aggregation query
    // AggregationResults<TopSoldProductDto> results =
    // mongoTemplate.aggregate(aggregation, "product", TopSoldProductDto.class);
    // // Get the result list
    // return results.getMappedResults();
    // }

    // public List<TopSoldProductDto> getTopSoldProducts() {
    // // Create a custom query to sum the sold quantities for each product
    // Aggregation aggregation = Aggregation.newAggregation(
    // Aggregation.unwind("productVariationsList"), // Unwind the variations array
    // Aggregation.group("_id")
    // .first("id").as("id")
    // .first("barcode").as("barcode")
    // .first("productName").as("productName")
    // .first("productImage").as("productImage")
    // .first("description").as("description")
    // .first("isAd").as("isAd")
    // .addToSet("productVariationsList").as("productVariationsList")
    // .sum("productVariationsList.sold").as("totalSold"), // Sum the sold
    // quantities
    // Aggregation.match(where("totalSold").gt(0)), // Exclude documents with
    // totalSold of 0
    // Aggregation.sort(Sort.Direction.DESC, "totalSold"), // Sort by total sold in
    // descending order
    // Aggregation.limit(8) // Limit the result to the top 8
    // );
    //
    // // Execute the aggregation query
    // AggregationResults<TopSoldProductDto> results =
    // mongoTemplate.aggregate(aggregation, "product", TopSoldProductDto.class);
    // // Get the result list
    // return results.getMappedResults();
    // }

    // public List<TopSoldProductDto> getProductsWithZeroSold() {
    // // Create a custom query to sum the sold quantities for each product
    // Aggregation aggregation = Aggregation.newAggregation(
    // Aggregation.unwind("productVariationsList"), // Unwind the variations array
    // Aggregation.group("_id")
    // .first("id").as("id")
    // .first("barcode").as("barcode")
    // .first("productName").as("productName")
    // .first("productImage").as("productImage")
    // .first("description").as("description")
    // .first("isAd").as("isAd")
    // .addToSet("productVariationsList").as("productVariationsList")
    // .sum("productVariationsList.sold").as("totalSold"), // Sum the sold
    // quantities
    // Aggregation.match(where("totalSold").is(0)), // Include only documents with
    // totalSold of 0
    // Aggregation.sort(Sort.Direction.DESC, "totalSold") // Sort by total sold in
    // descending order
    // );
    //
    // // Execute the aggregation query
    // AggregationResults<TopSoldProductDto> results =
    // mongoTemplate.aggregate(aggregation, "product", TopSoldProductDto.class);
    //
    // return results.getMappedResults();
    // }

    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProductById(String productId) {
        productRepository.deleteById(productId);
    }

    public Product updateProduct(String id, Product product) {
        Product setProduct = productRepository.findById(id).orElse(null);

        assert setProduct != null;

        if (product.getProductName() != null && !product.getProductName().isEmpty()) {
            setProduct.setProductName(product.getProductName());
        }

        if (product.getProductImage() != null && !product.getProductImage().isEmpty()) {
            setProduct.setProductImage(product.getProductImage());
        }

        if (product.getDescription() != null && !product.getDescription().isEmpty()) {
            setProduct.setDescription(product.getDescription());
        }

        if (product.getCategoryName() != null && !product.getCategoryName().isEmpty()) {
            setProduct.setCategoryName(product.getCategoryName());
        }

        if (product.getBrandName() != null && !product.getBrandName().isEmpty()) {
            setProduct.setBrandName(product.getBrandName());
        }

        if (product.getPrice() != null) {
            setProduct.setPrice(product.getPrice());
        }

        if (product.getQuantity() != null) {
            setProduct.setQuantity(product.getQuantity());
        }

        if (!setProduct.getIsPreOrder().equals(product.getIsPreOrder())) {
            setProduct.setIsPreOrder(product.getIsPreOrder());
        }

        if (!setProduct.getPreOrderEta().equals(product.getPreOrderEta())) {
            setProduct.setPreOrderEta(product.getPreOrderEta());
        }

        productRepository.save(setProduct);

        return setProduct;
    }

    public List<Product> getProductsByIsPreOrder() {
        Boolean isPreOrder = true;
        return productRepository.findByIsPreOrder(isPreOrder);
    }

    public List<Product> getProductsThatIsNotPreOrder() {
        Boolean isPreOrder = false;
        return productRepository.findByIsPreOrder(isPreOrder);
    }

    // public Product addProductVariation(String id, Product addProductVariation) {
    // Product product = productRepository.findById(id).orElse(null);
    // assert product != null;
    // product.setProductVariationsList(addProductVariation.getProductVariationsList());
    // return productRepository.save(product);
    // }

    // public List<TopSoldProductDto> getProductsByIsAd() {
    // Aggregation aggregation = Aggregation.newAggregation(
    // Aggregation.match(Criteria.where("isAd").is(true)), // Match documents with
    // isAd=true
    // Aggregation.unwind("productVariationsList"), // Unwind the variations array
    // Aggregation.group("_id")
    // .first("id").as("id")
    // .first("barcode").as("barcode")
    // .first("productName").as("productName")
    // .first("productImage").as("productImage")
    // .first("description").as("description")
    // .first("isAd").as("isAd")
    // .addToSet("productVariationsList").as("productVariationsList")
    // .sum("productVariationsList.sold").as("totalSold"), // Sum the sold
    // quantities
    // Aggregation.match(where("totalSold").is(0))
    // );
    //
    //// Execute the aggregation query
    // AggregationResults<TopSoldProductDto> results =
    // mongoTemplate.aggregate(aggregation, "product", TopSoldProductDto.class);
    //
    // return results.getMappedResults();
    //
    // }

    public List<Product> getProductsWithinLastMonth() {
        long monthlyTimestamp = 30;
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusDays(monthlyTimestamp);
        return productRepository.findByCreatedAtAfter(oneMonthAgo);
    }

    // public void removeProductVariation(String productId, String variationName) {
    // Product product = productRepository.findById(productId).orElse(null);
    //
    // if (product != null) {
    // List<ProductVariations> variationsList = product.getProductVariationsList();
    //
    // variationsList.removeIf(variation ->
    // variation.getVariationName().equals(variationName.trim()));
    //
    // productRepository.save(product);
    // } else {
    // System.out.println("Product not found with ID: " + productId);
    // }
    // }

    // public void updateProductVariation(String productId, String oldVariationName,
    // ProductVariationsDto updatedVariation) {
    // Product product = productRepository.findById(productId).orElse(null);
    //
    // if (product != null) {
    // List<ProductVariations> variationsList = product.getProductVariationsList();
    //
    // for (ProductVariations variation : variationsList) {
    // if (variation.getVariationName().equals(oldVariationName.trim())) {
    // // Update the fields of the existing variation with the values from the
    // updatedVariation
    // variation.setVariationName(updatedVariation.getVariationName()); // Update
    // the name
    // variation.setImgUrl(updatedVariation.getImgUrl());
    // variation.setDescription(updatedVariation.getDescription());
    // variation.setPrice(updatedVariation.getPrice());
    // variation.setQuantity(updatedVariation.getQuantity());
    // // Add more fields to update as needed
    // productRepository.save(product);
    // return;
    // }
    // }
    // } else {
    // System.out.println("Product not found with ID: " + productId);
    // }
    // }

    // public ProductVariations getProductVariationByName(String productId, String
    // variationName) {
    // Product product = productRepository.findById(productId).orElse(null);
    //
    // if (product != null) {
    // List<ProductVariations> variationsList = product.getProductVariationsList();
    //
    // for (ProductVariations variation : variationsList) {
    // if (variation.getVariationName().equals(variationName.trim())) {
    // return variation;
    // }
    // }
    // } else {
    // System.out.println("Product not found with ID: " + productId);
    // }
    //
    // return null;
    // }

}
