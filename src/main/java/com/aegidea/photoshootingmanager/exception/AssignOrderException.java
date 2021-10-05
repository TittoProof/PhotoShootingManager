package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssignOrderException extends RuntimeException {

    private static final long serialVersionUID = -758561684557748911L;

    public AssignOrderException(String message) {
        super(message);
    }

}
