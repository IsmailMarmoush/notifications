package io.memoria.notifications.infra.out.subscription.postgres;

import io.memoria.notifications.core.application.subscription.SelectionHistoryRepo;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;

import java.util.List;
import java.util.Set;

public class PgSelectionHistory implements SelectionHistoryRepo {
  @Override
  public void markSelectedSubscribers(List<UserId> userIds, Frequency frequency, long createdAt) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<UserId> findSubscribersBy(long fromMillis, long toMillis) {
    throw new UnsupportedOperationException();
  }
}
