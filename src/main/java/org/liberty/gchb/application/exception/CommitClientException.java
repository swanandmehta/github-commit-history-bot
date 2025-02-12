package org.liberty.gchb.application.exception;

public class CommitClientException extends RuntimeException {
  public CommitClientException(Exception e) {
    super(e);
  }
}
