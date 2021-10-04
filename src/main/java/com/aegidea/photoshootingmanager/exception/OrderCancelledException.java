package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class OrderCancelledException extends RuntimeException {

    private static final long serialVersionUID = -2997012358466765883L;

    public OrderCancelledException(String message) {
        super(message);
    }

}
