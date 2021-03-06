package com.wishlist.conf;

import com.wishlist.service.impl.MongoAuthProvider;
import com.wishlist.util.auth.AuthUtils;
import com.wishlist.util.auth.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
@EnableWebMvc
@ComponentScan("com.wishlist")
public class WebMvcConfiguration extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter {

    @Autowired
    AuthUtils authUtils;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Bean
    public MongoAuthProvider mongoAuthProvider(){
        MongoAuthProvider provider = new MongoAuthProvider();
        return provider;
    }

    @Bean
    AuthorizeInterceptor authorizeInterceptor(){
        return new AuthorizeInterceptor(authUtils);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(authorizeInterceptor())
                .addPathPatterns("/api/**");
    }
}
