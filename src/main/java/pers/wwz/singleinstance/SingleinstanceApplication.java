package pers.wwz.singleinstance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SingleinstanceApplication {

    public static void main(String[] args) {
        System.out.println("我是王大锤!");
        SpringApplication.run(SingleinstanceApplication.class, args);
    }

}
