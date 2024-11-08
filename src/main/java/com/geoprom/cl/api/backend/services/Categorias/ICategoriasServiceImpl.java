package com.geoprom.cl.api.backend.services.Categorias;

import com.geoprom.cl.api.backend.Repository.CategoriasRepository;
import com.geoprom.cl.api.backend.models.Categorias;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ICategoriasServiceImpl implements CategoriasService {
    private final Logger logger = LoggerFactory.getLogger(ICategoriasServiceImpl.class.getSimpleName());

    private final CategoriasRepository categoriasRepository;

    public ICategoriasServiceImpl(CategoriasRepository categoriasRepository){
        this.categoriasRepository = categoriasRepository;
    }

    public List<Categorias> getCategorias(Long categoria_id) {
        if (categoria_id != null) {
            Categorias categorias = categoriasRepository.findById(categoria_id).orElse(null);
            if (categorias != null) {
                return Collections.singletonList(categorias);
            } else {
                return Collections.emptyList();
            }
        } else {
            return categoriasRepository.findAll();
        }
    }

}
