package com.moditech.ecommerce.controller;

import com.moditech.ecommerce.dto.AddressDto;
import com.moditech.ecommerce.model.Address;
import com.moditech.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@CrossOrigin("*")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/add")
    public ResponseEntity<Address> addAddress(@RequestBody AddressDto address) {
        Address addedAddress = addressService.addAddress(address);
        return ResponseEntity.ok(addedAddress);
    }

    @GetMapping("/list/{email}")
    public ResponseEntity<List<Address>> getListAddressByEmail(@PathVariable String email) {
        List<Address> addressList = addressService.getListAddressByEmail(email);
        return ResponseEntity.ok(addressList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable String id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable String id) {
        addressService.deleteAddressById(id);
        return ResponseEntity.ok().body("Address" + id + "successfully deleted.");
    }

    // a PUT mapping to update the default address for a given email
    @PutMapping("/default/{email}/{id}")
    public ResponseEntity<?> setDefaultAddress(@PathVariable String email, @PathVariable String id) {
        try {
            addressService.setDefaultAddressByEmail(email, id);
            return ResponseEntity.ok().body("Default address updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/default/address/{email}")
    public ResponseEntity<Address> getAddressByEmailAndDefault(@PathVariable String email) {
        Address address = addressService.getAddressByEmailAndDefault(email);
        return ResponseEntity.ok(address);
    }

}
