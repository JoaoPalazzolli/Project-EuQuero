package com.project.euquero.configs;

import com.cloudinary.Cloudinary;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "cloudinary")
@Data
public class CloudinaryConfig {

    private String cloudName;

    private String apiKey;

    private String apiSecret;

    @Bean
    Cloudinary cloudinary(){
        return new Cloudinary(
                Map.of("cloud_name", this.cloudName,
                        "api_key", this.apiKey,
                            "api_secret", this.apiSecret));
    }
}
