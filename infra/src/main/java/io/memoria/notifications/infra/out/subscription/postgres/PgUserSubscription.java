package io.memoria.notifications.infra.out.subscription.postgres;

import io.memoria.notifications.core.application.subscription.UserSubscriptionRepo;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;

import java.util.List;

public class PgUserSubscription implements UserSubscriptionRepo {
  @Override
  public UserSubscription upsert(UserId userId, Frequency frequency) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<UserId> getUsers(Frequency frequency) {
    throw new UnsupportedOperationException();
  }
}
