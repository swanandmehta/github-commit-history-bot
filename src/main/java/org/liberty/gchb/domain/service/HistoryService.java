package org.liberty.gchb.domain.service;

import org.liberty.gchb.application.port.CommitClient;
import org.liberty.gchb.application.port.FileSystemClient;
import org.liberty.gchb.domain.model.Config;

public class HistoryService {
  protected final ConfigService configService;
  protected final FileSystemClient fsClient;
  protected final CommitClient commitClient;

  public HistoryService(
      ConfigService configService, FileSystemClient fsClient, CommitClient commitClient) {
    this.configService = configService;
    this.fsClient = fsClient;
    this.commitClient = commitClient;
  }

  public void createCommitHistory() {
    Config config = configService.getConfig();
  }
}
