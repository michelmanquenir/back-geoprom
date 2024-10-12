package com.geoprom.cl.api.backend.controller;

import com.geoprom.cl.api.backend.helper.UsuarioHelper;
import com.geoprom.cl.api.backend.models.Request.LoginRequest;
import com.geoprom.cl.api.backend.models.Request.Usuarios.UpdateUsuarioRequest;
import com.geoprom.cl.api.backend.models.Response.Usuarios.ResponseCreateUsuarioDTO;
import com.geoprom.cl.api.backend.models.Usuarios;
import com.geoprom.cl.api.backend.services.Users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
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
public class UsuariosController {
    private final Logger logger = LoggerFactory.getLogger(UsuariosController.class.getSimpleName());

    private final UserService usersService;
    private final UsuarioHelper usuarioHelper;

    public UsuariosController(UserService usersService,
                              UsuarioHelper usuarioHelper) {
        this.usersService = usersService;
        this.usuarioHelper = usuarioHelper;
    }

    @GetMapping("/usuarios")
    public ResponseEntity<?> getUsers(@RequestParam(required = false) Long user_id) {
        Map<String, Object> response = new HashMap<>();

        List<Usuarios> users = usersService.getUsers(user_id);
        logger.info("users" + users.size());
        response.put("usuarios", users);
        response.put("error", 0);
        response.put("message", "Usuarios obtenidos con exito");
        response.put("code", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/crear-usuario")
    public ResponseEntity<?> createUsuario(
            @Valid @RequestPart("usuario") Usuarios usuarioDTO,
            @RequestPart("file") MultipartFile file) {

        ResponseCreateUsuarioDTO response = new ResponseCreateUsuarioDTO();
        try {
            // Manejo del archivo de imagen
            if (!file.isEmpty()) {
                // Verificar si el directorio de subida existe, si no, crearlo
                String uploadDir = "uploads/usuarios/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // Guardar la imagen en el servidor
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.write(filePath, file.getBytes());

                // Establecer la URL de la imagen en el DTO
                usuarioDTO.setUrlImg("/uploads/usuarios/" + fileName);
            }

            // Crear el usuario con la imagen asignada
            Usuarios newUsuario = usersService.createUser(usuarioDTO);

            response.setError(0); // Sin errores
            response.setMessage("Usuario creado con éxito");
            response.setUsuarios(newUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (IOException e) {
            response.setError(1); // Error genérico
            response.setMessage("Ocurrió un error al guardar la imagen: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            response.setError(2); // Otro tipo de error
            response.setMessage("Ocurrió un error durante la creación del usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @DeleteMapping("/usuarios/{userId}/soft-delete")
    public ResponseEntity<?> softDeleteUser(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        usersService.softDeleteUser(userId);
        response.put("message", "Usuario eliminado con exito");
        response.put("code", HttpStatus.OK.value());
        response.put("error", 0);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<Void> activateUser(@PathVariable Long userId) {
        usersService.activateUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?>loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("email: " + loginRequest.getEmail());
        logger.info("password: " + loginRequest.getPassword());
        try {
            return usersService.findUserByEmail(loginRequest);
        } catch (Exception e) {
            return null;
        }
    }


    @PutMapping("/update-user/{user_id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long user_id,
            @RequestPart("usuario") UpdateUsuarioRequest updateUsuarioRequest,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        // Logs para depuración
        System.out.println("update usuario: " + updateUsuarioRequest);
        System.out.println("user id: " + user_id);

        Map<String, Object> response = new HashMap<>();
        try {
            // Obtener el usuario actual
            Usuarios currentUser = usersService.findById(user_id);

            if (currentUser == null) {
                System.out.println("El usuario no existe en la base de datos");
                response.put("message", "Error: no se pudo editar el usuario con ID: "
                        .concat(user_id.toString().concat(" no existe en la base de datos")));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            System.out.println("Proceso de actualización de datos comienza");

            // Si se recibe un archivo (imagen), procesar la actualización de la imagen
            if (file != null && !file.isEmpty()) {
                // Directorio donde se almacenan las imágenes
                String uploadDir = "uploads/usuarios/";
                String existingImagePath = currentUser.getUrlImg();

                // Si ya existe una imagen, eliminarla
                if (existingImagePath != null && !existingImagePath.isEmpty()) {
                    Path oldImagePath = Paths.get(uploadDir)
                            .resolve(existingImagePath.replace("/uploads/usuarios/", ""))
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
                updateUsuarioRequest.setUrlImg("/uploads/usuarios/" + newFileName);
            }

            // Actualizar los campos del usuario
            usuarioHelper.updateUsuario(updateUsuarioRequest, currentUser);

            logger.info("Actualización exitosa");
            response.put("code", HttpStatus.OK.value());
            response.put("error", 0);
            response.put("message", "Usuario actualizado correctamente");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
            response.put("message", "Error al guardar la imagen: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.err.println("Error al actualizar el usuario: " + e.getMessage());
            response.put("message", "Error al actualizar el usuario: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}