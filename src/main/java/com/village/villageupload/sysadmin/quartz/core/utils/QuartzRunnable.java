package com.village.villageupload.sysadmin.quartz.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Slf4j
public class QuartzRunnable implements Runnable {

  private Object target;
  private Method method;
  private String params;

  public QuartzRunnable(String beanName, String methodName, String params) throws NoSuchMethodException {
    this.target = ApplicationContextHolder.getBean(beanName);
    this.params = params;

    if (!StringUtils.isEmpty(params)) {
      this.method = target.getClass().getDeclaredMethod(methodName, String.class);
    } else {
      this.method = target.getClass().getDeclaredMethod(methodName);
    }
  }

  @Override
  public void run() {
    try {
      ReflectionUtils.makeAccessible(method);
      if (!StringUtils.isEmpty(params)) {
        method.invoke(target, params);
      } else {
        method.invoke(target);
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }
}
