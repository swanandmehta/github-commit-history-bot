package org.liberty.gchb.infrastructure.adapter.cli;

import java.io.IOException;
import java.util.Properties;
import org.liberty.gchb.application.exception.ConfigClientException;
import org.liberty.gchb.application.port.ConfigClient;
import org.liberty.gchb.domain.model.Config;

public class PropertiesConfigClient implements ConfigClient {

  private static Config config;

  @Override
  public void loadConfig() {
    try {
      Properties properties = new Properties();
      properties.load(getClass().getResourceAsStream("/app.config"));
      PropertiesConfigClient.config = new Config(properties);
    } catch (IOException e) {
      throw new ConfigClientException(e);
    }
  }

  @Override
  public Config getConfig() {
    if (PropertiesConfigClient.config == null) {
      this.loadConfig();
    }

    return PropertiesConfigClient.config;
  }
}
