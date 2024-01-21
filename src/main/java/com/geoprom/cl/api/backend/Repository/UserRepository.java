package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Modifying
    @Query("UPDATE Users u SET u.status = 0 WHERE u.id = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Users u SET u.status = 1 WHERE u.id = :userId")
    void activateUser(@Param("userId") Long userId);

    //@Query("SELECT u FROM Users u WHERE u.email = :email")
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Users findUserByEmail(@Param("email") String email);

}
