package org.liberty.gchb.domain.model;

import java.time.LocalDateTime;

public record Commit(String filePath, LocalDateTime time, String message) {}
