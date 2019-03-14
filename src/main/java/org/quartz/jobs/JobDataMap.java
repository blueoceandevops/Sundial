package org.quartz.jobs;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Holds state information for <code>Job</code> instances.
 *
 * <p><code>JobDataMap</code> instances are stored once when the <code>Job</code> is added to a
 * scheduler. They are also re-persisted after every execution of <code>StatefulJob</code>
 * instances.
 *
 * <p><code>JobDataMap</code> instances can also be stored with a <code>Trigger</code>. This can be
 * useful in the case where you have a Job that is stored in the scheduler for regular/repeated use
 * by multiple Triggers, yet with each independent triggering, you want to supply the Job with
 * different data inputs.
 *
 * <p>The <code>JobExecutionContext</code> passed to a Job at execution time also contains a
 * convenience <code>JobDataMap</code> that is the result of merging the contents of the trigger's
 * JobDataMap (if any) over the Job's JobDataMap (if any).
 *
 * @author James House
 * @author timmolter
 */
public class JobDataMap extends HashMap<String, Object> implements Serializable {

  private static final long serialVersionUID = -6939901990106713909L;

  /** Create an empty <code>JobDataMap</code>. */
  public JobDataMap() {

    super(15);
  }

  /**
   * Constructor - creates a shallow copy of the passed in JobDataMap
   *
   * @param jobDataMap
   */
  public JobDataMap(JobDataMap jobDataMap) {

    super(jobDataMap);
  }

  /**
   * Retrieve the identified <code>String</code> value from the <code>JobDataMap</code>.
   *
   * @throws ClassCastException if the identified object is not a String.
   */
  public String getString(String key) {

    Object obj = get(key);

    try {
      return (String) obj;
    } catch (Exception e) {
      throw new ClassCastException("Identified object is not a String.");
    }
  }

  public JobDataMap shallowCopy() {

    return new JobDataMap(this);
  }
}
