package io.memoria.notifications.core.domain.model;

import lombok.Getter;

@Getter
public enum Frequency {
  ONE_SECOND(1000),
  TEN_SECONDS(10 * 1000),
  ONE_MIN(60 * 1000),
  THIRTY_MIN(60 * 30 * 1000),
  ONE_HOUR(60 * 60 * 1000);

  private final int millis;

  Frequency(int millis) {
    this.millis = millis;
  }
}
