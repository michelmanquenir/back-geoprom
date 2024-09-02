package com.geoprom.cl.api.backend.Repository;

import com.geoprom.cl.api.backend.models.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasRepository extends JpaRepository<Categorias, Long> {
}
