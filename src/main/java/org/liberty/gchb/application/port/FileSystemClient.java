package org.liberty.gchb.application.port;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.liberty.gchb.domain.model.Commit;

public interface FileSystemClient {

  default void createFile(Commit commit) {
    try {
      File file = new File(commit.filePath());
      file.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  default void write(Commit commit) {
    File file = new File(commit.filePath());
    try (FileWriter fw = new FileWriter(file)) {
      fw.write(commit.message());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
