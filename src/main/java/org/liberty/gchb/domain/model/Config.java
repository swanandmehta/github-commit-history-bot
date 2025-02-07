package org.liberty.gchb.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import lombok.Getter;

@Getter
public class Config {

  private final String path;
  private final LocalDate startDate;
  private final LocalDate endDate;
  private final LocalDateTime startTime;
  private final LocalDateTime endTime;
  private final Boolean isWeekendIncluded;
  private final Boolean isOnlyWeekend;
  private final Integer minCommitPerDay;
  private final Integer maxCommitPerDay;

  public Config(Properties properties) {
    LocalDateTime today = LocalDateTime.now();

    this.path = properties.getProperty("org.liberty.gchb.path", "");

    this.startDate =
        LocalDate.parse(
            properties.getProperty(
                "org.liberty.gchb.start-date", today.format(DateTimeFormatter.ISO_LOCAL_DATE)),
            DateTimeFormatter.ISO_LOCAL_DATE);
    this.endDate =
        LocalDate.parse(
            properties.getProperty(
                "org.liberty.gchb.end-date", today.format(DateTimeFormatter.ISO_LOCAL_DATE)),
            DateTimeFormatter.ISO_LOCAL_DATE);

    this.startTime =
        LocalDateTime.parse(
            properties.getProperty(
                "org.liberty.gchb.start-time", today.format(DateTimeFormatter.ISO_DATE_TIME)));

    this.endTime =
        LocalDateTime.parse(
            properties.getProperty(
                "org.liberty.gchb.end-time", today.format(DateTimeFormatter.ISO_DATE_TIME)));

    this.isWeekendIncluded =
        Boolean.parseBoolean(
            properties.getProperty("org.liberty.gchb.is-weekend-included", "true"));
    this.isOnlyWeekend =
        Boolean.parseBoolean(properties.getProperty("org.liberty.gchb.is-only-weekend", "false"));

    this.minCommitPerDay =
        Integer.parseInt(properties.getProperty("org.liberty.gchb.min-commit-per-day", "1"));
    this.maxCommitPerDay =
        Integer.parseInt(properties.getProperty("org.liberty.gchb.max-commit-per-day", "5"));
  }
}
