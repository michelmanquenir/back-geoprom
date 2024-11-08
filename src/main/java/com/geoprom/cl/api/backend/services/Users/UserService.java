package com.geoprom.cl.api.backend.services.Users;

import com.geoprom.cl.api.backend.models.Productos;
import com.geoprom.cl.api.backend.models.Request.LoginRequest;
import com.geoprom.cl.api.backend.models.Request.Usuarios.UpdateUsuarioRequest;
import com.geoprom.cl.api.backend.models.Usuarios;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<Usuarios> getUsers(Long user_id);

    Usuarios createUser(Usuarios user);

    void softDeleteUser(Long user_id);

    void activateUser(Long user_id);

    ResponseEntity<?> findUserByEmail(LoginRequest loginRequest);

    Usuarios findById(Long user_id);

    Usuarios updateUsuario(Long user_id, UpdateUsuarioRequest updateUsuarioRequest);

    void save(Usuarios user);

}
