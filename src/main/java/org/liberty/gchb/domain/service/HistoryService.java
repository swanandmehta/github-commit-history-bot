package org.liberty.gchb.domain.service;

import java.util.List;
import java.util.Objects;
import org.liberty.gchb.application.exception.FileSystemException;
import org.liberty.gchb.application.port.CommitClient;
import org.liberty.gchb.application.port.FileSystemClient;
import org.liberty.gchb.domain.model.Commit;
import org.liberty.gchb.domain.model.Config;

public class HistoryService {
  protected final Config config;
  protected final FileSystemClient fsClient;
  protected final CommitClient commitClient;
  protected final CommitService commitService;

  public HistoryService(
      ConfigService configService,
      FileSystemClient fsClient,
      CommitClient commitClient,
      CommitService commitService) {
    this.config = configService.getConfig();
    this.fsClient = fsClient;
    this.commitClient = commitClient;
    this.commitService = commitService;
  }

  public void createCommitHistory() {
    List<Commit> commitList = commitService.getCommitList();
    commitList.stream()
        .map(this::createFile)
        .filter(Objects::nonNull)
        .map(this::updateFile)
        .filter(Objects::nonNull)
        .map(this::createCommit)
        .filter(Objects::nonNull)
        .forEach(
            commit -> System.out.printf("Created commit with message \"%s\" %n", commit.message()));
  }

  private Commit createCommit(Commit commit) {
    try {
      boolean isCommitCreated = commitClient.createCommit(commit);
      if (isCommitCreated) {
        return commit;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  private Commit updateFile(Commit commit) {
    try {
      boolean isFileUpdated = fsClient.write(commit);
      if (isFileUpdated) {
        return commit;
      }
    } catch (FileSystemException e) {
      throw new RuntimeException(e);
    }
    return null;
  }

  private Commit createFile(Commit commit) {
    try {
      boolean isFileCreated = fsClient.createFile(commit);
      if (isFileCreated) {
        return commit;
      }
    } catch (FileSystemException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
