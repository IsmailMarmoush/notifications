package io.memoria.notifications.core.domain.service;

import io.memoria.notifications.core.domain.model.UserId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomUserEligibilityService implements UserEligibilityService {
  private final double percentage;

  public RandomUserEligibilityService(double percentage) {
    if (percentage > 1)
      throw new IllegalArgumentException("Percentage is more than one");
    this.percentage = percentage;
  }

  @Override
  public List<UserId> filter(List<UserId> userIds) {
    int subListSize = (int) (userIds.size() * percentage);
    var subList = userIds.subList(0, subListSize);
    var result = new ArrayList<>(subList);
    Collections.shuffle(result);
    return result;
  }
}
