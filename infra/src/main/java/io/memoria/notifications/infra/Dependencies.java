package io.memoria.notifications.infra;

import io.helidon.http.media.jackson.JacksonRuntimeException;
import io.helidon.logging.common.LogConfig;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import io.memoria.atom.core.text.SerializableTransformer;
import io.memoria.atom.core.text.TextTransformer;
import io.memoria.atom.jackson.JacksonTransformerBuilder;
import io.memoria.notifications.core.application.NotificationService;
import io.memoria.notifications.core.application.subscription.SelectionHistoryRepo;
import io.memoria.notifications.core.application.subscription.UserSubscriptionRepo;
import io.memoria.notifications.core.application.subscription.memory.MemSelectionHistory;
import io.memoria.notifications.core.application.subscription.memory.MemUserSubscription;
import io.memoria.notifications.core.domain.service.RandomUserEligibilityService;
import io.memoria.notifications.infra.config.Config;
import io.memoria.notifications.infra.in.subscription.controller.SubscriptionController;
import io.memoria.notifications.infra.out.subscription.h2.H2SelectionHistory;
import io.memoria.notifications.infra.out.subscription.h2.H2UserSubscription;
import io.memoria.notifications.infra.out.subscription.postgres.PgSelectionHistory;
import io.memoria.notifications.infra.out.subscription.postgres.PgUserSubscription;
import lombok.Getter;

@Getter
public class Dependencies {
  private final Config config;
  private final NotificationService notificationService;
  private final JacksonTransformerBuilder transformerBuilder;

  public Dependencies(Config config) {
    this.config = config;
    var userSubscriptionRepo = subscriptionRepo();
    var selectionHistoryRepo = selectionHistoryRepo();
    var userSelectionService = new RandomUserEligibilityService(config.percentage());
    this.notificationService = new NotificationService(userSubscriptionRepo,
                                                       userSelectionService,
                                                       selectionHistoryRepo);
    this.transformerBuilder = JacksonTransformerBuilder.json().withDefaults();

    // Global setup
    LogConfig.configureRuntime();
    //    io.helidon.config.Config.global(io.helidon.config.Config.create());
  }

  public WebServer server() {
    var webServer = WebServer.builder()
                             .host(config.httpServerConfig().host())
                             .port(config.httpServerConfig().httpPort())
                             .routing(this::routing);
    return webServer.build();
  }

  private UserSubscriptionRepo subscriptionRepo() {
    return switch (config.subscriptionDB()) {
      case MEMORY -> new MemUserSubscription();
      case H2 -> new H2UserSubscription();
      case POSTGRESQL -> new PgUserSubscription();
    };
  }

  private SelectionHistoryRepo selectionHistoryRepo() {
    return switch (config.subscriptionDB()) {
      case MEMORY -> new MemSelectionHistory();
      case H2 -> new H2SelectionHistory();
      case POSTGRESQL -> new PgSelectionHistory();
    };
  }

  public TextTransformer textTransformer() {
    return switch (config.textTransformerType()) {
      case COMPACT_JSON -> transformerBuilder.asTextTransformer();
      case PRETTY_JSON -> transformerBuilder.withPrettyFormat().asTextTransformer();
      case SERIALIZABLE -> new SerializableTransformer();
    };
  }

  public void routing(HttpRouting.Builder routing) {
    routing.error(JacksonRuntimeException.class, new JsonErrorHandler())
           //           .error(UnrecognizedPropertyException.class, new JsonErrorHandler())
           .register("/notifications/subscriptions", new SubscriptionController(notificationService));
  }
}
