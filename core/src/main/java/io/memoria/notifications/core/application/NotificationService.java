package io.memoria.notifications.core.application;

import io.memoria.notifications.core.application.subscription.SelectionHistoryRepo;
import io.memoria.notifications.core.application.subscription.UserSubscriptionRepo;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;
import io.memoria.notifications.core.domain.service.UserEligibilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class NotificationService {
  private static final Logger log = LoggerFactory.getLogger(NotificationService.class.getName());

  private final UserSubscriptionRepo userSubscriptionRepo;
  private final UserEligibilityService userEligibilityService;
  private final SelectionHistoryRepo selectionHistoryRepo;

  public NotificationService(UserSubscriptionRepo userSubscriptionRepo,
                             UserEligibilityService userEligibilityService,
                             SelectionHistoryRepo selectionHistoryRepo) {
    this.userSubscriptionRepo = userSubscriptionRepo;
    this.userEligibilityService = userEligibilityService;
    this.selectionHistoryRepo = selectionHistoryRepo;
  }

  /**
   * User RBAC
   */
  public UserSubscription createSubscription(UserSubscription sub) {
    return userSubscriptionRepo.upsert(sub.userId(), sub.frequency());
  }

  /**
   * Cron RBAC
   */
  public void markSubscribers(Frequency frequency, long createdAt) {
    var users = userSubscriptionRepo.getUsers(frequency);
    var selectedUsers = userEligibilityService.filter(users);
    if (!selectedUsers.isEmpty()) {
      selectionHistoryRepo.markSelectedSubscribers(selectedUsers, frequency, createdAt);
      log.info("Selected subscribers are: {}, at {}", selectedUsers, createdAt);
    }
  }

  /**
   * Admin RBAC
   */
  public Set<UserId> find(long startMillis, long toMillis) {
    if (startMillis > toMillis) {
      throw new IllegalArgumentException("Start time can't be bigger than end");
    }
    return selectionHistoryRepo.findSubscribersBy(startMillis, toMillis);
  }
}
