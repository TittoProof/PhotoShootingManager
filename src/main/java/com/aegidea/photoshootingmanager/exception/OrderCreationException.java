package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrderCreationException extends RuntimeException {

    private static final long serialVersionUID = -5312789871497686922L;

    public OrderCreationException(String message) {
        super(message);
    }

}
