package edu.pnu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FlowAlarmApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlowAlarmApplication.class, args);
	}

}
