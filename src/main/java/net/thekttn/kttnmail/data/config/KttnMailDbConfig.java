package net.thekttn.kttnmail.data.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;


@Configuration
@EnableJpaRepositories(
  basePackages = "net.thekttn.kttnmail.data.repos", 
  entityManagerFactoryRef = "kttnMailDbEntityManagerFactory", 
  transactionManagerRef = "kttnMailDbTransactionManager")
public class KttnMailDbConfig
{
  @Value("${datasources.kttn-mail.driver-class-name}")
  private String driverClassName;

  @Value("${datasources.kttn-mail.url}")
  private String url;

  @Value("${datasources.kttn-mail.username}")
  private String username;

  @Value("${datasources.kttn-mail.password}")
  private String password;


  @Bean
  public DataSource kttnMailDbDataSource()
  {
    DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(url);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
    return dataSourceBuilder.build();
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean kttnMailDbEntityManagerFactory(@Qualifier("kttnMailDbDataSource") DataSource dataSource)
  {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(dataSource);
    factoryBean.setPackagesToScan("net.thekttn.kttnmail.data.entities");
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    return factoryBean;
  }

  @Bean
  public PlatformTransactionManager kttnMailDbTransactionManager(@Qualifier("kttnMailDbEntityManagerFactory") EntityManagerFactory entityManagerFactory)
  {
    return new JpaTransactionManager(entityManagerFactory);
  }
}