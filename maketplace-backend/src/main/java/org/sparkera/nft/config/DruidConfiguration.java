package org.sparkera.nft.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


@Configuration
public class DruidConfiguration {
//    private static final Logger LOGGER = LoggerFactory.getLogger(DruidConfiguration.class);
//    @ConfigurationProperties(prefix = "spring.datasource.db1")
//    @Bean("dataSource")
//    public DataSource druid(){
//        return new DruidDataSource();
//    }
//
//    /**
//     * Druid的监控
//     * @return
//     */
//    @Bean
//    public ServletRegistrationBean statViewServlet(){
//        LOGGER.info("init Druid Servlet Configuration ");
//        ServletRegistrationBean bean=new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
//        Map<String,String> initParams=new HashMap<>();
//        initParams.put("loginUsername","root");
//        initParams.put("loginPassword","r123456");
//        initParams.put("allow","");//默认就是允许所有访问
//        initParams.put("deny","");
//        bean.setInitParameters(initParams);
//        return bean;
//    }
//
//    /**
//     *  web监控filter
//     * @return
//     */
//    @Bean
//    public FilterRegistrationBean webStatFilter(){
//        FilterRegistrationBean bean=new FilterRegistrationBean();
//        bean.setFilter(new WebStatFilter());
//        Map<String,String> initParams = new HashMap<>();
//        initParams.put("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
//        bean.setInitParameters(initParams);
//        bean.setUrlPatterns(Arrays.asList("/*"));
//        return  bean;
//    }
//
//
//

}
