package by.exchange.model;

import by.exchange.jackson.ScheduleDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonDeserialize(using = ScheduleDeserializer.class)
@AllArgsConstructor
public class Schedule {
  @JsonValue
  @Getter
  private final DaySchedule[] days;

  @AllArgsConstructor
  public static class DaySchedule {
    @Getter
    private final String day;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    private final TimeRange work;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Getter
    private final TimeRange[] breaks;
  }

  @AllArgsConstructor
  public static class TimeRange {
    @Getter
    private final TimePoint start;
    @Getter
    private final TimePoint end;
  }

  @AllArgsConstructor
  public static class TimePoint {
    @Getter
    private final int hour;
    @Getter
    private final int minutes;

    @JsonValue
    public String getValue() {
      return String.format("%02d:%02d", hour, minutes);
    }

    @Override
    public String toString() {
      return getValue();
    }
  }
}
