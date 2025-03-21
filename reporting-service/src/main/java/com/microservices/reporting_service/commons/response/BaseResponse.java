package com.microservices.reporting_service.commons.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class BaseResponse {

    private boolean status;
    private String message;
    private Object response;
    // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Instant systemTime;
}
