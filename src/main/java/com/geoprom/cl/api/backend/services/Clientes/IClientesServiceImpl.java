package com.geoprom.cl.api.backend.services.Clientes;

import com.geoprom.cl.api.backend.Repository.ClientesRepository;
import com.geoprom.cl.api.backend.models.Clientes;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    @Override
    public Clientes findById(Long cliente_id) {
        return clientesRepository.findById(cliente_id).orElse(null);
    }

    @Override
    public void save(Clientes cliente) {
        clientesRepository.save(cliente);

    }

    @Override
    public Clientes crearCliente(Clientes clientes) {
        String contrasenaEncrypt = encryptPassword(clientes.getContrasena());

        clientes.setContrasena(contrasenaEncrypt);
        return clientesRepository.save(clientes);
    }

    public static String encryptPassword(String password){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] inputBytes = password.getBytes("UTF-8");

            byte[] hashBytes = messageDigest.digest(inputBytes);

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // En caso de error
    }
}

