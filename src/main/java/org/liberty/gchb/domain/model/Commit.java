package org.liberty.gchb.domain.model;

import java.time.LocalDateTime;

public record Commit(LocalDateTime time, String message) {}
