package com.ubs.m295.config;

import com.ubs.m295.dao.GroupsDao;
import com.ubs.m295.dao.TeamsDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class SpringConfiguration {

    @Bean
    TeamsDao TeamsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new TeamsDao(namedParameterJdbcTemplate);
    }

    @Bean
    GroupsDao GroupsDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new GroupsDao(namedParameterJdbcTemplate);
    }

}
