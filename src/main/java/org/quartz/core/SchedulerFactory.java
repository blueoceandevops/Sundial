package org.quartz.core;

import org.knowm.sundial.plugins.AnnotationJobTriggerPlugin;
import org.quartz.QuartzScheduler;
import org.quartz.exceptions.SchedulerException;
import org.quartz.plugins.management.ShutdownHookPlugin;
import org.quartz.plugins.xml.XMLSchedulingDataProcessorPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of <code>{@link org.quartz.core.SchedulerFactory}</code> that does all of its
 * work of creating a <code>QuartzScheduler</code> instance.
 *
 * @author James House
 * @author Anthony Eden
 * @author Mohammad Rezaei
 * @author timmolter
 */
public class SchedulerFactory {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private QuartzScheduler quartzScheduler = null;

  private int threadPoolSize = 10; // default size is 10
  private String packageName = null;

  /**
   * @param threadPoolSize
   * @param packageName
   * @return Returns a handle to the Scheduler produced by this factory. Initialized with given
   *     threadPoolSize and packageName where it looks for annotated Job classes
   * @throws SchedulerException
   */
  public Scheduler getScheduler(int threadPoolSize, String packageName) throws SchedulerException {

    this.threadPoolSize = threadPoolSize;
    this.packageName = packageName;

    return getScheduler();
  }

  /**
   * @param threadPoolSize
   * @return Returns a handle to the Scheduler produced by this factory. Initialized with given
   *     threadPoolSize
   * @throws SchedulerException
   */
  public Scheduler getScheduler(int threadPoolSize) throws SchedulerException {

    this.threadPoolSize = threadPoolSize;

    return getScheduler();
  }

  /**
   * Returns a handle to the Scheduler produced by this factory.
   *
   * <p>If one of the <code>initialize</code> methods has not be previously called, then the default
   * (no-arg) <code>initialize()</code> method will be called by this method.
   */
  public Scheduler getScheduler() throws SchedulerException {

    if (quartzScheduler != null) {
      return quartzScheduler;
    }

    return instantiate();
  }

  private Scheduler instantiate() throws SchedulerException {

    // Setup SimpleThreadPool
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    SimpleThreadPool threadPool = new SimpleThreadPool();
    threadPool.setThreadCount(threadPoolSize);

    // Setup RAMJobStore
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    JobStore jobstore = new RAMJobStore();

    // Set up any TriggerListeners
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    boolean tpInited = false;
    boolean qsInited = false;

    // Fire everything up
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    try {

      JobRunShellFactory jrsf =
          new StandardJobRunShellFactory(); // Create correct run-shell factory...

      QuartzSchedulerResources quartzSchedulerResources = new QuartzSchedulerResources();
      quartzSchedulerResources.setThreadName("Quartz Scheduler Thread");
      quartzSchedulerResources.setJobRunShellFactory(jrsf);
      quartzSchedulerResources.setMakeSchedulerThreadDaemon(false);
      quartzSchedulerResources.setThreadsInheritInitializersClassLoadContext(false);
      quartzSchedulerResources.setBatchTimeWindow(0L);
      quartzSchedulerResources.setMaxBatchSize(1);
      quartzSchedulerResources.setInterruptJobsOnShutdown(true);
      quartzSchedulerResources.setInterruptJobsOnShutdownWithWait(true);
      quartzSchedulerResources.setThreadPool(threadPool);
      threadPool.setThreadNamePrefix("Quartz_Scheduler_Worker");
      threadPool.initialize();
      tpInited = true;

      quartzSchedulerResources.setJobStore(jobstore);

      quartzScheduler = new QuartzScheduler(quartzSchedulerResources);
      qsInited = true;

      // Set up any SchedulerPlugins
      // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
      XMLSchedulingDataProcessorPlugin xmlSchedulingDataProcessorPlugin =
          new XMLSchedulingDataProcessorPlugin();
      xmlSchedulingDataProcessorPlugin.setFailOnFileNotFound(false);
      quartzSchedulerResources.addSchedulerPlugin(xmlSchedulingDataProcessorPlugin);

      ShutdownHookPlugin shutdownHookPlugin = new ShutdownHookPlugin();
      quartzSchedulerResources.addSchedulerPlugin(shutdownHookPlugin);

      AnnotationJobTriggerPlugin annotationJobTriggerPlugin =
          new AnnotationJobTriggerPlugin(packageName);
      quartzSchedulerResources.addSchedulerPlugin(annotationJobTriggerPlugin);

      // fire up job store, and runshell factory
      jobstore.initialize(quartzScheduler.getSchedulerSignaler());
      jobstore.setThreadPoolSize(threadPool.getPoolSize());

      // Initialize plugins now that we have a Scheduler instance.
      xmlSchedulingDataProcessorPlugin.initialize(
          "XMLSchedulingDataProcessorPlugin", quartzScheduler);
      shutdownHookPlugin.initialize("ShutdownHookPlugin", quartzScheduler);
      annotationJobTriggerPlugin.initialize("AnnotationJobTriggerPlugin", quartzScheduler);

      jrsf.initialize(quartzScheduler);

      quartzScheduler.initialize(); // starts the thread

      return quartzScheduler;

    } catch (SchedulerException e) {
      if (qsInited) {
        quartzScheduler.shutdown(false);
      } else if (tpInited) {
        threadPool.shutdown(false);
      }
      throw e;
    } catch (RuntimeException re) {
      if (qsInited) {
        quartzScheduler.shutdown(false);
      } else if (tpInited) {
        threadPool.shutdown(false);
      }
      throw re;
    } catch (Error re) {
      if (qsInited) {
        quartzScheduler.shutdown(false);
      } else if (tpInited) {
        threadPool.shutdown(false);
      }
      throw re;
    }
  }
}
