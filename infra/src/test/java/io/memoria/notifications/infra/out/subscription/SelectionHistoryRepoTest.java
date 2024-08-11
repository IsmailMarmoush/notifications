package io.memoria.notifications.infra.out.subscription;

import io.memoria.notifications.core.application.subscription.SelectionHistoryRepo;
import io.memoria.notifications.infra.out.subscription.h2.H2SelectionHistory;
import io.memoria.notifications.infra.out.subscription.postgres.PgSelectionHistory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SelectionHistoryRepoTest {
  // TODO
  @ParameterizedTest
  @MethodSource("repos")
  void findSubscribers(SelectionHistoryRepo repo) {
  }

  public static Stream<Arguments> repos() {
    return Stream.of(Arguments.of(new H2SelectionHistory()), Arguments.of(new PgSelectionHistory()));
  }
}
