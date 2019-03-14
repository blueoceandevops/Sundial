package org.quartz.core;

import java.util.ArrayList;
import java.util.List;
import org.quartz.QuartzScheduler;
import org.quartz.plugins.SchedulerPlugin;

/**
 * Contains all of the resources (<code>JobStore</code>,<code>ThreadPool</code>, etc.) necessary to
 * create a <code>{@link QuartzScheduler}</code> instance.
 *
 * @see QuartzScheduler
 * @author James House
 */
public class QuartzSchedulerResources {

  private String threadName;

  private ThreadPool threadPool;

  private JobStore jobStore;

  private JobRunShellFactory jobRunShellFactory;

  private List<SchedulerPlugin> schedulerPlugins = new ArrayList<SchedulerPlugin>(10);

  private boolean makeSchedulerThreadDaemon = false;

  private boolean threadsInheritInitializersClassLoadContext = false;

  private long batchTimeWindow;

  private int maxBatchSize;

  /** Create an instance with no properties initialized. */
  public QuartzSchedulerResources() {

    // do nothing...
  }

  /** Get the name for the <code>{@link QuartzSchedulerThread}</code>. */
  public String getThreadName() {

    return threadName;
  }

  /**
   * Set the name for the <code>{@link QuartzSchedulerThread}</code>.
   *
   * @exception IllegalArgumentException if name is null or empty.
   */
  public void setThreadName(String threadName) {

    if (threadName == null || threadName.trim().length() == 0) {
      throw new IllegalArgumentException("Scheduler thread name cannot be empty.");
    }

    this.threadName = threadName;
  }

  /**
   * Get the <code>{@link ThreadPool}</code> for the <code>{@link QuartzScheduler}</code> to use.
   */
  public ThreadPool getThreadPool() {

    return threadPool;
  }

  /**
   * Set the <code>{@link ThreadPool}</code> for the <code>{@link QuartzScheduler}</code> to use.
   *
   * @exception IllegalArgumentException if threadPool is null.
   */
  public void setThreadPool(ThreadPool threadPool) {

    if (threadPool == null) {
      throw new IllegalArgumentException("ThreadPool cannot be null.");
    }

    this.threadPool = threadPool;
  }

  /** Get the <code>{@link JobStore}</code> for the <code>{@link QuartzScheduler}</code> to use. */
  public JobStore getJobStore() {

    return jobStore;
  }

  /**
   * Set the <code>{@link JobStore}</code> for the <code>{@link QuartzScheduler}</code> to use.
   *
   * @exception IllegalArgumentException if jobStore is null.
   */
  public void setJobStore(JobStore jobStore) {

    if (jobStore == null) {
      throw new IllegalArgumentException("JobStore cannot be null.");
    }

    this.jobStore = jobStore;
  }

  /**
   * Get the <code>{@link JobRunShellFactory}</code> for the <code>{@link QuartzScheduler}</code> to
   * use.
   */
  public JobRunShellFactory getJobRunShellFactory() {

    return jobRunShellFactory;
  }

  /**
   * Set the <code>{@link JobRunShellFactory}</code> for the <code>{@link QuartzScheduler}</code> to
   * use.
   *
   * @exception IllegalArgumentException if jobRunShellFactory is null.
   */
  public void setJobRunShellFactory(JobRunShellFactory jobRunShellFactory) {

    if (jobRunShellFactory == null) {
      throw new IllegalArgumentException("JobRunShellFactory cannot be null.");
    }

    this.jobRunShellFactory = jobRunShellFactory;
  }

  /**
   * Add the given <code>{@link org.quartz.plugins.SchedulerPlugin}</code> for the <code>
   * {@link QuartzScheduler}</code> to use. This method expects the plugin's "initialize" method to
   * be invoked externally (either before or after this method is called).
   */
  public void addSchedulerPlugin(SchedulerPlugin plugin) {

    schedulerPlugins.add(plugin);
  }

  /**
   * Get the <code>List</code> of all <code>{@link org.quartz.plugins.SchedulerPlugin}</code>s for
   * the <code>{@link QuartzScheduler}</code> to use.
   */
  public List<SchedulerPlugin> getSchedulerPlugins() {

    return schedulerPlugins;
  }

  /**
   * Get whether to mark the Quartz scheduling thread as daemon.
   *
   * @see Thread#setDaemon(boolean)
   */
  public boolean getMakeSchedulerThreadDaemon() {

    return makeSchedulerThreadDaemon;
  }

  /**
   * Set whether to mark the Quartz scheduling thread as daemon.
   *
   * @see Thread#setDaemon(boolean)
   */
  public void setMakeSchedulerThreadDaemon(boolean makeSchedulerThreadDaemon) {

    this.makeSchedulerThreadDaemon = makeSchedulerThreadDaemon;
  }

  /**
   * Get whether to set the class load context of spawned threads to that of the initializing
   * thread.
   */
  public boolean isThreadsInheritInitializersClassLoadContext() {

    return threadsInheritInitializersClassLoadContext;
  }

  /**
   * Set whether to set the class load context of spawned threads to that of the initializing
   * thread.
   */
  public void setThreadsInheritInitializersClassLoadContext(
      boolean threadsInheritInitializersClassLoadContext) {

    this.threadsInheritInitializersClassLoadContext = threadsInheritInitializersClassLoadContext;
  }

  public long getBatchTimeWindow() {

    return batchTimeWindow;
  }

  public void setBatchTimeWindow(long batchTimeWindow) {

    this.batchTimeWindow = batchTimeWindow;
  }

  public int getMaxBatchSize() {

    return maxBatchSize;
  }

  public void setMaxBatchSize(int maxBatchSize) {

    this.maxBatchSize = maxBatchSize;
  }
}
