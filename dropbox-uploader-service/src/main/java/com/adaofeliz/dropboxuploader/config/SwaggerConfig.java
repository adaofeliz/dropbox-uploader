package com.adaofeliz.dropboxuploader.config;


import com.mangofactory.swagger.plugin.EnableSwagger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created on 20/10/14.
 */

@Configuration
@EnableSwagger
public class SwaggerConfig extends WebMvcConfigurerAdapter {
}