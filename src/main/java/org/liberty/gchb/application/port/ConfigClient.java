package org.liberty.gchb.application.port;

import org.liberty.gchb.domain.model.Config;

public interface ConfigClient {
  void loadConfig();

  Config getConfig();
}
