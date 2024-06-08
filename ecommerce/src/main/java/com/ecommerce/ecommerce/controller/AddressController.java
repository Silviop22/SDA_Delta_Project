package com.ecommerce.ecommerce.controller;

import com.ecommerce.ecommerce.dto.AddressDTO;
import com.ecommerce.ecommerce.model.entity.Address;
import com.ecommerce.ecommerce.services.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "ecommerce")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO saveAddressDTO = addressService.createAddress(addressDTO);
        return new ResponseEntity<AddressDTO>(saveAddressDTO, HttpStatus.CREATED);
    }
    @GetMapping("/addresses")
    public ResponseEntity<List<AddressDTO>> getAddress() {
        List<AddressDTO> addressDTOs = addressService.getAddress();

        return new ResponseEntity<List<AddressDTO>>(addressDTOs, HttpStatus.OK);
    }

    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable Long addressId) {
        AddressDTO addressDTO = addressService.getAddress(addressId);

        return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId, @RequestBody Address address) {
        AddressDTO addressDTO = addressService.updateAddress(addressId, address);
        return new ResponseEntity<AddressDTO>(addressDTO,HttpStatus.OK);
    }
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long addressId) {
        String status = addressService.deleteAddress(addressId);
        return new ResponseEntity<String>(status, HttpStatus.OK);
    }
}
