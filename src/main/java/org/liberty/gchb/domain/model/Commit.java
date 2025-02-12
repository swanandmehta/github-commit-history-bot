package org.liberty.gchb.domain.model;

import java.time.LocalDateTime;

public record Commit(String fileName, String filePath, LocalDateTime time, String message) {}
