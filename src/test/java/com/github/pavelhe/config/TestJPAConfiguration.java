package com.github.pavelhe.config;

import javax.persistence.*;

import com.github.pavelhe.dao.*;
import com.github.pavelhe.dao.jpa.*;
import org.hibernate.jpa.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.orm.jpa.*;
import org.springframework.orm.jpa.vendor.*;
import org.springframework.transaction.*;

@Configuration
public class TestJPAConfiguration {


    @Autowired
    private TestBasicConfiguration testBasicConfiguration;
    private @Value("#{environment['packageToScan']}")
    String packageToScan;

    private HibernateJpaVendorAdapter vendorAdaptor() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(true);
        return vendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdaptor());
        entityManagerFactoryBean.setDataSource(testBasicConfiguration.dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(packageToScan);

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager testJpaTransactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public GenreDao testGenreDaoJpa() {
        return new GenreDaoJpaImpl();
    }

    @Bean
    public AuthorDao testAuthorDaoJpa() {
        return new AuthorDaoJpaImpl();
    }

    @Bean
    public BookDao testBookDaoJpa() {
        return new BookDaoJpaImpl();
    }

    @Bean
    public CommentDao testCommentDaoJpa() {
        return new CommentDaoJpaImpl();
    }

}
