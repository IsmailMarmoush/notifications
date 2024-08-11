package io.memoria.notifications.infra.out.subscription;

import io.memoria.notifications.core.application.subscription.UserSubscriptionRepo;
import io.memoria.notifications.infra.out.subscription.h2.H2UserSubscription;
import io.memoria.notifications.infra.out.subscription.postgres.PgUserSubscription;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class UserSubscriptionRepoTest {
  // TODO
  @ParameterizedTest
  @MethodSource("repos")
  void findUsers(UserSubscriptionRepo repo) {
  }

  public static Stream<Arguments> repos() {
    return Stream.of(Arguments.of(new H2UserSubscription()), Arguments.of(new PgUserSubscription()));
  }
}
