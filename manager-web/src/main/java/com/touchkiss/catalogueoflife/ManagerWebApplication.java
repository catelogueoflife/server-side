package com.touchkiss.catalogueoflife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created on 2020/03/18 16:56
 *
 * @author Touchkiss
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class ManagerWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerWebApplication.class);
    }
}
