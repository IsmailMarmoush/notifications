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
    List<UserId> shuffledUserIds = new ArrayList<>(userIds);
    Collections.shuffle(shuffledUserIds);
    int subListSize = (int) (userIds.size() * percentage);
    return shuffledUserIds.subList(0, subListSize);
  }
}
