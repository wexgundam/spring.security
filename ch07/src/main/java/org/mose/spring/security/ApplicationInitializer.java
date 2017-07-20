package org.mose.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 以jar包形式部署
 *
 * @author 靳磊
 * @date  2017/07/06
 *
 */
@SpringBootApplication
public class ApplicationInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationInitializer.class, args);
    }
}


