package com.huaxin.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = "com.huaxin.*.mapper",sqlSessionFactoryRef = "sqlSessionFactory")
public class MapperScanConfig {
}
