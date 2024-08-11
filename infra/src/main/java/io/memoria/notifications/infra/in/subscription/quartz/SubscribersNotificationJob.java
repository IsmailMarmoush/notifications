package io.memoria.notifications.infra.in.subscription.quartz;

import io.memoria.notifications.core.application.NotificationService;
import io.memoria.notifications.core.domain.model.Frequency;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SubscribersNotificationJob implements Job {
  private static final Logger log = LoggerFactory.getLogger(SubscribersNotificationJob.class.getName());

  @Override
  public void execute(JobExecutionContext ctx) throws JobExecutionException {
    try {
      var jobMap = ctx.getJobDetail().getJobDataMap();
      var frequency = (Frequency) jobMap.get(Frequency.class.getSimpleName());
      var notificationService = (NotificationService) jobMap.get(NotificationService.class.getSimpleName());
      log.debug("{} has started.", ctx.getJobDetail().getKey().getName());
      var now = System.currentTimeMillis();
      log.debug("Marking subscribers at {}", now);
      notificationService.markSubscribers(frequency, now);
      log.debug("{} has ended.", ctx.getJobDetail().getKey().getName());
    } catch (Throwable t) {
      t.printStackTrace();
      throw new JobExecutionException(t);
    }
  }
}
