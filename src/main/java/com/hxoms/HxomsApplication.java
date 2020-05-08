package com.hxoms;

import com.hxoms.common.utils.UserInfoUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HxomsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HxomsApplication.class, args);
    }

}
