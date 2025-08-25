package com.rookies4.myspringbootlab.exception.advice;

import com.rookies4.myspringbootlab.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionAdvice {


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorObject> handleBusinessException(BusinessException ex, WebRequest request) {
        log.warn("BusinessException: {}", ex.getMessage());
        ErrorObject body = new ErrorObject();
        body.setStatusCode(ex.getHttpStatus().value());
        body.setMessage(ex.getMessage());
        return ResponseEntity.status(ex.getHttpStatus()).body(body);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorObject> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.toList());

        String combined = messages.isEmpty() ? "Validation failed" : String.join("; ", messages);

        ErrorObject body = new ErrorObject();
        body.setStatusCode(HttpStatus.BAD_REQUEST.value());
        body.setMessage(combined);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private String formatFieldError(FieldError fe) {
        String field = fe.getField();
        String defaultMsg = fe.getDefaultMessage();
        Object rejected = fe.getRejectedValue();
        return String.format("%s: %s (rejected: %s)", field, defaultMsg, rejected);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorObject> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("HttpMessageNotReadableException: {}", ex.getMessage());
        ErrorObject body = new ErrorObject();
        body.setStatusCode(HttpStatus.BAD_REQUEST.value());
        body.setMessage("Malformed request body or invalid value. Check types and date formats (yyyy-MM-dd).");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorObject> handleMissingParam(MissingServletRequestParameterException ex) {
        ErrorObject body = new ErrorObject();
        body.setStatusCode(HttpStatus.BAD_REQUEST.value());
        body.setMessage("Missing required parameter: " + ex.getParameterName());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorObject> handleNoHandler(NoHandlerFoundException ex) {
        ErrorObject body = new ErrorObject();
        body.setStatusCode(HttpStatus.NOT_FOUND.value());
        body.setMessage("Endpoint not found: " + ex.getRequestURL());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public Object handleRuntimeException(RuntimeException ex, WebRequest request) {
        log.error("Unexpected error", ex);

        if (isApiRequest(request)) {
            ErrorObject body = new ErrorObject();
            body.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            body.setMessage(ex.getMessage() != null ? ex.getMessage() : "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName("error/500");
            mav.addObject("error", ex);
            return mav;
        }
    }

    private boolean isApiRequest(WebRequest request) {
        if (request instanceof ServletWebRequest swr) {
            String path = swr.getRequest().getRequestURI();
            if (path != null && path.startsWith("/api/")) return true;

            String accept = swr.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) return true;
        }
        return false;
    }
}