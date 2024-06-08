package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.AddressDTO;
import com.ecommerce.ecommerce.model.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddress();

    AddressDTO getAddress(Long addressId);

    AddressDTO updateAddress(Long addressId, Address address);

    String deleteAddress(Long addressId);
}
