package io.memoria.notifications.core.domain.service;

import io.memoria.notifications.core.domain.model.UserId;

import java.util.List;

public interface UserEligibilityService {
  List<UserId> filter(List<UserId> userIds);
}
