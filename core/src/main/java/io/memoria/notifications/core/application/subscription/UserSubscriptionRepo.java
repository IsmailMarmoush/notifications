package io.memoria.notifications.core.application.subscription;

import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;

import java.util.List;

public interface UserSubscriptionRepo {
  /**
   * The service will allow any user to register as a subscriber to receive push notifications.
   * <p>
   * Subscribers will be able to choose how frequently they want to be considered to receive push notifications (e.g.,
   * every 30 minutes, every hour, etc.).
   */
  UserSubscription upsert(UserId userId, Frequency frequency);

  List<UserId> getUsers(Frequency frequency);
}
