package com.github.pavelhe.config.utils;


import java.io.*;
import java.net.*;

import de.flapdoodle.embed.mongo.*;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.*;
import de.flapdoodle.embed.process.runtime.*;

public class MongoUtils {

    public static void startEmbeddedMongodb(String host, int port) {
        if (isPortAvailable(host, port)) {
            IMongoConfig mongoConfig;
            try {
                mongoConfig = new MongodConfigBuilder()
                        .version(Version.V3_2_20)
                        .net(new Net(host, port, Network.localhostIsIPv6())).build();
                MongodStarter starter = MongodStarter.getDefaultInstance();
                MongodExecutable mongodExecutable = starter.prepare((IMongodConfig) mongoConfig);
                mongodExecutable.start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static boolean isPortAvailable(String host, int port) {
        try (Socket ignored = new Socket(host, port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

}
