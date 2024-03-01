package com.moditech.ecommerce.controller;

import com.moditech.ecommerce.model.Brand;
import com.moditech.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@CrossOrigin("*")
public class BrandController {

    @Autowired
    BrandService brandService;

    @PostMapping("/create")
    ResponseEntity<Brand> createBrand(@RequestBody Brand brand) {
        Brand brand1 = brandService.createBrand(brand);
        return ResponseEntity.ok(brand1);
    }

    @GetMapping("/list")
    ResponseEntity<List<Brand>> getBrandList() {
        List<Brand> brandList = brandService.getBrandList();
        return ResponseEntity.ok(brandList);
    }

    @GetMapping("/{id}")
    ResponseEntity<Brand> getBrandById(@PathVariable String id) {
        Brand brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<Brand> updateBrandById(@PathVariable("id") String id, @RequestBody Brand brand) {
        Brand brand1 = brandService.updateBrandById(id, brand);
        return ResponseEntity.ok(brand1);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<String> deleteBrandById(@PathVariable("id") String id) {
        brandService.deleteBrandById(id);
        return ResponseEntity.ok("brand deleted");
    }

}
