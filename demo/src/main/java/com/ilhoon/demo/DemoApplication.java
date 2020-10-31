package com.ilhoon.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication 이란?
 * @ComponentScan, @Configuration, @EnableAutoConfiguration을 함께 사용한 것과 같음
 * 해당 annotation을 설정한 클래스가 있는 package를 최상위 패키지로 인식하고 ComponentScan을 수행
 */
@SpringBootApplication
public class DemoApplication {
    /**
     * SpringContext를 실행해 Application을 구동한다.
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
