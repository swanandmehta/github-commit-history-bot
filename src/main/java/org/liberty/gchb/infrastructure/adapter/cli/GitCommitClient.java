package org.liberty.gchb.infrastructure.adapter.cli;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.liberty.gchb.application.exception.CommitClientException;
import org.liberty.gchb.application.port.CommitClient;
import org.liberty.gchb.domain.model.Commit;

public class GitCommitClient implements CommitClient {

  @Override
  public boolean createCommit(Commit commit) {
    File workingDir = new File(commit.filePath().replace(commit.fileName(), ""));
    ProcessBuilder pb = new ProcessBuilder().directory(workingDir).command(createCommand(commit));
    try {
      Process process = pb.start();
      process.waitFor();
      return process.exitValue() == 0;
    } catch (IOException | InterruptedException e) {
      throw new CommitClientException(e);
    }
  }

  private List<String> createCommand(Commit commit) {
    String gitTimestamp = DateTimeFormatter.ISO_DATE_TIME.format(commit.time());
    String gitAddCommand = String.format("git add %s", commit.filePath());
    String gitCommitCommand =
        String.format("git commit -m \"%s\" --date=\"%s\"", commit.message(), gitTimestamp);
    String gitPushCommand = "git push origin HEAD";
    String executionCommand =
        String.format("%s && %s && %s", gitAddCommand, gitCommitCommand, gitPushCommand);
    return List.of("sh", "-c", executionCommand);
  }
}
