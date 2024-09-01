package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Usuarios, Long> {

    @Modifying
    @Query("UPDATE Usuarios u SET u.estado = 0 WHERE u.id_usuario = :id")
    void softDelete(@Param("id") Long id);

    @Modifying
    @Query("UPDATE Usuarios u SET u.estado = 1 WHERE u.id_usuario = :userId")
    void activateUser(@Param("userId") Long userId);

    //@Query("SELECT u FROM Users u WHERE u.email = :email")
    @Query(value = "SELECT * FROM usuarios WHERE email = :email", nativeQuery = true)
    Usuarios findUserByEmail(@Param("email") String email);

}
