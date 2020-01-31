package by.exchange.model;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

@JsonDeserialize(using = Schedule.Deserializer.class)
public class Schedule {
  @JsonValue
  public DaySchedule[] days;

  public static class DaySchedule {
    @JsonIgnore
    public String day;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("work")
    public int[][] workTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("break")
    public int[][] breakTime;
  }

  public static class Deserializer extends StdDeserializer<Schedule> {

    public Deserializer() {
      super(Schedule.class);
    }

    @Override
    public Schedule deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
      Schedule result = new Schedule();
      String[] parts = p.getText().split("\\|");
      result.days = new DaySchedule[parts.length];
      for (int i = 0; i < parts.length; ++i) {
        DaySchedule dayResult = new DaySchedule();
        result.days[i] = dayResult;
        String[] dayParts = parts[i].split("\\s");
        dayResult.day = dayParts[0];
        if (dayParts.length > 1) {
          int[] start = asIntPairIfPossible(dayParts[1], dayParts[2]);
          int[] finish = asIntPairIfPossible(dayParts[3], dayParts[4]);
          if (start != null && finish != null) dayResult.workTime = new int[][] { start, finish };
          if (dayParts.length > 5) {
            start = asIntPairIfPossible(dayParts[5], dayParts[6]);
            finish = asIntPairIfPossible(dayParts[7], dayParts[8]);
            if (start != null && finish != null) dayResult.breakTime = new int[][] { start, finish };
          }
        }
      }
      return result;
    }

    private static int[] asIntPairIfPossible(String first, String second) {
      try {
        return new int[] { Integer.parseInt(first), Integer.parseInt(second) };
      } catch (NumberFormatException nEx) {
        return null;
      }
    }
  }
}
