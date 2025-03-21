package com.microservices.portfolio_service.commons.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class DateTimeUtil {


    public static Instant getInstant() {
        Instant instant = Instant.now();
        ZoneId zone1 = ZoneId.of("Asia/Kolkata"); //America/New_York"
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone1);
        Instant toInstant = localDateTime.toInstant(ZoneOffset.UTC);
        return toInstant;
    }

}
