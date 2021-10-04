package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -5434761419971922906L;

    public OrderNotFoundException(String message) {
        super(message);
    }

}
