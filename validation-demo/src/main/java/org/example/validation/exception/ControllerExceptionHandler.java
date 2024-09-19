package org.example.validation.exception;

import java.util.List;
import org.example.validation.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorMessage handleValidationException(MethodArgumentNotValidException ex) {
    List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
    if (!allErrors.isEmpty()) {
      ObjectError error = allErrors.get(0);
      String message = error.getDefaultMessage();
      return new ErrorMessage("validation_failed", message);
    }
    return new ErrorMessage("validation_failed", "validation failed");
  }
}
