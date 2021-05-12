package org.example.exception.controlleradvice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.exception.FieldValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    ValidationErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ValidationErrorResult errorResult = new ValidationErrorResult();

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResult.getFieldErrors()
                    .add(new FieldValidationError(fieldError.getField(),
                            fieldError.getDefaultMessage()));
        }
        return errorResult;
    }

    @Getter
    @NoArgsConstructor
    public static class ValidationErrorResult {
        private final List<FieldValidationError> fieldErrors = new ArrayList<>();

        public ValidationErrorResult(String field, String message) {
            this.fieldErrors.add(new FieldValidationError(field, message));
        }
    }
}
