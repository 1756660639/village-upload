package com.village.villageupload.sysadmin.quartz.core.utils;

import com.village.villageupload.sysadmin.quartz.entity.QuartzJob;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJobLog;
import com.village.villageupload.sysadmin.quartz.service.Tst01ScheduletaskService;
import com.village.villageupload.sysadmin.quartz.service.Tst02ScheduletasklogService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Async
public class ExecutionJob extends QuartzJobBean {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private ExecutorService executorService = Executors.newSingleThreadExecutor();

  @Autowired
  private Tst01ScheduletaskService sheduletaskService;

  @Autowired
  private Tst02ScheduletasklogService sheduletasklogService;

  @Override
  protected void executeInternal(JobExecutionContext context) {
    QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);

    QuartzJobLog log = new QuartzJobLog();
    log.setJobName(quartzJob.getJobName());
    log.setBeanName(quartzJob.getBeanName());
    log.setMethodName(quartzJob.getMethodName());
    log.setParams(quartzJob.getParams());

    long startTime = System.currentTimeMillis();
    log.setCronExpression(quartzJob.getCronExpression());

    try {
      logger.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
      QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(), quartzJob.getParams());
      Future<?> future = executorService.submit(task);
      future.get();

      long times = System.currentTimeMillis() - startTime;
      log.setTime(times);
      log.setIsSuccess("1");
      logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
    } catch (Exception e) {
      logger.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
      long times = System.currentTimeMillis() - startTime;
      log.setTime(times);

      log.setIsSuccess("0");
      log.setExceptionDetail(e.getMessage());
      quartzJob.setIsPause("0");

      sheduletaskService.update(quartzJob);
    } finally {
      sheduletasklogService.insert(log);
    }
  }
}
