package io.memoria.notifications.core.application.subscription.memory;

import io.memoria.notifications.core.application.subscription.SelectionHistoryRepo;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MemSelectionHistory implements SelectionHistoryRepo {
  private final TreeMap<Long, SelectionRecord> db;

  public MemSelectionHistory() {
    this(new TreeMap<>());
  }

  public MemSelectionHistory(TreeMap<Long, SelectionRecord> db) {
    this.db = db;
  }

  @Override
  public void markSelectedSubscribers(List<UserId> userIds, Frequency frequency, long createdAt) {
    db.put(createdAt, new SelectionRecord(userIds, frequency));
  }

  @Override
  public Set<UserId> findSubscribersBy(long fromMillis, long toMillis) {
    return db.subMap(fromMillis, true, toMillis, true)
             .entrySet()
             .stream()
             .flatMap(rec -> rec.getValue().userIds().stream())
             .collect(Collectors.toSet());
  }
}
