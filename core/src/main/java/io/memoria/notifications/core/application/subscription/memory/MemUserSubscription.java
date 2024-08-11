package io.memoria.notifications.core.application.subscription.memory;

import io.memoria.notifications.core.application.subscription.UserSubscriptionRepo;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class MemUserSubscription implements UserSubscriptionRepo {
  private final Map<UserId, UserSubscription> db;

  public MemUserSubscription() {
    this(new ConcurrentHashMap<>());
  }

  public MemUserSubscription(ConcurrentHashMap<UserId, UserSubscription> db) {
    this.db = db;
  }

  @Override
  public UserSubscription upsert(UserId userId, Frequency frequency) {
    var sub = new UserSubscription(userId, frequency);
    db.putIfAbsent(userId, sub);
    return sub;
  }

  @Override
  public List<UserId> getUsers(Frequency frequency) {
    return db.entrySet()
             .stream()
             .filter(entry -> entry.getValue().frequency() == frequency)
             .map(Entry::getKey)
             .toList();
  }
}
