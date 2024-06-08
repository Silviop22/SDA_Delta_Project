package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.dto.AddressDTO;
import com.ecommerce.ecommerce.exceptions.APIException;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.entity.Address;
import com.ecommerce.ecommerce.model.entity.User;
import com.ecommerce.ecommerce.repository.AddressRepo;
import com.ecommerce.ecommerce.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {

        String country = addressDTO.getCountry();
        String state = addressDTO.getState();
        String city = addressDTO.getCity();
        String pincode = addressDTO.getPincode();
        String street = addressDTO.getStreet();
        String buildingName = addressDTO.getBuildingName();

        Address addressForm = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state, city
                , pincode, street, buildingName);

        if (addressForm != null) {
            throw new APIException("Address already exist with addressId : " + addressForm.getAddressId());
        }
        Address address = modelMapper.map(addressDTO, Address.class);
        Address savedAddress = addressRepo.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddress() {
        List<Address> addresses = addressRepo.findAll();

        List<AddressDTO> addressDTOs = addresses.stream().map(address -> modelMapper.map(address, AddressDTO.class))
                .collect(Collectors.toList());
        return addressDTOs;
    }

    @Override
    public AddressDTO getAddress(Long addressId) {
        Address address = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, Address address) {
        Address addressForm = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(address.getCountry(), address.getState(), address.getCity()
                , address.getPincode(), address.getStreet(), address.getBuildingName());

        if (addressForm == null) {
            addressForm = addressRepo.findById(addressId).orElseThrow(() ->
                    new ResourceNotFoundException("Address", "addressId", addressId));

            addressForm.setCountry(address.getCountry());
            addressForm.setState(address.getState());
            addressForm.setCity(address.getState());
            addressForm.setPincode(address.getPincode());
            addressForm.setStreet(address.getStreet());
            addressForm.setBuildingName(address.getBuildingName());

            Address updatedAddress = addressRepo.save(addressForm);

            return modelMapper.map(updatedAddress, AddressDTO.class);
        } else {
            List<User> users = userRepo.findByAddresses(addressId);
            final Address a = addressForm;

            users.forEach(user -> user.getAddresses().add(a));

            deleteAddress(addressId);
            return modelMapper.map(addressForm, AddressDTO.class);
        }

    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressForm = addressRepo.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

        List<User> users = userRepo.findByAddresses(addressId);

        users.forEach(user -> {
            user.getAddresses().remove(addressForm);

            userRepo.save(user);
        });
        addressRepo.deleteById(addressId);

        return "Address with addressId : " + addressId + " has been deleted";

    }
}
