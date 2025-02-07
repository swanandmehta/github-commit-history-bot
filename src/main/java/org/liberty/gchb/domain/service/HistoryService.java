package org.liberty.gchb.domain.service;

import org.liberty.gchb.domain.model.Config;

public class HistoryService {
  protected final ConfigService configService;

  public HistoryService(ConfigService configService) {
    this.configService = configService;
  }

  public void createCommitHistory() {
    Config config = configService.getConfig();
  }
}
