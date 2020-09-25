package com.hxoms.common.interceptor;/*
 * @description:
 * @author 杨波
 * @date:2020-09-24
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.TimeZone;

@Configuration
@ConditionalOnClass(Jackson2ObjectMapperBuilder.class)
public class JacksonAutoConfiguration {
    JacksonAutoConfiguration(){}

    @Bean
    @Primary
    @ConditionalOnMissingBean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder){
        ObjectMapper om=builder.createXmlMapper(false).build();
        om.setTimeZone(TimeZone.getDefault());
        return om;
    }
}
