package io.memoria.notifications.infra.in.subscription.quartz;

import io.memoria.notifications.core.application.NotificationService;
import io.memoria.notifications.core.domain.model.Frequency;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class NotificationsScheduler {
  private static final Logger log = LoggerFactory.getLogger(NotificationsScheduler.class.getName());
  public final String jobGroup;
  private final Scheduler scheduler;

  public NotificationsScheduler(String jobGroup, NotificationService notificationService, Frequency... frequencies)
          throws SchedulerException {
    this.jobGroup = jobGroup;
    this.scheduler = StdSchedulerFactory.getDefaultScheduler();
    for (Frequency frequency : frequencies) {
      var map = new JobDataMap();
      map.put(Frequency.class.getSimpleName(), frequency);
      map.put(NotificationService.class.getSimpleName(), notificationService);
      var jobDetail = createJobDetail(frequency.name().toLowerCase(), map);
      var trigger = createTrigger(frequency.name().toLowerCase(), frequency.getMillis());
      scheduler.scheduleJob(jobDetail, trigger);
    }
  }

  public void start() throws SchedulerException {
    log.info(STR."Starting \{this.getClass().getSimpleName()}");
    scheduler.start();
  }

  public void shutdown(boolean waitForJobsToComplete) throws SchedulerException {
    log.info(STR."Shutting down \{this.getClass().getSimpleName()}");
    scheduler.shutdown(waitForJobsToComplete);
  }

  private JobDetail createJobDetail(String name, JobDataMap extra) {
    var jobName = STR."\{SubscribersNotificationJob.class.getSimpleName()}_every_\{name}";
    return newJob(SubscribersNotificationJob.class).withIdentity(jobName, jobGroup).usingJobData(extra).build();
  }

  private Trigger createTrigger(String name, int milliseconds) {
    var triggerName = STR."\{name}_TRIGGER";
    var schedule = simpleSchedule().withIntervalInMilliseconds(milliseconds).repeatForever();
    return newTrigger().withIdentity(triggerName, jobGroup).startNow().withSchedule(schedule).build();
  }
}
