package com.github.pavelhe.config.health;

import java.io.*;
import java.nio.file.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.actuate.health.*;
import org.springframework.stereotype.*;

@Component
public class DiskSpaceHealthIndicator extends AbstractHealthIndicator {

    private final FileStore fileStore;
    private final long thresholdBytes;

    @Autowired
    public DiskSpaceHealthIndicator(@Value("${health.filestore.path:${user.dir}}") String path,
                                    @Value("${health.filestore.threshold.bytes:10485760}") long thresholdBytes) throws IOException {
        fileStore = Files.getFileStore(Paths.get(path));
        this.thresholdBytes = thresholdBytes;
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        long diskFreeInBytes = fileStore.getUnallocatedSpace();
        if (diskFreeInBytes >= thresholdBytes) {
            builder.up();
        } else {
            builder.down();
        }
    }
}
