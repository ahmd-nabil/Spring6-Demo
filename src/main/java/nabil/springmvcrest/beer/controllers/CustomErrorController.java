package nabil.springmvcrest.beer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity handleBindingError(MethodArgumentNotValidException exception) {
        List<Map<String, String>> errorList = exception.getBindingResult().getFieldErrors().stream().map(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            return errorMap;
        }).toList();
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    ResponseEntity handleMethodArgumentError(MethodArgumentTypeMismatchException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(e.getName(), e.getValue().toString());
        return ResponseEntity.badRequest().body(errorMap);
    }
}
