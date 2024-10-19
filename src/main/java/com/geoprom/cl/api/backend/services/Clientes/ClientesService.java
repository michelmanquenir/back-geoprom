package com.geoprom.cl.api.backend.services.Clientes;

import com.geoprom.cl.api.backend.models.Clientes;

import java.util.List;

public interface ClientesService {

    List<Clientes> getClientes(Long user_id);

}
