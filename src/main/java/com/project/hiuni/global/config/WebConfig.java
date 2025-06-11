//package com.project.hiuni.global.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")
//                .allowedOrigins(
//                        "http://localhost:8080",
//                        "http://localhost:3000",
//                        "https://hi-uni-web-fe.vercel.app"
//                )
//                .allowCredentials(true)
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .maxAge(3600);
//    }
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*")
//                .allowCredentials(true)
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .maxAge(3600);
//    }
//}