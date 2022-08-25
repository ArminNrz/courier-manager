package ir.ronad.courierManager.service.lowlevel.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Component
@Slf4j
public class DateTimeUtility {

    private static final ZoneId IRR_ZONE_ID = ZoneId.of("Asia/Tehran");

    public LocalDate getJalaliLocalDate() {
        LocalDate now = LocalDate.now(IRR_ZONE_ID);
        log.debug("Date of IRR zone is: {}", now);
        return now;
    }

    public LocalTime getJalaliLocalTime() {
        LocalTime now = LocalTime.now(IRR_ZONE_ID);
        log.debug("Time of IRR zone is: {}", now);
        return now;
    }
}
