package com.geoprom.cl.api.backend.services.Clientes;

import com.geoprom.cl.api.backend.Repository.ClientesRepository;
import com.geoprom.cl.api.backend.models.Clientes;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class IClientesServiceImpl implements ClientesService {

    public static ClientesRepository clientesRepository;

    public IClientesServiceImpl(ClientesRepository clientesRepository) {
        IClientesServiceImpl.clientesRepository = clientesRepository;
    }


    public List<Clientes> getClientes(Long id_cliente) {
        if (id_cliente != null) {
            Clientes user = clientesRepository.findById(id_cliente).orElse(null);
            if (user != null) {
                return Collections.singletonList(user);
            } else {
                return Collections.emptyList();
            }
        } else {
            return clientesRepository.findAll();
        }    }
}

