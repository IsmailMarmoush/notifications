package io.memoria.notifications.core.application.subscription.memory;

import io.memoria.notifications.core.domain.model.Frequency;
import io.memoria.notifications.core.domain.model.UserId;

import java.util.List;

public record SelectionRecord(List<UserId> userIds, Frequency frequency) {}
