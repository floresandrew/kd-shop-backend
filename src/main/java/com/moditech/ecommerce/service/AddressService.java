package com.moditech.ecommerce.service;

import com.moditech.ecommerce.dto.AddressDto;
import com.moditech.ecommerce.model.Address;
import com.moditech.ecommerce.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public Address addAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setFullName(addressDto.getFullName());
        address.setEmail(addressDto.getEmail());
        address.setAddressLine1(addressDto.getAddressLine1());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setPostalCode(addressDto.getPostalCode());
        address.setContactNumber(addressDto.getContactNumber());
        return addressRepository.save(address);
    }

    public List<Address> getListAddressByEmail(String email) {
        return addressRepository.findByEmail(email);
    }

    public Address getAddressById(String id) {
        return addressRepository.findById(id).orElse(null);
    }

    public void deleteAddressById(String id) {
        addressRepository.deleteById(id);
    }

    public void updateAddress(String id, AddressDto addressDto) {
        Address address = addressRepository.findById(id).orElse(null);

        if (address != null) {
            // Check and update each field only if it has changed
            if (addressDto.getAddressLine1() != null) {
                address.setAddressLine1(addressDto.getAddressLine1());
            }

            if (addressDto.getFullName() != null) {
                address.setFullName(addressDto.getFullName());
            }

            if (addressDto.getCity() != null) {
                address.setCity(addressDto.getCity());
            }

            if (addressDto.getCountry() != null) {
                address.setCountry(addressDto.getCountry());
            }

            if (addressDto.getPostalCode() != null) {
                address.setPostalCode(addressDto.getPostalCode());
            }

            if (addressDto.getContactNumber() != null) {
                address.setContactNumber(addressDto.getContactNumber());
            }

            addressRepository.save(address);
        }
    }

    public void setDefaultAddressByEmail(String email, String addressId) {
        // find the current default address for this email and set its isDefault to
        // false
        Address currentDefault = addressRepository.findByEmailAndIsDefault(email, true);
        if (currentDefault != null) {
            currentDefault.setIsDefault(false);
            addressRepository.save(currentDefault);
        }
        // find the new default address by its id and set its isDefault to true
        Address newDefault = addressRepository.findById(addressId).orElse(null);
        if (newDefault != null) {
            newDefault.setIsDefault(true);
            addressRepository.save(newDefault);
        }
    }

    public Address getAddressByEmailAndDefault(String email) {
        return addressRepository.findByEmailAndIsDefault(email, true);
    }

}
