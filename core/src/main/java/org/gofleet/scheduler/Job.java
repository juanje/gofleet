package org.gofleet.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

public abstract class Job implements StatefulJob {
	private final static Log LOG = LogFactory.getLog(Job.class);

	public abstract String getName();

	public abstract String getDescription();

	public abstract Integer getFrequency();

	public abstract void run();

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			run();
		} catch (Throwable t) {
			LOG.error("Error executing job.", t);
		}
	}
}
