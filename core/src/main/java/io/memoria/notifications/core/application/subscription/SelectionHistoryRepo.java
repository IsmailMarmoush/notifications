package io.memoria.notifications.core.application.subscription;

import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;

import java.util.List;
import java.util.Set;

public interface SelectionHistoryRepo {
  void markSelectedSubscribers(List<UserId> userIds, Frequency frequency, long createdAt);

  Set<UserId> findSubscribersBy(long fromMillis, long toMillis);
}
