package com.geoprom.cl.api.backend.models.Response.Productos;

import com.geoprom.cl.api.backend.models.Productos;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDTO {
    private int error;
    private String message;
    private Productos product;

}
