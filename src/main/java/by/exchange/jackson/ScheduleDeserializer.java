package by.exchange.jackson;

import by.exchange.model.Schedule;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ScheduleDeserializer extends StdDeserializer<Schedule> {

  public ScheduleDeserializer() {
    super(Schedule.class);
  }

  @Override
  public Schedule deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    String[] parts = p.getText().split("\\|");
    Schedule.DaySchedule[] days = new Schedule.DaySchedule[parts.length];
    for (int i = 0; i < parts.length; ++i) {
      String[] dayParts = parts[i].split("\\s+");
      Schedule.TimeRange work = null;
      Schedule.TimeRange[] breaks = null;
      if (dayParts.length >= 5) {
        work = parseTimeRange(dayParts, 1);
        if (dayParts.length >= 9) {
          breaks = new Schedule.TimeRange[(dayParts.length - 5) >> 2];
          for (int j = 5, z = 0; z < breaks.length; j += 4, ++z)
            breaks[z] = parseTimeRange(dayParts, j);
        }
      }
      days[i] = new Schedule.DaySchedule(dayParts[0], work, breaks);
    }
    return new Schedule(days);
  }

  private static Schedule.TimeRange parseTimeRange(String[] dayParts, int index) {
    return new Schedule.TimeRange(
        new Schedule.TimePoint(
            Integer.parseInt(dayParts[index]),
            Integer.parseInt(dayParts[index + 1])
        ),
        new Schedule.TimePoint(
            Integer.parseInt(dayParts[index + 2]),
            Integer.parseInt(dayParts[index + 3])
        ));
  }
}
