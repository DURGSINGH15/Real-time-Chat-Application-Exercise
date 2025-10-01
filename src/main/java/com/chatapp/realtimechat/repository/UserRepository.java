package com.chatapp.realtimechat.repository;

import com.chatapp.realtimechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 * By extending JpaRepository, we get a lot of CRUD (Create, Read, Update, Delete)
 * functionality for free, including methods like save(), findById(), findAll(), delete().

 * The @Repository annotation is a stereotype annotation that marks this as a Spring-managed
 * component. It also enables Spring's exception translation feature, which converts
 * database-specific exceptions into Spring's unified DataAccessException hierarchy.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their username.
     * Spring Data JPA will automatically generate the implementation for this method
     * based on its name. It will create a query equivalent to:
     * "SELECT u FROM User u WHERE u.username = :username"
     *
     * @param username The username to search for.
     * @return An Optional containing the found user, or an empty Optional if no user with that username exists.
     *         Using Optional is a best practice to avoid NullPointerExceptions.
     */
    Optional<User> findByUsername(String username);
}