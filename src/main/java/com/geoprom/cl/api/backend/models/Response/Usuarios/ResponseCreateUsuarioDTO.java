package com.geoprom.cl.api.backend.models.Response.Usuarios;

import com.geoprom.cl.api.backend.models.Usuarios;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreateUsuarioDTO {
    private int error;
    private String message;
    private Usuarios usuarios;
}
