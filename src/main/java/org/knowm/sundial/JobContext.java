package org.knowm.sundial;

import java.util.HashMap;
import java.util.Map;
import org.knowm.sundial.exceptions.RequiredParameterException;
import org.quartz.core.JobExecutionContext;
import org.quartz.triggers.CronTrigger;

/**
 * The JobContext is a Map that contains key value pairs from the Quartz Job's JobDataMap object and
 * any key/value pairs the user wishes to add.
 *
 * @author timothy.molter
 */
public class JobContext {

  // Logger logger = LoggerFactory.getLogger(JobContext.class);

  private static final String KEY_JOB_NAME = "KEY_JOB_NAME";

  private static final String KEY_TRIGGER_NAME = "KEY_TRIGGER_NAME";

  private static final String KEY_TRIGGER_CRON_EXPRESSION = "KEY_TRIGGER_CRON_EXPRESSION";

  /** The Map holding key/value pairs */
  public Map<String, Object> map = new HashMap<String, Object>();

  /**
   * Add all the mappings from the JobExecutionContext to the JobContext
   *
   * @param jobExecutionContext
   */
  public void addQuartzContext(JobExecutionContext jobExecutionContext) {

    for (Object mapKey : jobExecutionContext.getMergedJobDataMap().keySet()) {
      // logger.debug("added key: " + (String) mapKey);
      // logger.debug("added value: " + (String)
      // jobExecutionContext.getMergedJobDataMap().get(mapKey));
      map.put((String) mapKey, jobExecutionContext.getMergedJobDataMap().get(mapKey));
    }
    map.put(KEY_JOB_NAME, jobExecutionContext.getJobDetail().getName());
    map.put(KEY_TRIGGER_NAME, (jobExecutionContext.getTrigger().getName()));
    if (jobExecutionContext.getTrigger() instanceof CronTrigger) {
      map.put(
          KEY_TRIGGER_CRON_EXPRESSION,
          ((CronTrigger) jobExecutionContext.getTrigger()).getCronExpression());
    }
  }

  /**
   * Add a key/value pair to the JobContext
   *
   * @param key
   * @param value
   */
  public void put(String key, Object value) {

    map.put(key, value);
  }

  /**
   * Get a value from a key out of the JobContext
   *
   * @param key
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T get(String key) {

    T value = (T) map.get(key);
    return value;
  }

  /**
   * Get a required value from a key out of the Job Context
   *
   * @param key
   * @return
   */
  @SuppressWarnings("unchecked")
  public <T> T getRequiredValue(String key) {

    T value = (T) map.get(key);
    if (value == null) {
      throw new RequiredParameterException();
    }
    return value;
  }

  /**
   * Convenience method to get the Job Name
   *
   * @return
   */
  public String getJobName() {

    return get(KEY_JOB_NAME);
  }

  /**
   * Convenience method to get the Trigger Name
   *
   * @return
   */
  public String getTriggerName() {

    return get(KEY_TRIGGER_NAME);
  }

  /**
   * Convenience method to get the Cron Expression
   *
   * @return
   */
  public String getCronExpressionName() {

    return get(KEY_TRIGGER_CRON_EXPRESSION);
  }
}
