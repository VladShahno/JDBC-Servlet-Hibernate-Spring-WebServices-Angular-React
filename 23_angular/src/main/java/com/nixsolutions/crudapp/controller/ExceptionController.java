package com.nixsolutions.crudapp.controller;

import com.nixsolutions.crudapp.exception.FormProcessingException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExceptionController implements Controller {

    @ExceptionHandler(FormProcessingException.class)
    public Response handleException(FormProcessingException e) {
        Map<String, String> invalidFields = new HashMap<>();
        invalidFields.put(e.getAttributeName(), e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(invalidFields).build();
    }
}