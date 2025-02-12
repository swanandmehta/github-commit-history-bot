package org.liberty.gchb.domain.service;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.liberty.gchb.domain.model.Commit;
import org.liberty.gchb.domain.model.Config;

public class CommitService {
  protected final Config config;
  private final Random random;

  public CommitService(ConfigService configService) {
    this.config = configService.getConfig();
    this.random = new Random();
  }

  public List<Commit> getCommitList() {
    long totalDaysToProcess = calculateTotalDays();
    long totalAvailableMinutes = calculateAvailableMinutes();

    return LongStream.range(0L, totalDaysToProcess)
        .mapToObj(i -> getCommitHistoryByDay(i, totalAvailableMinutes))
        .filter(e -> !e.isEmpty())
        .flatMap(List::stream)
        .sorted(Comparator.comparing(Commit::time))
        .toList();
  }

  private List<Commit> getCommitHistoryByDay(long i, long totalAvailableMinutes) {
    LocalDate date = config.getStartDate().plusDays(i);
    boolean isWeekend = isWeekend(date);

    switch (config.getCommitType()) {
      case EVERYDAY -> {
        return getCommitListByDay(date, totalAvailableMinutes);
      }
      case ONLY_WEEKEND -> {
        if (isWeekend) {
          return getCommitListByDay(date, totalAvailableMinutes);
        }
        return List.of();
      }
      case ONLY_WORKDAY -> {
        if (!isWeekend) {
          return getCommitListByDay(date, totalAvailableMinutes);
        }
        return List.of();
      }
      default -> {
        return List.of();
      }
    }
  }

  private boolean isWeekend(LocalDate date) {
    return date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY;
  }

  private int calculateCommitsForDay() {
    int minCommit = config.getMinCommitPerDay();
    int maxCommit = config.getMaxCommitPerDay();
    return random.nextInt(minCommit, maxCommit + 1);
  }

  private long calculateAvailableMinutes() {
    LocalTime startTime = config.getStartTime();
    LocalTime endTime = config.getEndTime();
    return ChronoUnit.MINUTES.between(startTime, endTime);
  }

  private long calculateTotalDays() {
    LocalDate startDate = config.getStartDate();
    LocalDate endDate = config.getEndDate();
    if (startDate.isEqual(endDate)) {
      return 1;
    }
    return ChronoUnit.DAYS.between(startDate, endDate) + 1L;
  }

  private List<Commit> getCommitListByDay(LocalDate date, long diffInMin) {
    int totalNumberOfCommit = calculateCommitsForDay();
    return IntStream.range(0, totalNumberOfCommit)
        .mapToObj(
            e -> {
              String fileName = String.format("%s.txt", UUID.randomUUID());
              LocalDateTime commitDateTime = getCommitDateTime(date, diffInMin);
              String message = getCommitMessage();
              String filePath =
                  String.format("%s%c%s", config.getPath(), File.separatorChar, fileName);
              return new Commit(fileName, filePath, commitDateTime, message);
            })
        .toList();
  }

  private String getCommitMessage() {
    List<String> commitMessages = config.getCommitMessages();
    if (commitMessages.isEmpty()) {
      return String.format("Commit ID : %s", UUID.randomUUID());
    } else {
      return commitMessages.get(random.nextInt(commitMessages.size()));
    }
  }

  private LocalDateTime getCommitDateTime(LocalDate date, long diffInMin) {
    long randomMin = random.nextLong(diffInMin);
    int randomSec = random.nextInt(60);
    int randomNanoSec = random.nextInt(999999999);

    LocalTime commitTime =
        config.getStartTime().plusMinutes(randomMin).withSecond(randomSec).withNano(randomNanoSec);

    return date.atTime(commitTime);
  }
}
