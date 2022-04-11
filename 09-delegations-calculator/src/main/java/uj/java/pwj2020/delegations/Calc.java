package uj.java.pwj2020.delegations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Calc {

    BigDecimal calculate(String name, String start, String end, BigDecimal dailyRate) {

        ZonedDateTime startDateTime = ZonedDateTime.parse(start, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm VV"));
        ZonedDateTime endDateTime = ZonedDateTime.parse(end, DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm VV"));

        long jobTimeInMinutes = Duration.between(startDateTime, endDateTime).toMinutes();

        BigDecimal salary = BigDecimal.ZERO;

        while (jobTimeInMinutes > 0) {
            if (jobTimeInMinutes > 720) {
                salary = salary.add(dailyRate);
                jobTimeInMinutes -= 1440;
            } else if (jobTimeInMinutes > 480) {
                salary = salary.add(dailyRate.multiply(BigDecimal.valueOf(0.5)));
                break;
            } else {
                salary = salary.add(dailyRate.divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP));
                break;
            }
        }
        return salary.setScale(2, RoundingMode.HALF_UP);
    }
}
