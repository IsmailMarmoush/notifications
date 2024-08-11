package io.memoria.notifications.core.domain.model;

public record UserSubscription(UserId userId, Frequency frequency) {
  public UserSubscription {

  }
}
