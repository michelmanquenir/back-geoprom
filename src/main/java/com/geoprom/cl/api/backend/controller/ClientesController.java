package com.geoprom.cl.api.backend.controller;


import com.geoprom.cl.api.backend.helper.ClientesHelper;
import com.geoprom.cl.api.backend.models.Clientes;
import com.geoprom.cl.api.backend.models.Request.Clientes.UpdateClienteRequest;
import com.geoprom.cl.api.backend.models.Response.Clientes.ResponseCreateClienteDTO;
import com.geoprom.cl.api.backend.services.Clientes.ClientesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientesController {
    private final Logger logger = LoggerFactory.getLogger(ClientesController.class.getSimpleName());

    private final ClientesService clientesService;
    private final ClientesHelper clientesHelper;

    public ClientesController(ClientesService clientesService,
                              ClientesHelper clientesHelper){
        this.clientesService = clientesService;
        this.clientesHelper = clientesHelper;
    }


    @GetMapping("/clientes")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) Long id_cliente) {
        Map<String, Object> response = new HashMap<>();

        List<Clientes> clientes = clientesService.getClientes(id_cliente);
        logger.info("clientes" + clientes.size());
        response.put("clientes", clientes);
        response.put("error", 0);
        response.put("message", "Clientes obtenidos con exito");
        response.put("code", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/crear-cliente")
    public ResponseEntity<?> createCliente(
            @Valid @RequestPart("cliente") Clientes clienteDTO,
            @RequestPart("file") MultipartFile file) {

        ResponseCreateClienteDTO response = new ResponseCreateClienteDTO();
        try {
            // Manejo del archivo de imagen
            if (!file.isEmpty()) {
                // Verificar si el directorio de subida existe, si no, crear
                String uploadDir = "uploads/clientes/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // Guardar la imagen en el servidor
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, file.getBytes());

                // Establecer la URL de la imagen en el DTO
                clienteDTO.setUrlImg("/uploads/clientes/" + fileName);
            }

            // Crear el cliente con la imagen asignada
            Clientes nuevoCliente = clientesService.crearCliente(clienteDTO);

            response.setError(0); // Sin errores
            response.setMessage("Cliente creado con éxito");
            response.setClientes(nuevoCliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            response.setError(1); // Error genérico
            response.setMessage("Ocurrió un error al guardar la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.setError(2); // Otro tipo de error
            response.setMessage("Ocurrió un error durante la creación del cliente: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PutMapping("/update-cliente/{cliente_id}")
    public ResponseEntity<?> updateCliente(
            @PathVariable Long cliente_id,
            @RequestPart("cliente") UpdateClienteRequest updateClienteRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        // Logs para depuración
        System.out.println("update cliente: " + updateClienteRequest);
        System.out.println("user id: " + cliente_id);

        Map<String, Object> response = new HashMap<>();
        try {
            // Obtener el cliente actual
            Clientes currentCliente = clientesService.findById(cliente_id);

            if (currentCliente == null) {
                System.out.println("El cliente no existe en la base de datos");
                response.put("message", "Error: no se pudo editar el cliente con ID: "
                        .concat(cliente_id.toString().concat(" no existe en la base de datos")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            System.out.println("Proceso de actualización de datos comienza");

            // Si se recibe un archivo (imagen), procesar la actualización de la imagen
            if (file != null && !file.isEmpty()) {
                // Directorio donde se almacenan las imágenes
                String uploadDir = "uploads/clientes/";
                String existingImagePath = currentCliente.getUrlImg();

                // Si ya existe una imagen, eliminarla
                if (existingImagePath != null && !existingImagePath.isEmpty()) {
                    Path oldImagePath = Paths.get(uploadDir)
                            .resolve(existingImagePath.replace("/uploads/clientes/", ""))
                            .toAbsolutePath();
                    Files.deleteIfExists(oldImagePath); // Elimina la imagen anterior
                }

                // Guardar la nueva imagen
                String newFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path newImagePath = Paths.get(uploadDir).resolve(newFileName).toAbsolutePath();

                // Crear el directorio si no existe
                Path uploadDirectory = newImagePath.getParent();
                if (!Files.exists(uploadDirectory)) {
                    Files.createDirectories(uploadDirectory);
                    System.out.println("Directorio creado: " + uploadDirectory);
                }

                System.out.println("Intentando copiar el archivo a: " + newImagePath);
                Files.copy(file.getInputStream(), newImagePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo copiado exitosamente a: " + newImagePath);

                // Actualizar la URL de la imagen en el DTO
                updateClienteRequest.setUrlImg("/uploads/clientes/" + newFileName);
            }

            // Actualizar los campos del cliente
            clientesHelper.updateCliente(updateClienteRequest, currentCliente);

            logger.info("Actualización exitosa");
            response.put("code", HttpStatus.OK.value());
            response.put("error", 0);
            response.put("message", "Cliente actualizado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
            response.put("message", "Error al guardar la imagen: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("Error al actualizar el cliente: " + e.getMessage());
            response.put("message", "Error al actualizar el cliente: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
