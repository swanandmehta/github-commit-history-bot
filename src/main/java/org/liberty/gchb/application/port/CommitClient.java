package org.liberty.gchb.application.port;

import org.liberty.gchb.domain.model.Commit;

public interface CommitClient {
  boolean createCommit(Commit commit);
}
