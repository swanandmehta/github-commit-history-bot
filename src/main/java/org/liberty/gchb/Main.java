package org.liberty.gchb;

import org.liberty.gchb.application.port.ConfigClient;
import org.liberty.gchb.domain.service.ConfigService;
import org.liberty.gchb.domain.service.HistoryService;
import org.liberty.gchb.infrastructure.adapter.cli.PropertiesConfigClient;

public class Main {

    public static void main(String[] arg) {
        ConfigClient configClient = new PropertiesConfigClient();
        ConfigService configService = new ConfigService(configClient);
        HistoryService historyService = new HistoryService(configService);
        historyService.createCommitHistory();

    }
}
