package ru.gasin.course.exception.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gasin.course.exception.ErrorResponse;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.error(e.getMessage());
        e.printStackTrace();
        ErrorResponse errorResponse = new ErrorResponse(e.getStatus().value(), e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(errorResponse);
    }
}
