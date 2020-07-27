package com.xsy.job.configuration;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xueshaoyi
 * @Date 2019/10/21 下午3:39
 **/
@Configuration
public class DataSourceConfig {
    @Bean("esJobDataSource")
    @ConditionalOnMissingBean(name = "esJobDataSource")
    @ConfigurationProperties("spring.datasource.druid.es-job")
    public DataSource mostWantedDataSource() {
        return DruidDataSourceBuilder.create().build();
    }
}
