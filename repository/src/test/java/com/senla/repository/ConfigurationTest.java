package com.senla.repository;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.senla"})
@EntityScan(basePackages = "com.senla.entity")
@SpringBootConfiguration
public class ConfigurationTest {
}
