package org.liberty.gchb.domain.service;

import lombok.Getter;
import org.liberty.gchb.application.port.ConfigClient;
import org.liberty.gchb.domain.model.Config;

public class ConfigService {

  @Getter protected final Config config;
  protected final ConfigClient configClient;

  public ConfigService(ConfigClient configClient) {
    this.configClient = configClient;
    this.config = this.configClient.getConfig();
    this.config.validate();
  }
}
