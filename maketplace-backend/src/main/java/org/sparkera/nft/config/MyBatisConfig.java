package org.sparkera.nft.config;


import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.sparkera.nft.utils.BSCUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class MyBatisConfig {
    /**
     * 第一个数据库 SqlSessionFactory && SqlSessionTemplate 创建
     */
    private static Boolean dblog;
    public static Boolean getDbLog() { return dblog;}
    @Value("${dblog}")
    public void setDbLog(Boolean dblog) {
        MyBatisConfig.dblog = dblog;
    }

    @Configuration
    @MapperScan(basePackages = {"org.sparkera.nft.dao.mapper"},
            sqlSessionTemplateRef = "sqlSessionTemplateOne")
    public static  class DBOne{
        @Resource
        DataSource dbOneDataSource;

        /** 全局自定义配置 */
        @Bean(name = "globalConfig")
        @ConfigurationProperties(prefix = "mybatis-plus.global-config")
        public GlobalConfig globalConfig(){
            return new GlobalConfig();
        }

        @Autowired
        @Qualifier("globalConfig")
        private GlobalConfig globalConfig;

        @Bean
        public SqlSessionFactory sqlSessionFactoryOne() throws Exception {
            log.info("sqlSessionFactoryOne 创建成功。");
            MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
            factoryBean.setDataSource(dbOneDataSource);

            MybatisConfiguration configuration = new MybatisConfiguration();
            if (getDbLog()) {
                configuration.addInterceptor(new PerformanceInterceptor());
            }
            configuration.addInterceptor(new PaginationInterceptor());
            factoryBean.setConfiguration(configuration);

            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));
            return factoryBean.getObject();
        }


        @Bean
        public SqlSessionTemplate sqlSessionTemplateOne() throws Exception {
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryOne()); // 使用上面配置的Factory
            return template;

        }

    }


    /**
     * 第二个数据库 SqlSessionFactory && SqlSessionTemplate 创建
     */

    @Configuration
    @MapperScan(basePackages = {"org.sparkera.nft.dao.mapper2"},
            sqlSessionTemplateRef ="sqlSessionTemplateTwo" )
    public static class DBTwo{

        @Resource
        DataSource dbTwoDataSource;

        @Bean
        public SqlSessionFactory sqlSessionFactoryTwo() throws Exception {

            log.info("sqlSessionFactoryTwo 创建成功。");
            MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
            factoryBean.setDataSource(dbTwoDataSource);

            MybatisConfiguration configuration = new MybatisConfiguration();
            if (getDbLog()) {
                configuration.addInterceptor(new PerformanceInterceptor());
            }
            configuration.addInterceptor(new PaginationInterceptor());
            factoryBean.setConfiguration(configuration);

            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));

            return factoryBean.getObject();

        }


        @Bean
        public SqlSessionTemplate sqlSessionTemplateTwo() throws Exception {
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryTwo()); // 使用上面配置的Factory
            return template;
        }

    }

    /**
     * 第三个数据库 SqlSessionFactory && SqlSessionTemplate 创建
     */

    @Configuration
    @MapperScan(basePackages = {"org.sparkera.nft.dao.mapper3"},
            sqlSessionTemplateRef ="sqlSessionTemplateThree" )
    public static class DBThree{

        @Resource
        DataSource dbThreeDataSource;

        @Bean
        public SqlSessionFactory sqlSessionFactoryThree() throws Exception {

            log.info("sqlSessionFactoryTwo 创建成功。");
            MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
            factoryBean.setDataSource(dbThreeDataSource);

            MybatisConfiguration configuration = new MybatisConfiguration();
            if (getDbLog()) {
                configuration.addInterceptor(new PerformanceInterceptor());
            }
            configuration.addInterceptor(new PaginationInterceptor());
            factoryBean.setConfiguration(configuration);

            factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*Mapper.xml"));

            return factoryBean.getObject();

        }

        @Bean
        public SqlSessionTemplate sqlSessionTemplateThree() throws Exception {
            SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryThree()); // 使用上面配置的Factory
            return template;
        }

    }

        /**
     * Druid的监控
     * @return
     */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        Map<String,String> initParams=new HashMap<>();
        initParams.put("loginUsername","root");
        initParams.put("loginPassword","r123456");
        initParams.put("allow","");//默认就是允许所有访问
        initParams.put("deny","");
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     *  web监控filter
     * @return
     */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean=new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParams = new HashMap<>();
        initParams.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }



}
