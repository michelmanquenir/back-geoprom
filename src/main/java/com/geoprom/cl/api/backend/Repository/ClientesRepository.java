package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Clientes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientesRepository extends JpaRepository<Clientes, Long> {

}
