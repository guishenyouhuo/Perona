package com.guigui.perona.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * mybatis配置
 *
 * @author guigui
 */
@Configuration
@MapperScan(basePackages = {"com.guigui.perona.mapper", "com.guigui.perona.manage.tool.gencode.mapper"})
public class MybatisConfig {

    private static final String MAPPER_XML_PACKAGE = "classpath:mapper/perona/*.xml";

    private static final String MYBATIS_BEAN_PACKAGE = "com.guigui.perona.entity.**";

    private static final String MYBATIS_CONFIG_LOCATION = "classpath:mapper/mybatis-config.xml";

    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid")
    @Bean(name = "dataSource")
    public DataSource dataSourceConfig() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceConfig());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 设置扫描 mybatis-config.xml
        sqlSessionFactoryBean.setConfigLocation(resolver.getResource(MYBATIS_CONFIG_LOCATION));
        // 设置扫描mapper.xml
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_XML_PACKAGE));
        // 设置扫描实体类
        sqlSessionFactoryBean.setTypeAliasesPackage(MYBATIS_BEAN_PACKAGE);

        return sqlSessionFactoryBean.getObject();
    }


    @Primary
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate popSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Primary
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSourceConfig());
    }

}
