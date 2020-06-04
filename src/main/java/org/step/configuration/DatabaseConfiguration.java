package org.step.configuration;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Optional;
import java.util.Properties;

@Configuration
@ComponentScan
@PropertySources({
        @PropertySource("classpath:/db.properties")
})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.step.repository")
public class DatabaseConfiguration {

    /*
    Стартуер раньше Configuration и создается раньше
     */
    private Environment environment;

    @Bean
//    @Profile(value = {"dev", "test"})
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(
                Optional.ofNullable(environment.getProperty("db.driver"))
                        .orElse("org.postgresql.Driver")
        );
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.user"));
        dataSource.setPassword(environment.getProperty("db.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean embFactory = new LocalContainerEntityManagerFactoryBean();

        embFactory.setDataSource(dataSource);
        embFactory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        embFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        embFactory.setJpaProperties(jpaProperties());
        embFactory.setPackagesToScan("org.step");

        return embFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactory);

        return transactionManager;
    }

    /*
    @Autowired - проводим зависимости в бин (Set method, поле, конструктор)
    @Qualifier - разрешаем спорные инжекты разных бинов с одним и тем же именем
     */

    private Properties jpaProperties() {

        Properties jpaProperties = new Properties();

        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        jpaProperties.setProperty("hibernate.show_sql", "true");

        return jpaProperties;
    }

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
