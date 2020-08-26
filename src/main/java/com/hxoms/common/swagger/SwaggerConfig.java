package com.hxoms.common.swagger;


import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig{

    public ApiInfo apiInfo() {
        Contact contact = new Contact("学霸王学川", null, null);
        return new ApiInfoBuilder()
                .title("出国（境）信息管理系统")
                .description("系统功能接口")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("www.baidu.com")
                .version("V1.0.0")
                .contact(contact)
                .build();
    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .groupName("首页")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hxoms.modules.roadPage.controller"))
                .build();
    }
    @Bean
    public Docket certificateModulDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .groupName("证照管理")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hxoms.modules.passportCard"))
                .build();
    }

    @Bean
    public Docket docket2(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hxoms.modules"))
                .paths(PathSelectors.any())
                .build();
    }

}
