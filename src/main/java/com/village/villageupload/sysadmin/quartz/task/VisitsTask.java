
package com.village.villageupload.sysadmin.quartz.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VisitsTask {


  public void run() {
    log.info("执行成功");
  }

  public void run1(String str) {
    log.info("执行成功，参数为：{}" + str);
  }

}
