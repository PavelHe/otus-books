package com.github.pavelhe.service.batch;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importJob;

    @Override
    public void start() {
        try {
            jobLauncher.run(importJob, new JobParameters());
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            throw new RuntimeException(e);
        }
    }
}
