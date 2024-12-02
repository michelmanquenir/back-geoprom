package com.geoprom.cl.api.backend.models.Response.Clientes;

import com.geoprom.cl.api.backend.models.Clientes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreateClienteDTO {
    private int error;
    private String message;
    private Clientes clientes;
}
