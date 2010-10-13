/*
 * Copyright (C) 2010, Emergya (http://www.emergya.es)
 *
 * @author <a href="mailto:jlrodriguez@emergya.es">Juan Luís Rodríguez</a>
 * @author <a href="mailto:marias@emergya.es">María Arias</a>
 *
 * This file is part of DEMOGIS
 *
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 */
/**
 * 
 */
package es.emergya.scheduler;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.StatefulJob;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Class used to schedule jobs that are repeated periodically.
 * 
 * @author jlrodriguez
 * @param <T>
 * 
 */
public class CustomScheduler {
	private final static Log LOG = LogFactory.getLog(CustomScheduler.class);
	private org.quartz.Scheduler scheduler;

	/**
	 * Default constructor.
	 * 
	 * @throws SchedulerException
	 *             if the scheduler can't be initialized.
	 */
	public CustomScheduler() throws SchedulerException {
		SchedulerFactory schedFact = new StdSchedulerFactory();
		try {
			this.scheduler = schedFact.getScheduler();

		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error initalizing scheduler", e);
			throw new SchedulerException("Error initializing scheduler", e);
		}
	}

	public <T extends Job> void schedule(final String expression,
			final String jobKey, final Class<T> jobClass)
			throws SchedulerException {

		try {
			final CronTrigger trigger = new CronTrigger(jobKey,
					Scheduler.DEFAULT_GROUP, expression);

			final JobDetail jobDetail = new JobDetail(jobKey,
					Scheduler.DEFAULT_GROUP, jobClass);

			this.scheduler.scheduleJob(jobDetail, trigger);
		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error scheduling job" + jobKey, e);
			throw new SchedulerException("Error scheduling job " + jobKey, e);
		} catch (ParseException e) {
			CustomScheduler.LOG.info("Error scheduling job" + jobKey, e);
			throw new SchedulerException("Error scheduling job " + jobKey, e);
		}
	}

	/**
	 * Schedule a <code>job</code> execution indefinitely every
	 * <code>repeatInterval</code>, with the given <code>jobKey</code>. <br/>
	 * <b>Note:</b> To start execution call method start().
	 * 
	 * @param repeatInterval
	 *            miliseconds between job executions.
	 * @param jobKey
	 *            A name for the job.
	 * @param jobClass
	 *            Class of the job to be executed. It must have a empty
	 *            constructor.
	 * @throws SchedulerException
	 *             if the job can't be scheduled.
	 * 
	 * @author jlrodriguez
	 * @param <T>
	 * 
	 */
	public <T extends StatefulJob> void addJob(final int repeatInterval,
			final String jobKey, final Class<T> jobClass)
			throws SchedulerException {

		Trigger trigger = new SimpleTrigger(jobKey + "Trigger",
				Scheduler.DEFAULT_GROUP, SimpleTrigger.REPEAT_INDEFINITELY,
				repeatInterval);

		JobDetail jobDetail = new JobDetail(jobKey, Scheduler.DEFAULT_GROUP,
				jobClass);

		try {
			this.scheduler.scheduleJob(jobDetail, trigger);
		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error scheduling job" + jobKey, e);
			throw new SchedulerException("Error scheduling job " + jobKey, e);
		}
	}

	/**
	 * Schedule a Job, initializated with params passed in paramsForJob. This
	 * params can be recovered in
	 * {@link Job#execute(org.quartz.JobExecutionContext)} using the same keys
	 * that in paramsForJob.
	 * 
	 * @param <T>
	 *            class implementing {@link StatefulJob}. When the job is fired, a
	 *            new instance of T is created, and its method execute is
	 *            called.
	 * @param repeatInterval
	 *            miliseconds between job executions.
	 * @param jobKey
	 *            A name for the job.
	 * @param jobClass
	 *            Class of the job to be executed. It must have a empty
	 *            constructor.
	 * @param paramsForJob
	 *            a map containing initialization parameters for the Job.
	 * @throws SchedulerException
	 *             if the job can't be scheduled.
	 */
	public <T extends StatefulJob> void addJob(final int repeatInterval,
			final String jobKey, final Class<T> jobClass,
			final Map<String, Object> paramsForJob) throws SchedulerException {

		JobDetail jb = new JobDetail(jobKey,
				org.quartz.Scheduler.DEFAULT_GROUP, jobClass);

		Trigger t = new SimpleTrigger(jobKey + "Trigger",
				Scheduler.DEFAULT_GROUP, SimpleTrigger.REPEAT_INDEFINITELY,
				repeatInterval);

		// Store entries of paramsForJob into jobDataMap.
		if (paramsForJob != null) {
			jb.getJobDataMap().putAll(paramsForJob);
		}

		try {
			this.scheduler.scheduleJob(jb, t);
		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error scheduling job" + jobKey, e);
			throw new SchedulerException("Error scheduling job " + jobKey, e);
		}
	}

