package com.github.pavelhe.config.utils;

import javax.annotation.*;

import com.github.pavelhe.service.batch.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.explore.*;
import org.springframework.batch.core.explore.support.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.launch.support.*;
import org.springframework.batch.core.repository.*;
import org.springframework.batch.core.repository.support.*;
import org.springframework.batch.support.transaction.*;
import org.springframework.context.annotation.*;
import org.springframework.transaction.*;

@Configuration
public class TestBatchConfiguration implements BatchConfigurer {

    private PlatformTransactionManager transactionManager;
    private JobRepository jobRepository;
    private JobLauncher jobLauncher;
    private JobExplorer jobExplorer;

    @Override
    public JobRepository getJobRepository() {
        return jobRepository;
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    public JobLauncher getJobLauncher() {
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() {
        return jobExplorer;
    }

    @PostConstruct
    void initialize() throws Exception {

        if (this.transactionManager == null) {
            this.transactionManager = new ResourcelessTransactionManager();
        }

        MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.transactionManager);
        jobRepositoryFactory.afterPropertiesSet();
        this.jobRepository = jobRepositoryFactory.getObject();

        MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
        jobExplorerFactory.afterPropertiesSet();
        this.jobExplorer = jobExplorerFactory.getObject();
        this.jobLauncher = createJobLauncher();
    }

    private JobLauncher createJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public ImportService testImportService() {
        return new ImportServiceImpl();
    }

}
