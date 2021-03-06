package cn.plou.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@EnableTransactionManagement
@MapperScan("cn.plou.web.**.dao")
@SpringBootApplication
@ComponentScan({"cn.plou"})
@EnableScheduling
@EnableCaching
public class TGWebApplication extends SpringBootServletInitializer {


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TGWebApplication.class, args);

        TGWebApplication tg = new TGWebApplication();
        System.out.println("       _                            _            \n" +
                "      | |                          | |           \n" +
                " _ __ | | ___  _   _ _ __ ___   ___| |_ ___ _ __ \n" +
                "| '_ \\| |/ _ \\| | | | '_ ` _ \\ / _ \\ __/ _ \\ '__|\n" +
                "| |_) | | (_) | |_| | | | | | |  __/ ||  __/ |   \n" +
                "| .__/|_|\\___/ \\__,_|_| |_| |_|\\___|\\__\\___|_|   \n" +
                "| |                                              \n" +
                "|_|   ");
        System.out.println("tg-webapp-server启动成功.............");

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

