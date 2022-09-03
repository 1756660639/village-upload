package com.village.villageupload;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.village.villageupload.**.mapper")
public class VillageUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(VillageUploadApplication.class, args);
        System.out.printf("village-upload文件系统启动成功");
    }

}
