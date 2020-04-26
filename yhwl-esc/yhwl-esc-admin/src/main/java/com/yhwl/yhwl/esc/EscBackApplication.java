package com.yhwl.yhwl.esc;

import com.yhwl.yhwl.common.feign.annotation.EnableYhwlFeignClients;
import com.yhwl.yhwl.common.security.annotation.EnableYhwlResourceServer;
import com.yhwl.yhwl.common.swagger.annotation.EnableYhwlSwagger2;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@EnableYhwlSwagger2
@SpringCloudApplication
@EnableYhwlFeignClients
@EnableYhwlResourceServer
public class EscBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(EscBackApplication.class);
	}
}
