package org.liberty.gchb;

import org.liberty.gchb.application.port.CommitClient;
import org.liberty.gchb.application.port.ConfigClient;
import org.liberty.gchb.application.port.FileSystemClient;
import org.liberty.gchb.domain.service.ConfigService;
import org.liberty.gchb.domain.service.HistoryService;
import org.liberty.gchb.infrastructure.adapter.cli.GitCommitClient;
import org.liberty.gchb.infrastructure.adapter.cli.LocalFileSystemClient;
import org.liberty.gchb.infrastructure.adapter.cli.PropertiesConfigClient;

public class Main {

    public static void main(String[] arg) {
        ConfigClient configClient = new PropertiesConfigClient();
        FileSystemClient fileSystemClient = new LocalFileSystemClient();
        CommitClient commitClient = new GitCommitClient();

        ConfigService configService = new ConfigService(configClient);
        HistoryService historyService = new HistoryService(configService, fileSystemClient, commitClient);

        historyService.createCommitHistory();

    }
}
