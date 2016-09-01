package ua.com.smiddle.task.configs;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Added to ${PACKAGE_NAME} by A.Osadchuk on 08.04.2016 at 16:06.
 * Project: Manager
 */
@Configuration
@Profile("production")
@EnableWebMvc
@EnableScheduling
@EnableTransactionManagement
@ComponentScan({"ua.com.smiddle.task.core"})
@PropertySource("classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter {
    @Autowired
    Environment environment;


    //======================================= Beans ==============================================
    @Bean
    @Description("Set transactions management strategy")
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    @Description("Need for EntityManager creation")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter adapter) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("ua.com.smiddle.task.core");
        return emf;
    }

    @Bean
    @Description("Setting JPA vendor adapter")
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(Boolean.valueOf(environment.getProperty("database.showSQL")));
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform(environment.getProperty("database.dialect"));
        return adapter;
    }

    @Bean
    @Description("DB connection pool")
    public DataSource dataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(environment.getProperty("database.driver"));
        ds.setUrl(environment.getProperty("database.url"));
        ds.setUsername(environment.getProperty("database.username"));
        ds.setPassword(environment.getProperty("database.password"));
        ds.addConnectionProperty("useUnicode", environment.getProperty("database.useUnicode"));
        ds.addConnectionProperty("characterEncoding", environment.getProperty("database.characterEncoding"));
        ds.setInitialSize(2);
        ds.setMinIdle(2);
        ds.setMaxIdle(2);
        ds.setMaxTotal(2);
        ds.setLifo(true);
        ds.setRemoveAbandonedOnBorrow(true);
        ds.setRemoveAbandonedOnMaintenance(true);
        ds.setRemoveAbandonedTimeout(60 * 5);
        return ds;
    }

    @Bean(name = "multipartResolver")
    @Description("Обертка для Apache multipart request")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(10000000);
        resolver.setDefaultEncoding("utf8");
        return resolver;
    }

    @Bean
    @Description("Вспомагательный класс который указывает фреймворку откуда брать страницы для отображения")
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/recourses/**")
                .addResourceLocations("/recourses/");
    }

}