	/**
	 * Schedule a {@link Job}, initializated with params passed in paramsForJob.
	 * This params can be recovered in
	 * {@link Job#execute(org.quartz.JobExecutionContext)} using the same keys
	 * that in paramsForJob. It receives a list of {@link JobListener} too.
	 * These listeners are notified of events fired in Jobs. It is very
	 * important {@link JobListener} have an unique name (
	 * {@link JobListener#getName()}).
	 * 
	 * @param <T>
	 *            class implementing {@link StatefulJob}. When the job is fired, a
	 *            new instance of T is created, and its method execute is
	 *            called.
	 * @param repeatInterval
	 *            miliseconds between job executions.
	 * @param jobKey
	 *            A name for the job.
	 * @param jobClass
	 *            Class of the job to be executed. It must have a empty
	 *            constructor.
	 * @param paramsForJob
	 *            a map containing initialization parameters for the Job.
	 * @param listenerList
	 *            a list of {@link JobListener} for the job.
	 * @throws SchedulerException
	 *             if the job can't be scheduled.
	 */
	public <T extends StatefulJob> void addJob(int repeatInterval,
			String jobKey, Class<T> jobClass, Map<String, Object> paramsForJob,
			List<JobListener> listenerList) throws SchedulerException {

		JobDetail jb = new JobDetail(jobKey,
				org.quartz.Scheduler.DEFAULT_GROUP, jobClass);

		Trigger t = new SimpleTrigger(jobKey + "Trigger",
				Scheduler.DEFAULT_GROUP, SimpleTrigger.REPEAT_INDEFINITELY,
				repeatInterval);

		// Store entries of paramsForJob into jobDataMap.
		if (paramsForJob != null) {
			jb.getJobDataMap().putAll(paramsForJob);
		}

		try {
			for (JobListener listener : listenerList) {
				this.scheduler.addJobListener(listener);
				// make sure listener is asociated with job
				jb.addJobListener(listener.getName());
			}
			this.scheduler.scheduleJob(jb, t);

		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error scheduling job" + jobKey, e);
			throw new SchedulerException("Error scheduling job " + jobKey, e);
		}
	}

	/**
	 * Starts execution of the Scheduler.
	 * 
	 * @throws SchedulerException
	 *             if the scheduler can't be started.
	 */
	public void start() throws SchedulerException {
		try {
			this.scheduler.start();
		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error starting scheduler", e);
			throw new SchedulerException("Error starting scheduler", e);
		}
	}

	/**
	 * Finish periodic executions of all Jobs. This method not return until all
	 * jobs have finished their current executions.
	 * 
	 * @throws SchedulerException
	 */
	public void shutdown() throws SchedulerException {
		try {
			this.scheduler.shutdown(false);
		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info("Error shutdowning the scheduler", e);
			throw new SchedulerException("Error halting the scheduler", e);
		}
	}

	public Object getValue(String jobName, String key)
			throws SchedulerException {
		try {
			return this.scheduler
					.getJobDetail(jobName, Scheduler.DEFAULT_GROUP)
					.getJobDataMap().get(key);
		} catch (NullPointerException e) {
			CustomScheduler.LOG.info(
					"Error getting job data map of " + jobName, e);
			throw new SchedulerException("Error getting job data map of "
					+ jobName, e);
		} catch (org.quartz.SchedulerException e) {
			CustomScheduler.LOG.info(
					"Error getting job data map of " + jobName, e);
			throw new SchedulerException("Error getting job data map of "
					+ jobName, e);
		}
	}
}
