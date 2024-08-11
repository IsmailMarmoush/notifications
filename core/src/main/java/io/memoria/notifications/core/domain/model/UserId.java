package io.memoria.notifications.core.domain.model;

public record UserId(String value) {
  public UserId(int id) {
    this(String.valueOf(id));
  }
}
