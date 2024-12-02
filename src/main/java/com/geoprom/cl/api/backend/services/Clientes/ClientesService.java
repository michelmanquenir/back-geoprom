package com.geoprom.cl.api.backend.services.Clientes;

import com.geoprom.cl.api.backend.models.Clientes;
import com.geoprom.cl.api.backend.models.Usuarios;

import java.util.List;

public interface ClientesService {

    List<Clientes> getClientes(Long user_id);

    Clientes findById(Long cliente_id);

    void save(Clientes cliente);

    Clientes crearCliente(Clientes clientes);

}
