package org.liberty.gchb.application.exception;

public class FileSystemException extends RuntimeException {
  public FileSystemException(Exception e) {
    super(e);
  }
}
