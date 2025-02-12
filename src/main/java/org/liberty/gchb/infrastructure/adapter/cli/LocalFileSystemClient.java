package org.liberty.gchb.infrastructure.adapter.cli;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.liberty.gchb.application.exception.FileSystemException;
import org.liberty.gchb.application.port.FileSystemClient;
import org.liberty.gchb.domain.model.Commit;

public class LocalFileSystemClient implements FileSystemClient {
  @Override
  public boolean createFile(Commit commit) {
    try {
      File file = new File(commit.filePath());
      return file.createNewFile();
    } catch (IOException e) {
      throw new FileSystemException(e);
    }
  }

  @Override
  public boolean write(Commit commit) {
    File file = new File(commit.filePath());
    try (FileWriter fw = new FileWriter(file)) {
      fw.write(commit.message());
    } catch (IOException e) {
      throw new FileSystemException(e);
    }

    return true;
  }
}
