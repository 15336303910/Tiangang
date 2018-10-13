package cn.plou.web.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author liuxiadong
 * @Date 2018年7月2日 上午11:41:34
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createHeatChargeRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())//对所有api进行监控
                .paths(PathSelectors.any())//对所有路径进行监控
                 //不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .build();
    }
    
    @Bean
    public Docket createHeatManageRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.groupName("用热管理Restful APIs")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.heatManage"))//对所有api进行监控
                .paths(PathSelectors.any())//对所有路径进行监控
                 //不显示错误的接口地址
                .paths(Predicates.not(PathSelectors.regex("/error.*")))//错误路径不监控
                .build();
    }

    //构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("用热管理Restful APIs")
                //创建人
                .contact(new Contact("employee", "xxx@qq.com", "xxx@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}