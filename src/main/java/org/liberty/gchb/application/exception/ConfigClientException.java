package org.liberty.gchb.application.exception;

public class ConfigClientException extends RuntimeException {
  public ConfigClientException(Exception e) {
    super(e);
  }

  public ConfigClientException(String msg) {
    super(msg);
  }
}
