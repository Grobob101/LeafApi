package com.yewei.apiclient;

import com.yewei.apiclient.client.ApiClient;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties("api.client")
@ComponentScan
public class ApiClientConfig {
  private String accessKey;
  private String secretKey;
  @Bean
  public ApiClient apiClient() {
   return new ApiClient(accessKey,secretKey);
  }

}
