package com.ecommerce.ecommerce.services;

import com.ecommerce.ecommerce.config.AppConstants;
import com.ecommerce.ecommerce.dto.*;
import com.ecommerce.ecommerce.exceptions.APIException;
import com.ecommerce.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.ecommerce.model.entity.*;
import com.ecommerce.ecommerce.repository.AddressRepo;
import com.ecommerce.ecommerce.repository.CartRepo;
import com.ecommerce.ecommerce.repository.RoleRepo;
import com.ecommerce.ecommerce.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private AddressRepo addressRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepo cartRepo;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);


            Optional<Role> role = roleRepo.findById(AppConstants.USER_ID);
            if (role.isPresent()) {
                user.getRoles().add(role.get());

            } else {
                throw new APIException("Role not found with id: " + AppConstants.USER_ID);
            }

            String country = userDTO.getAddress().getCountry();
            String state = userDTO.getAddress().getState();
            String city = userDTO.getAddress().getCity();
            String pincode = userDTO.getAddress().getPincode();
            String street = userDTO.getAddress().getStreet();
            String buildingName = userDTO.getAddress().getBuildingName();

            Address address = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
                    city, pincode, street, buildingName);

            if (address == null) {
                address = new Address(country, state, city, pincode, street, buildingName);

                address = addressRepo.save(address);
            }
            user.setAddresses(List.of(address));

            User registerUser = userRepo.save(user); // save the user first

            Cart cart = new Cart();
            cart.setUser(registerUser);
            cart = cartRepo.save(cart); // then save the cart
            registerUser.setCart(cart); // set the cart to the user




            userDTO = modelMapper.map(registerUser, UserDTO.class);

            userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

            return userDTO;
        } catch (DataIntegrityViolationException e) {
            throw new APIException("User already exists with emailId: " + userDTO.getEmail());


        }
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<User> pageUser = userRepo.findAll(pageDetails);

        List<User> users = pageUser.getContent();

        if (users.size() == 0) {
            throw new APIException("No user exist !");
        }
        List<UserDTO> userDTOs = users.stream().map(user -> {
            UserDTO dto = modelMapper.map(user, UserDTO.class);

            if (user.getAddresses().size() != 0) {
                dto.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));
            }
            CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

            List<ProductDTO> products = user.getCart().getCartItems().stream()
                    .map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

            dto.setCart(cart);
            dto.getCart().setProducts(products);
            return dto;
        }).collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(userDTOs);
        userResponse.setPageNumber(pageUser.getNumber());
        userResponse.setPageSize(pageUser.getSize());
        userResponse.setTotalElements(pageUser.getTotalElements());
        userResponse.setTotalPages(pageUser.getTotalPages());
        userResponse.setLastPage(pageUser.isLast());

        return userResponse;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

        CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

        List<ProductDTO> products = user.getCart().getCartItems().stream()
                .map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        userDTO.setCart(cart);

        userDTO.getCart().setProducts(products);

        return userDTO;
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        String encodedPass = passwordEncoder.encode(userDTO.getPassword());

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setEmail(userDTO.getEmail());
        user.setPassword(encodedPass);

        if (userDTO.getAddress() != null) {
            String country = userDTO.getAddress().getCountry();
            String state = userDTO.getAddress().getState();
            String city = userDTO.getAddress().getCity();
            String pincode = userDTO.getAddress().getPincode();
            String street = userDTO.getAddress().getStreet();
            String buildingName = userDTO.getAddress().getBuildingName();

            Address address = addressRepo.findByCountryAndStateAndCityAndPincodeAndStreetAndBuildingName(country, state,
                    city, pincode, street, buildingName);

            if (address == null) {
                address = new Address(country, state, city, pincode, street, buildingName);

                address = addressRepo.save(address);

                user.setAddresses(List.of(address));
            }
        }

        userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.setAddress(modelMapper.map(user.getAddresses().stream().findFirst().get(), AddressDTO.class));

        CartDTO cart = modelMapper.map(user.getCart(), CartDTO.class);

        List<ProductDTO> products = user.getCart().getCartItems().stream()
                .map(item -> modelMapper.map(item.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        userDTO.setCart(cart);

        userDTO.getCart().setProducts(products);

        return userDTO;
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        Cart cart = user.getCart();
        if (cart != null) {
            List<CartItem> cartItems = user.getCart().getCartItems();
            Long cartId = user.getCart().getCartId();

            cartItems.forEach(item -> {

                Long productId = item.getProduct().getProductId();

                cartService.deleteProductFromCart(cartId, productId);
            });
        }
            userRepo.delete(user);

            return "User with userId " + userId + " deleted successfully!!!";
        }
    }


