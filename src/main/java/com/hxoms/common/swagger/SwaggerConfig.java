package com.hxoms.common.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public ApiInfo apiInfo() {
        Contact contact = new Contact("王学川", "www.baidu.com", "863722195@qq.com");
        return new ApiInfoBuilder()
                .title("出国（境）信息管理系统")
                .description("Swagger2 接口在线测试")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("www.baidu.com")
                .version("V1.0")
                .contact(contact)
                .build();
    }

    @Bean
    public Docket docket1(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(true)
                .groupName("首页")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hxoms.modules.roadPage.controller"))
                .build();
    }

}
