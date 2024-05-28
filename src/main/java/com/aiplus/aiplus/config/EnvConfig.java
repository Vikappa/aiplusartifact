package com.aiplus.aiplus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "file:./env.properties")
public class EnvConfig {



}
