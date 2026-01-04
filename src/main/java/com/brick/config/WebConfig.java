package com.brick.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    private static final String UPLOAD_DIR = "file:uploads/"; // 프로젝트 루트/uploads 폴더

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            //CORS 설정
            //@Override
            //public void addCorsMappings(CorsRegistry registry) {
                // API
                //registry.addMapping("/api/**")
                        //.allowedOrigins("http://localhost:5173", "http://localhost:8080")
                        //.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        //.allowedHeaders("*")
                        //.allowCredentials(true);
                // 이미지 파일
                //registry.addMapping("/uploads/**")
                        //.allowedOrigins("http://localhost:5173", "http://localhost:8080")
                        //.allowedMethods("GET", "OPTIONS")
                        //.allowedHeaders("*")
                        //.allowCredentials(true);
            //}

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations(UPLOAD_DIR);
            }
        };
    }
}
