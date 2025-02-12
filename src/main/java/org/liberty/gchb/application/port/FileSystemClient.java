package org.liberty.gchb.application.port;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.liberty.gchb.domain.model.Commit;

public interface FileSystemClient {

  boolean createFile(Commit commit);

  boolean write(Commit commit);
}
