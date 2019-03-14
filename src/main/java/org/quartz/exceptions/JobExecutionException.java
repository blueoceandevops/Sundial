package org.quartz.exceptions;

import org.quartz.core.JobExecutionContext;
import org.quartz.core.Scheduler;
import org.quartz.jobs.Job;

/**
 * An exception that can be thrown by a <code>{@link org.quartz.jobs.Job}</code> to indicate to the
 * Quartz <code>{@link Scheduler}</code> that an error occurred while executing, and whether or not
 * the <code>Job</code> requests to be re-fired immediately (using the same <code>
 * {@link JobExecutionContext}</code>, or whether it wants to be unscheduled.
 *
 * <p>Note that if the flag for 'refire immediately' is set, the flags for unscheduling the Job are
 * ignored.
 *
 * @see Job
 * @see JobExecutionContext
 * @see SchedulerException
 * @author James House
 */
public class JobExecutionException extends SchedulerException {

  /*
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Data members.
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   */

  private boolean refire = false;

  private boolean unscheduleTrigg = false;

  private boolean unscheduleAllTriggs = false;

  /*
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Constructors.
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   */

  /**
   * Create a JobExcecutionException, with the 're-fire immediately' flag set to <code>false</code>.
   */
  public JobExecutionException() {}

  /**
   * Create a JobExcecutionException with the given underlying exception, and the 're-fire
   * immediately' flag set to the given value.
   */
  public JobExecutionException(Throwable cause, boolean refireImmediately) {

    super(cause);

    refire = refireImmediately;
  }

  /*
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Interface.
   * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
   */

  public void setRefireImmediately(boolean refire) {

    this.refire = refire;
  }

  public boolean refireImmediately() {

    return refire;
  }

  public void setUnscheduleFiringTrigger(boolean unscheduleTrigg) {

    this.unscheduleTrigg = unscheduleTrigg;
  }

  public boolean unscheduleFiringTrigger() {

    return unscheduleTrigg;
  }

  public void setUnscheduleAllTriggers(boolean unscheduleAllTriggs) {

    this.unscheduleAllTriggs = unscheduleAllTriggs;
  }

  public boolean unscheduleAllTriggers() {

    return unscheduleAllTriggs;
  }
}
