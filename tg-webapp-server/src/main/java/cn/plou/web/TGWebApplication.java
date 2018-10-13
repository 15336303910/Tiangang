package cn.plou.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@EnableTransactionManagement
@MapperScan("cn.plou.web.**.dao")
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class TGWebApplication extends SpringBootServletInitializer {


	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(TGWebApplication.class, args);
		
		TGWebApplication tg = new TGWebApplication();
		System.out.println("tg-webapp-server启动成功..........................\n");

	}

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TGWebApplication.class);
	}
	


	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}

