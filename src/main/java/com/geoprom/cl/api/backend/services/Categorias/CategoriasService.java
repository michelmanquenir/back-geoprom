package com.geoprom.cl.api.backend.services.Categorias;

import com.geoprom.cl.api.backend.models.Categorias;

import java.util.List;

public interface CategoriasService {

    List<Categorias> getCategorias(Long categoria_id);

}
