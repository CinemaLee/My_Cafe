package com.study.mycafe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
//
//    @Value("${resources.location}")
//    private String resourcesLocation;
//
//    @Value("${resources.uri_path:}")
//    private String resourcesUriPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("file:///" + "C:/Users/owner/imagedata/");
//                  .addResourceLocations("file:///" + "http://drive.google.com/uc?export=view&id=1Nzt9oYCtCtQCNSokG8bmutzb69PxjomU");




    }
}
