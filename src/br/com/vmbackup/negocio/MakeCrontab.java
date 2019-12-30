package br.com.vmbackup.negocio;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class MakeCrontab {

	public static Boolean statusScheduler = false;

	public void schedule(Boolean option) {

		ReadFiles readFiles = new ReadFiles();
		String hourBegin = readFiles.getHour().getHourBegin();
		System.out.println(hourBegin);
		String hour[] = hourBegin.split(":");

		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler scheduler = sf.getScheduler();
			if (option) {
				if (!scheduler.isStarted()) {
					scheduler.start();
					statusScheduler = true;
					JobDetail job = JobBuilder.newJob(ImplementThread.class).withIdentity("threadClone", "group1").build();
					Trigger trigger = TriggerBuilder.newTrigger().withIdentity("threadTrigger", "group1")
							.withSchedule(CronScheduleBuilder.cronSchedule("0 " + hour[1] + " " + hour[0] + " * * ?"))
							.build();
					scheduler.scheduleJob(job, trigger);
				} else {
					Trigger oldTrigger = scheduler.getTrigger(TriggerKey.triggerKey("threadTrigger", "group1"));

					Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity("threadTrigger", "group1")
							.withSchedule(CronScheduleBuilder.cronSchedule("0 " + hour[1] + " " + hour[0] + " * * ?"))
							.build();
					scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
				}
			}
			if (!option) {
				if (scheduler.isStarted()) {
					scheduler.shutdown();
					statusScheduler = false;
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}