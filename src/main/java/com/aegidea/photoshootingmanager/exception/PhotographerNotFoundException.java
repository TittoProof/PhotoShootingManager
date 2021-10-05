package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhotographerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5607787347199453640L;

    public PhotographerNotFoundException(String message) {
        super(message);
    }

}
