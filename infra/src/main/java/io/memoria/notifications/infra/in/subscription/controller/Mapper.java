package io.memoria.notifications.infra.in.subscription.controller;

import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;
import io.memoria.notifications.core.domain.model.UserSubscription;
import io.memoria.notifications.infra.vm.SubscriptionVM;
import io.memoria.notifications.infra.vm.SubscriptionVM.FrequencyVMEnum;

public class Mapper {
  public static UserSubscription from(SubscriptionVM vm) {
    String name = vm.getFrequencyVM().name();
    return new UserSubscription(new UserId(vm.getUserId()), Frequency.valueOf(name));
  }

  public static SubscriptionVM to(UserSubscription sub) {
    var frequencyVM = FrequencyVMEnum.fromValue(sub.frequency().name());
    return SubscriptionVM.builder().userId(sub.userId().value()).frequencyVM(frequencyVM).build();
  }
}
