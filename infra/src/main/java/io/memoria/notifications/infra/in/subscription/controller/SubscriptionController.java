package io.memoria.notifications.infra.in.subscription.controller;

import io.helidon.http.Status;
import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import io.memoria.notifications.core.application.NotificationService;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;
import io.memoria.notifications.infra.vm.SubscriptionVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscriptionController implements HttpService {
  private static final Logger log = LoggerFactory.getLogger(SubscriptionController.class.getName());

  private final NotificationService notificationService;

  public SubscriptionController(NotificationService notificationService) {
    this.notificationService = notificationService;
  }

  @Override
  public void routing(HttpRules rules) {
    rules.post("", this::createSubscription).get("", this::find);
  }

  public void createSubscription(ServerRequest request, ServerResponse response) {
    var subscriptionVM = request.content().as(SubscriptionVM.class);
    log.info("Creating subscription {}", subscriptionVM);
    UserSubscription userSubscription = Mapper.from(subscriptionVM);
    var sub = notificationService.createSubscription(userSubscription);
    response.status(Status.OK_200).send(Mapper.to(sub));
  }

  public void find(ServerRequest request, ServerResponse response) {
    var startTime = Long.parseLong(request.query().get("startMillis"));
    var endMillis = Long.parseLong(request.query().get("endMillis"));
    var userIds = notificationService.find(startTime, endMillis).stream().map(UserId::value).toList();
    response.status(Status.OK_200).send(userIds);
  }
}
