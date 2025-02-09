package org.liberty.gchb.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import lombok.Getter;
import org.liberty.gchb.application.exception.ConfigClientException;

@Getter
public class Config {

  private final String path;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final LocalTime startTime;
  private final LocalTime endTime;
  private final Boolean isWeekendIncluded;
  private final Boolean isOnlyWeekend;
  private final Integer minCommitPerDay;
  private final Integer maxCommitPerDay;

  public Config(Properties properties) {
    LocalDate today = LocalDate.now();

    this.path = properties.getProperty("org.liberty.gchb.path", "");

    this.startDate = getValue(properties, "org.liberty.gchb.start-date", today);
    this.endDate = getValue(properties, "org.liberty.gchb.end-date", today);

    this.startTime = getValue(properties, "org.liberty.gchb.start-time", LocalTime.MIN);
    this.endTime = getValue(properties, "org.liberty.gchb.end-time", LocalTime.MAX);

    this.isWeekendIncluded = getValue(properties, "org.liberty.gchb.is-weekend-included", true);
    this.isOnlyWeekend = getValue(properties, "org.liberty.gchb.is-only-weekend", false);

    this.minCommitPerDay = getValue(properties, "org.liberty.gchb.min-commit-per-day", 1);
    this.maxCommitPerDay = getValue(properties, "org.liberty.gchb.max-commit-per-day", 5);
  }

  private LocalDate getValue(Properties properties, String key, LocalDate defaultValue) {
    String value = properties.getProperty(key, "");

    if (value.isBlank()) {
      return defaultValue;
    }

    return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
  }

  private LocalTime getValue(Properties properties, String key, LocalTime defaultValue) {
    String value = properties.getProperty(key, "");

    if (value.isBlank()) {
      return defaultValue;
    }

    return LocalTime.parse(value, DateTimeFormatter.ISO_TIME);
  }

  private boolean getValue(Properties properties, String key, boolean defaultValue) {
    String value = properties.getProperty(key, "");

    if (value.isBlank()) {
      return defaultValue;
    }

    return Boolean.parseBoolean(value);
  }

  private int getValue(Properties properties, String key, int defaultValue) {
    String value = properties.getProperty(key, "");

    if (value.isBlank()) {
      return defaultValue;
    }

    return Integer.parseInt(value);
  }

  public void validate() {
    if (path.isEmpty()) {
      throw new ConfigClientException("Provided path cannot be empty");
    }

    if (startDate.isAfter(endDate)) {
      throw new ConfigClientException("End date cannot be after start date");
    }

    if (startTime.isAfter(endTime)) {
      throw new ConfigClientException("End time cannot be after start date");
    }

    if (maxCommitPerDay <= 0) {
      throw new ConfigClientException("Max commit per day cannot be less then or equal to 0");
    }

    if (minCommitPerDay > maxCommitPerDay) {
      throw new ConfigClientException(
          "Min number of commit cant be more then max number of commits");
    }
  }
}
