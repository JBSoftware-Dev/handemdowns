package com.handemdowns.web.controller.advice;

import com.handemdowns.common.RestResponse;
import com.handemdowns.web.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("400 Error:", e);
        return handleExceptionInternal(e, new RestResponse<Object>(
                RestResponse.FAIL, e.getMessage(), HttpStatus.BAD_REQUEST.value(), "HttpMessageNotReadable"), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("400 Error:", e);
        return handleExceptionInternal(e, new RestResponse<Object>(
                RestResponse.FAIL, e.getMessage(), HttpStatus.BAD_REQUEST.value(), "MethodArgumentNotValid"), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("400 Error:", e);
        BindingResult result = e.getBindingResult();
        return handleExceptionInternal(e, new RestResponse<Object>(
                RestResponse.FAIL, e.getMessage(), HttpStatus.BAD_REQUEST.value(), result.getAllErrors()), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({
            AdExpiredException.class,
            AdNotActiveException.class,
            AdNotFoundException.class,
            GeoLocationException.class,
            InvalidOldPasswordException.class,
            InvalidTokenException.class,
            NoMoreDataException.class,
            UnauthorizedException.class,
            UserAlreadyExistsException.class,
            UserDisabledException.class,
            UserNotFoundException.class})
    public ResponseEntity<Object> handleRestException(RuntimeException e, WebRequest request) {
        return handleExceptionInternal(e, new RestResponse<Object>(
                RestResponse.FAIL, e.getMessage(), resolveAnnotatedResponseStatus(e).value(), resolveAnnotatedResponseReason(e)), new HttpHeaders(), resolveAnnotatedResponseStatus(e), request);
    }

    @ExceptionHandler({WebException.class,})
    public String handleWebException(RuntimeException e, WebRequest request, Model model) {
        model.addAttribute("message", e.getMessage());
        return "/error/error";
    }

    private HttpStatus resolveAnnotatedResponseStatus(Exception e) {
        ResponseStatus annotation = findMergedAnnotation(e.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String resolveAnnotatedResponseReason(Exception e) {
        ResponseStatus annotation = findMergedAnnotation(e.getClass(), ResponseStatus.class);
        if (annotation != null) {
            return annotation.reason();
        }
        return "Error";
    }
}