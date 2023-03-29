package net.lazars.timer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TimerRestController {

  private static final String TIME_FORMAT_PATTERN = "H:mm";
  private static final DateTimeFormatter TIME_FORMAT =
      DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN);
  private static final DateTimeFormatter DATE_TIME_FORMAT =
      DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

  @Value("${target-base-time}")
  private String targetBaseTime;

  @Value("${time-zone}")
  private String timeZone;

  @GetMapping("target")
  public String getTargetBaseTime() {
    return getTargetTime().format(TIME_FORMAT);
  }

  @GetMapping("calculate")
  public CalculationResponse calculateFinishTime(
      @RequestParam("runtime") @DateTimeFormat(pattern = TIME_FORMAT_PATTERN)
          final LocalTime runtime) {
    final LocalTime targetTime = getTargetTime();
    final LocalDateTime finishTime =
        LocalDateTime.of(
            targetTime.getHour() > 6 ? LocalDate.now().plusDays(1) : LocalDate.now(), targetTime);

    final LocalDateTime startTime =
        finishTime.minusHours(runtime.getHour()).minusMinutes(runtime.getMinute());

    return new CalculationResponse(
        startTime.format(DATE_TIME_FORMAT), finishTime.format(TIME_FORMAT));
  }

  private LocalTime getTargetTime() {
    final boolean isDst = TimeZone.getTimeZone(timeZone).inDaylightTime(new Date());
    return LocalTime.parse(targetBaseTime).plusHours(isDst ? 1 : 0);
  }

  private record CalculationResponse(String startTime, String timeToStart) {}
}
