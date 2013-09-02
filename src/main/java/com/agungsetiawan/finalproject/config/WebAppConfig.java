package com.agungsetiawan.finalproject.config;

import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.interceptor.CommonDataInterceptor;
import com.agungsetiawan.finalproject.service.CartService;
import java.io.IOException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

/**
 *
 * @author awanlabs
 */

@Configuration
@EnableWebMvc
@ComponentScan("com.agungsetiawan.finalproject")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@ImportResource("classpath:spring-security.xml")
public class WebAppConfig extends WebMvcConfigurerAdapter{
    @Resource
    Environment env;
    
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getRequiredProperty("db.url"));
        dataSource.setUsername(env.getRequiredProperty("db.username"));
        dataSource.setPassword(env.getRequiredProperty("db.password"));
        return dataSource;
    }
    
    private Properties hibernateProperties(){
        Properties p=new Properties();
        p.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        p.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        p.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return p;
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean factoryBean=new LocalSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(env.getRequiredProperty("entitymanager.packages.to.scan"));
        factoryBean.setHibernateProperties(hibernateProperties());
        return factoryBean;
        
    }
    
    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager hibernateTransactionManager=new HibernateTransactionManager();
        hibernateTransactionManager.setSessionFactory(sessionFactory().getObject());
        return  hibernateTransactionManager;
    }
    
    @Bean
    public InternalResourceViewResolver viewResolver(){
        InternalResourceViewResolver resolver=new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        return resolver;
    }
    
    @Bean
    @Scope(value = "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CartService cart(){
        return new CartService();
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/img/**").addResourceLocations("/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addWebRequestInterceptor(commonDataInterceptor());
    }
    
    @Bean
    public WebRequestInterceptor commonDataInterceptor(){
        return new CommonDataInterceptor();
    }
    
    @Bean(name = "exceptionResolver")
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put(NotFoundException.class.getName(), "404s");
        exceptionMappings.put(ConstraintViolationException.class.getName(), "409");
//        exceptionMappings.put("java.lang.Exception", "404");
//        exceptionMappings.put("java.lang.RuntimeException", "404");

        exceptionResolver.setExceptionMappings(exceptionMappings);
//        exceptionResolver.setDefaultErrorView("404s");
        exceptionResolver.setOrder(0);
//        Properties statusCodes = new Properties();
//
//        statusCodes.put("404", "404");
//        statusCodes.put("error/error", "500");
//
//        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
    }
  
    
//    @Bean
//    public TilesConfigurer tilesConfigurer(){
//        TilesConfigurer tc=new TilesConfigurer();
//        tc.setDefinitions(new String[]{"/WEB-INF/tiles.xml"});
//        return new TilesConfigurer();
//    }
//    
//    public TilesViewResolver tilesViewResolver(){
//        TilesViewResolver tilesViewResolver=new TilesViewResolver();
//        tilesViewResolver.setOrder(2);
//        return tilesViewResolver;
//    }
}
