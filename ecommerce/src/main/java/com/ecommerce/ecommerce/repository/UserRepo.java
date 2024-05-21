package com.ecommerce.ecommerce.repository;

import com.ecommerce.ecommerce.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN fetch u.addresses a where a.addressId = ?1")
    List<User> findByAddresses(Long addressId);

    Optional<User>findByEmail(String email);


    

    @Query("SELECT u FROM User u WHERE u.firstName = :firstName AND u.lastName = :lastName")
    List<User> findByFirstNameAndLastName(@Param("firstName")String firstName,@Param("lastName") String lastName);
}
