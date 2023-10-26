package com.project.euquero.configs;

import com.project.euquero.models.UserPermissionPK;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApiConfig {

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    UserPermissionPK userPermissionPK(){
        return new UserPermissionPK();
    }
}
