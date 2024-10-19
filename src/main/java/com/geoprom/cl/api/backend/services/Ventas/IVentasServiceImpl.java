package com.geoprom.cl.api.backend.services.Ventas;

import com.geoprom.cl.api.backend.Repository.*;
import com.geoprom.cl.api.backend.models.*;
import com.geoprom.cl.api.backend.models.DTOs.DetalleVentaDTO;
import com.geoprom.cl.api.backend.models.DTOs.VentaRequestDTO;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Service
public class IVentasServiceImpl implements VentasService {
    private final Logger logger = LoggerFactory.getLogger(IVentasServiceImpl.class.getSimpleName());

    private final VentasRepository ventasRepository;
    private final DetalleVentasRepository detalleVentasRepository;
    private final UsuariosRepository usuariosRepository;
    private final ProductosRepository productosRepository;
    private final ClientesRepository clientesRepository;

    public IVentasServiceImpl(VentasRepository ventasRepository,
                              DetalleVentasRepository detalleVentasRepository,
                              UsuariosRepository usuariosRepository,
                              ProductosRepository productosRepository,
                              ClientesRepository clientesRepository) {
        this.ventasRepository = ventasRepository;
        this.detalleVentasRepository = detalleVentasRepository;
        this.usuariosRepository = usuariosRepository;
        this.productosRepository = productosRepository;
        this.clientesRepository = clientesRepository;
    }

    @Override
    public List<Ventas> getSales(Long sale_id) {
        logger.info("sale_id:" + sale_id);
        if (sale_id != null) {
            Ventas sales = ventasRepository.findById(sale_id).orElse(null);
            if (sales != null) {
                return Collections.singletonList(sales);
            } else {
                return Collections.emptyList();
            }
        } else {
            return ventasRepository.findAll();
        }
    }

    @Transactional
    public Ventas crearVenta(VentaRequestDTO ventaRequest) {
        // Obtener el usuario que realiza la venta
        Usuarios usuario = usuariosRepository.findById(ventaRequest.getId_usuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Clientes cliente = clientesRepository.findById(ventaRequest.getId_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        // Crear la entidad Ventas
        Ventas venta = new Ventas();
        venta.setTotal_ganancia(ventaRequest.getTotal_ganancia());
        venta.setTotal_venta(ventaRequest.getTotal_venta());
        venta.setMetodo_pago(ventaRequest.getMetodo_pago());
        venta.setUser(usuario);
        venta.setCliente(cliente);
        venta.setEstado(ventaRequest.getEstado());
        venta.setCreated_at(new Timestamp(System.currentTimeMillis()));
        ventasRepository.save(venta);

        // Crear los detalles de la venta
        for (DetalleVentaDTO detalleDTO : ventaRequest.getDetalles()) {
            Productos producto = productosRepository.findById(detalleDTO.getId_producto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            DetalleVentas detalle = new DetalleVentas();
            detalle.setSale(venta);
            detalle.setProductos(producto);
            detalle.setCantidad(detalleDTO.getCantidad());
            detalle.setPrecio_unitario(detalleDTO.getPrecio_unitario());
            detalle.setSubtotal(detalleDTO.getSubtotal());
            detalle.setCreated_at(new Timestamp(System.currentTimeMillis()));
            detalleVentasRepository.save(detalle);
        }

        return venta;
    }
}
