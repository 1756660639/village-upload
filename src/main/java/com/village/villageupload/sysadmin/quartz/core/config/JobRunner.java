package com.village.villageupload.sysadmin.quartz.core.config;

import com.village.villageupload.sysadmin.quartz.core.utils.QuartzManage;
import com.village.villageupload.sysadmin.quartz.entity.QuartzJob;
import com.village.villageupload.sysadmin.quartz.service.Tst01ScheduletaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class JobRunner implements ApplicationRunner {

  @Autowired
  private Tst01ScheduletaskService scheduletaskService;

  @Autowired
  private QuartzManage quartzManage;

  @Override
  public void run(ApplicationArguments applicationArguments) {
    log.info("--------------------注入定时任务---------------------");

    QuartzJob quartzJob = new QuartzJob();
    quartzJob.setIsPause("1");
    List<QuartzJob> quartzJobs = scheduletaskService.listAll(quartzJob);
    quartzJobs.forEach(quartzManage::addJob);

    log.info("--------------------定时任务注入完成---------------------");
  }
}
