package com.village.villageupload.sysadmin.quartz.core.config;

import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Configuration
public class QuartzConfig {

  @Bean(name = "scheduler")
  public Scheduler scheduler(QuartzJobFactory quartzJobFactory) throws Exception {
    SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
    factoryBean.setJobFactory(quartzJobFactory);
    factoryBean.afterPropertiesSet();

    Scheduler scheduler = factoryBean.getScheduler();
    scheduler.start();

    return scheduler;
  }

  @Component("quartzJobFactory")
  public static class QuartzJobFactory extends AdaptableJobFactory {

    private final AutowireCapableBeanFactory capableBeanFactory;

    public QuartzJobFactory(AutowireCapableBeanFactory capableBeanFactory) {
      this.capableBeanFactory = capableBeanFactory;
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
      Object jobInstance = super.createJobInstance(bundle);
      capableBeanFactory.autowireBean(jobInstance);

      return jobInstance;
    }
  }
}
