package com.microservices.reporting_service.commons.exceptions;

import com.microservices.reporting_service.commons.response.BaseResponse;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Objects;

import static com.microservices.reporting_service.commons.util.DateTimeUtil.getInstant;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandling implements ErrorController {


    private static final String ACCOUNT_LOCKED = "Your account has been locked. Please contact administration";
    private static final String METHOD_IS_NOT_ALLOWED = "This request method is not allowed on this endpoint. Please send a '%s' request";
    private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
    private static final String INCORRECT_CREDENTIALS = "Username / password incorrect. Please try again";
    private static final String ACCOUNT_DISABLED = "Your account has been disabled. If this is an error, please contact administration";
    private static final String ERROR_PROCESSING_FILE = "Error occurred while processing file";
    private static final String NOT_ENOUGH_PERMISSION = "You do not have enough permission";
    public static final String INVALID_REFRESH_TOKEN = "Invalid refresh token";
    public static final String ERROR_PATH = "/error";


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    /*@ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<BaseResponse> createWebClientRequestException(WebClientRequestException ex) {
        return createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }*/
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<BaseResponse> createConnectException(ConnectException ex) {
        return createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<BaseResponse> createResourceAccessException(ResourceAccessException ex) {
        return createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods()).iterator().next();
        return createBaseResponse(METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> internalServerErrorException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return createBaseResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }


    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<BaseResponse> notFoundException(NoResultException exception) {
        log.error(exception.getMessage());
        return createBaseResponse(NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<BaseResponse> iOException(IOException exception) {
        log.error(exception.getMessage());
        return createBaseResponse(INTERNAL_SERVER_ERROR, ERROR_PROCESSING_FILE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> internalServerException(IllegalArgumentException exception) {
        log.error(exception.getMessage());
        return createBaseResponse(INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    public ResponseEntity<BaseResponse> createBaseResponse(HttpStatus httpStatus, String message) {
        BaseResponse baseResponse = BaseResponse.builder()
                .status(false)
                .message(message)
                .response(null)
                .systemTime(getInstant())
                .build();
        return new ResponseEntity<>(baseResponse, httpStatus);
    }

    @RequestMapping(ERROR_PATH)
    public ResponseEntity<BaseResponse> notFound404() {
        return createBaseResponse(NOT_FOUND, "There is no mapping for this URL");
    }

    public String getErrorPath() {
        return ERROR_PATH;
    }
}
