package org.liberty.gchb.infrastructure.adapter.cli;

import java.io.IOException;
import java.util.Properties;
import org.liberty.gchb.application.exception.ConfigClientException;
import org.liberty.gchb.application.port.ConfigClient;
import org.liberty.gchb.domain.model.Config;

public class PropertiesConfigClient implements ConfigClient {

  private Config config;

  @Override
  public void loadConfig() {}

  @Override
  public Config getConfig() {
    try {
      Properties properties = new Properties();
      properties.load(getClass().getResourceAsStream("/app.config"));
      return new Config(properties);
    } catch (IOException e) {
      throw new ConfigClientException(e);
    }
  }
}
