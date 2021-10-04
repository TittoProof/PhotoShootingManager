package com.aegidea.photoshootingmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tiziano (Titto) Fortin <tiziano@proofy.co>
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScheduleOrderException extends RuntimeException {

    private static final long serialVersionUID = -7622221679400845925L;

    public ScheduleOrderException(String message) {
        super(message);
    }

}
