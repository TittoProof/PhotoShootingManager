package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class OrderCannotVerifiedException extends RuntimeException {

    private static final long serialVersionUID = -5664667379353222441L;

    public OrderCannotVerifiedException(String message) {
        super(message);
    }

}
