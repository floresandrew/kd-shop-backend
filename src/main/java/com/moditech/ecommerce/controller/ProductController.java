package com.moditech.ecommerce.controller;

import com.moditech.ecommerce.model.Product;
import com.moditech.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/create")
    private ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @GetMapping("/list")
    List<Product> getAllProducts(@RequestParam(name = "categoryName", required = false) String categoryName) {
        System.out.print("category name here: ");
        if (categoryName != null) {
            System.out.print(categoryName.trim());
            return productService.getProductsByCategory(categoryName.trim());
        } else {
            return productService.getAllProducts();
        }
    }

    // @GetMapping("/bestProducts")
    // private List<TopSoldProductDto> getBestProducts() {
    // return productService.getTopSoldProducts();
    // }

    // @GetMapping("/getProductsWithZeroSold")
    // private List<TopSoldProductDto> getProductsWithZeroSold() {
    // return productService.getProductsWithZeroSold();
    // }

    @GetMapping("/{id}")
    private ResponseEntity<Product> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/delete/{productId}")
    private String deleteProductById(@PathVariable("productId") String productId) {
        productService.deleteProductById(productId);
        return "product deleted";
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        Product products = productService.updateProduct(id, product);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/isPreOrder")
    private ResponseEntity<List<Product>> getProductsByIsPreOrder() {
        List<Product> product = productService.getProductsByIsPreOrder();
        return ResponseEntity.ok(product);
    }

    @GetMapping("/isNotPreOrder")
    private ResponseEntity<List<Product>> getProductsThatIsNotPreOrder() {
        List<Product> product = productService.getProductsThatIsNotPreOrder();
        return ResponseEntity.ok(product);
    }

    // @PutMapping("/product-variation/{id}")
    // private ResponseEntity<Product> addProductVariation(@PathVariable String id,
    // @RequestBody Product product) {
    // Product products = productService.addProductVariation(id, product);
    // return ResponseEntity.ok(products);
    // }
    //
    // @GetMapping("/isAd")
    // private ResponseEntity<List<TopSoldProductDto>> getProductsByIsAd() {
    // List<TopSoldProductDto> product = productService.getProductsByIsAd();
    // return ResponseEntity.ok(product);
    // }
    //
    // @DeleteMapping("/{productId}/variations/{variationName}")
    // public ResponseEntity<String> removeProductVariation(
    // @PathVariable String productId,
    // @PathVariable String variationName) {
    // try {
    // productService.removeProductVariation(productId, variationName);
    // return ResponseEntity.ok("Product variation removed successfully");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    // }
    // }

    // @PutMapping("/{productId}/variations/{oldVariationName}")
    // public ResponseEntity<String> updateProductVariation(
    // @PathVariable String productId,
    // @PathVariable String oldVariationName,
    // @RequestBody ProductVariationsDto updatedVariation) {
    // try {
    // productService.updateProductVariation(productId, oldVariationName,
    // updatedVariation);
    // return ResponseEntity.ok("Product variation updated successfully");
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    // }
    // }
    //
    // @GetMapping("/{productId}/variations/{variationName}")
    // public ResponseEntity<ProductVariations> getProductVariationByName(
    // @PathVariable String productId,
    // @PathVariable String variationName) {
    // try {
    // ProductVariations variation =
    // productService.getProductVariationByName(productId, variationName);
    // return ResponseEntity.ok(variation);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    // }
    // }
}
