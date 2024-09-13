package com.geoprom.cl.api.backend.services.Users;

import com.geoprom.cl.api.backend.Repository.UsuariosRepository;
import com.geoprom.cl.api.backend.models.Usuarios;
import com.geoprom.cl.api.backend.services.Categorias.ICategoriasServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService
{
    Usuarios updatedUser(Long id, Usuarios userDetails);
}
