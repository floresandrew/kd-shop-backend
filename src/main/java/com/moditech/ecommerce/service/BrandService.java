package com.moditech.ecommerce.service;

import com.moditech.ecommerce.model.Brand;
import com.moditech.ecommerce.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    public List<Brand> getBrandList() {
        return brandRepository.findAll();
    }

    public Brand getBrandById(String id) {
        return brandRepository.findById(id).orElse(null);
    }

    public Brand updateBrandById(String id, Brand brand) {
        Brand brand1 = brandRepository.findById(id).orElse(null);
        assert brand1 != null;
        brand1.setBrandName(brand.getBrandName());
        return brandRepository.save(brand1);
    }

    public void deleteBrandById(String id) {
        Brand brand = brandRepository.findById(id).orElse(null);
        assert brand != null;
        brandRepository.deleteById(id);
    }
}
