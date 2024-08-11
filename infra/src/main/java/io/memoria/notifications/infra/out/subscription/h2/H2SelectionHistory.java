package io.memoria.notifications.infra.out.subscription.h2;

import io.memoria.notifications.core.application.subscription.SelectionHistoryRepo;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;

import java.util.List;
import java.util.Set;

public class H2SelectionHistory implements SelectionHistoryRepo {
  @Override
  public void markSelectedSubscribers(List<UserId> userIds, Frequency frequency, long createdAt) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<UserId> findSubscribersBy(long fromMillis, long toMillis) {
    throw new UnsupportedOperationException();
  }
}
