package com.codehows.mobul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

//파일을 읽어오기 위한 ResourceHandler
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 업로드한 파일 읽어올 경로 설정
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/files/**")
                .addResourceLocations(uploadPath);
    }
}
