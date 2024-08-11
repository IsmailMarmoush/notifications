package io.memoria.notifications.core.application;

import io.memoria.notifications.core.application.subscription.memory.MemSelectionHistory;
import io.memoria.notifications.core.application.subscription.memory.MemUserSubscription;
import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;
import io.memoria.notifications.core.domain.service.RandomUserEligibilityService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class NotificationServiceTest {
  private final NotificationService service;

  public NotificationServiceTest() {
    var userSubscriptionRepo = new MemUserSubscription();
    var selectionHistoryRepo = new MemSelectionHistory();
    var userSelectionService = new RandomUserEligibilityService(1); // 100 percent
    this.service = new NotificationService(userSubscriptionRepo, userSelectionService, selectionHistoryRepo);
  }

  @ParameterizedTest
  @EnumSource(Frequency.class)
  void selectionUseCase(Frequency frequency) {
    // Given
    UserId userId = new UserId(0);
    service.createSubscription(new UserSubscription(userId, frequency));

    // When
    var now = System.currentTimeMillis();
    service.markSubscribers(frequency, now);

    // Then
    Assertions.assertThat(service.find(now, now)).hasSize(1);
    Assertions.assertThat(service.find(now, now + 1)).hasSize(1);
    Assertions.assertThat(service.find(now - 1, now)).hasSize(1);
    Assertions.assertThat(service.find(now + 1, now + 1)).isEmpty();
  }
}
