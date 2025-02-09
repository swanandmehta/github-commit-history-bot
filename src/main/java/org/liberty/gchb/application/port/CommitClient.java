package org.liberty.gchb.application.port;

import org.liberty.gchb.domain.model.Commit;

public interface CommitClient {
  default void createCommit(Commit commit) {
    System.out.println(commit.filePath());
  }
}
