package ru.yandex.ivan_vaysman.test_task.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder();
    }
}
