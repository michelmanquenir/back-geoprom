package com.geoprom.cl.api.backend.services.Products;
import com.geoprom.cl.api.backend.Repository.CategoriasRepository;
import com.geoprom.cl.api.backend.Repository.ProductosRepository;
import com.geoprom.cl.api.backend.models.Categorias;
import com.geoprom.cl.api.backend.models.DTOs.ProductoDTO;
import com.geoprom.cl.api.backend.models.Productos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class IProductsServiceImpl implements ProductsService {
    private final Logger logger = LoggerFactory.getLogger(IProductsServiceImpl.class.getSimpleName());

    private final ProductosRepository productosRepository;
    private final CategoriasRepository categoriasRepository;

    public IProductsServiceImpl(ProductosRepository productRepository,
                                CategoriasRepository categoriasRepository) {
        this.productosRepository = productRepository;
        this.categoriasRepository = categoriasRepository;
    }

    public List<Productos> getProducts(Long product_id) {
        logger.info("product_id:" + product_id);
        if (product_id != null) {
            Productos products = productosRepository.findById(product_id).orElse(null);
            if (products != null) {
                return Collections.singletonList(products);
            } else {
                return Collections.emptyList();
            }
        } else {
            return productosRepository.findAll();
        }
    }

    @Transactional
    public Productos createProduct(ProductoDTO productosDTO) {
        Productos producto = new Productos();
        producto.setNombre(productosDTO.getNombre());
        producto.setRut_empresa(productosDTO.getRut_empresa());
        producto.setSku(productosDTO.getSku());
        producto.setPrecio_compra(productosDTO.getPrecio_compra());
        producto.setPrecio_venta(productosDTO.getPrecio_venta());
        producto.setStock(productosDTO.getStock());
        producto.setUrlImg(productosDTO.getUrlImg());
        producto.setEstado(productosDTO.getEstado());

        // Buscar la categoría por ID y asignarla al producto
        Optional<Categorias> categoria = categoriasRepository.findById(productosDTO.getId_categoria());
        categoria.ifPresent(producto::setCategoria);

        return productosRepository.save(producto);
    }

    @Transactional
    public void softDeleteProduct(Long product_id) {
        productosRepository.softDelete(product_id);
    }

    @Transactional
    public void activateProduct(Long product_id) {
        productosRepository.activateProduct(product_id);
    }

    @Transactional
    public void save(Productos producto) {
        productosRepository.save(producto); // Guarda el producto con el ID existente
    }

    @Override
    @Transactional(readOnly = true)
    public Productos findById(Long product_id) {
        return productosRepository.findById(product_id).orElse(null);
    }
}


const BtnApprove = (i
        businessId,
isActive,
approverUserId,
onApprove,
openPop,
anchorEl,
handlePopoverOpen,
handlePopoverClose,
popoverText,
esUsuarioCreador
aria-owns={openPop ? 'mouse-over-popover': undefined}
aria-haspopup="true"
onMouseEnter=(handlePopoverOpen}
onMouseLeave=[handlePopoverClose}

onClick={() => onApprove(businessId, isActive, approverUserId)}


        import { green, grey } from "@mui/material/colors";
        =>
        return (
< IconButton
disabled=[esUsuarioCreador]
< Popover
id="mouse-over-popover"
        5x={
pointerEvents: 'none'
        open={openPop}
        anchorEl- (anchorEl} anchorOrigin=t
        vertical: 'bottom' horizontal: 'left',
        }}
        transformOrigin=fl vertical: 'top', horizontal: 'left',
        }}
        onClose=(handlePopoverClose}
        disableRestoreFocus
        ‹Typography sx=ff p: 1 77>(popoverText)</Typography>
        </Popover>
        ‹DoneOutlineRoundedIcon sx=ff color: esUsuarioCreador ? grey[500] : green[500] 1}
        </ IconButton>
        export default BtnApprove;
