package cn.plou.web.common.config;

import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *swagger是一款书写API文档的框架，前后端的唯一联系API接口
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig  {

    @Bean
    public Docket plmeterApiBase() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("info")
                .apiInfo(plmeterApiInfoBase()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.system.baseMessage"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoBase() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-用户基础信息")
                .version("1.0")
                .build();
    }

   @Bean
    public Docket plmeterApiMeter() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("dev")
                .apiInfo(plmeterApiInfoMeter()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.system.meterMessage"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoMeter() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-设备信息")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiCommon() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("common")
                .apiInfo(plmeterApiInfoCommon()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.system.commonMessage"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoCommon() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-公用信息")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiPermission() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("role")
                .apiInfo(plmeterApiInfoPermission()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.system.permission"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoPermission() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-权限")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiOrganization() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("organization")
                .apiInfo(plmeterApiInfoOrganization()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.system.organizationMessage"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoOrganization() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-组织信息")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiDataAnalysis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("dataAnalysis")
                .apiInfo(plmeterApiInfoDataAnalysis()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.balance.dataAnalysis"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoDataAnalysis() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-数据分析")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiDistribution() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("distribution")
                .apiInfo(plmeterApiInfoDistribution()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.balance.distribution"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoDistribution() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-楼宇分布")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiRegulate() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("regulate")
                .apiInfo(plmeterInfoApiRegulate()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.balance.regulate"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterInfoApiRegulate() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-平衡控制")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket plmeterApiRegulateAnalysis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("regulateAnalysis")
                .apiInfo(plmeterApiInfoRegulateAnalysis()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("cn.plou.web.balance.regulateAnalysis"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo plmeterApiInfoRegulateAnalysis() {
        return new ApiInfoBuilder()
                .title("天罡仪表项目API-控制分析")
                .version("1.0")
                .build();
    }
}
