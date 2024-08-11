package io.memoria.notifications.app;

import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;
import io.memoria.notifications.infra.Dependencies;
import io.memoria.notifications.infra.Utils;
import io.memoria.notifications.infra.config.Config;
import io.memoria.notifications.infra.config.HttpServerConfig;
import io.memoria.notifications.infra.config.SubscriptionDB;
import io.memoria.notifications.infra.in.subscription.quartz.NotificationsScheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static io.memoria.notifications.core.domain.model.Frequency.TEN_SECONDS;
import static io.memoria.notifications.infra.config.TextTransformerType.COMPACT_JSON;

public class App {
  private static final Logger log = LoggerFactory.getLogger(Utils.class.getName());

  public static void main(String[] args) throws SchedulerException, InterruptedException {
    Utils.printCurrentLogLevels(log);
    var httpServerConfig = new HttpServerConfig("localhost", 9000);
    var config = new Config(SubscriptionDB.MEMORY, 1, COMPACT_JSON, httpServerConfig);
    var deps = new Dependencies(config);
    var ntfService = deps.getNotificationService();

    //    createSubscription(0, 10, ONE_SECOND).forEach(ntfService::createSubscription);
    //    createSubscription(10, 20, TWO_SECONDS).forEach(ntfService::createSubscription);

    var scheduler = new NotificationsScheduler("notifications", ntfService, TEN_SECONDS);
    scheduler.start();
    deps.server().start();
    //    scheduler.shutdown(false);
  }

  private static Stream<UserSubscription> createSubscription(int from, int to, Frequency frequency) {
    return IntStream.range(from, to).mapToObj(UserId::new).map(userId -> new UserSubscription(userId, frequency));
  }
}
