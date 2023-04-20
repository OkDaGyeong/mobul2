package com.codehows.mobul.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    // 업로드한 파일 읽어올 경로 설정
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/**")               // /images/** 뭔지 확인
//                .addResourceLocations(uploadPath)
//                .addResourceLocations("classpath:/static/"); // 추가적인 정적 리소스 경로
                .addResourceLocations("file:///C:/mobul/board/");

    }
}
