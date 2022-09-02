package com.zinkworks.atmmachine.config;

import com.mongodb.client.MongoClients;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.net.UnknownHostException;

//@TestConfiguration
public class DatabaseTestConfig {

    private static final String CONNECTION_STRING = "mongodb://%s:%d";

    private static final String IP = "localhost";

    private static final int PORT = 27018;


    ImmutableMongodConfig mongodConfig() throws UnknownHostException {
        return MongodConfig.builder().version(Version.Main.PRODUCTION)
                .net(new Net(IP, PORT, Network.localhostIsIPv6()))
                .build();
    }

    @Bean
    MongoTemplate template() throws IOException {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodExecutable mongodExecutable = starter.prepare(mongodConfig());
        mongodExecutable.start();
        return new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, IP, PORT)), "test");
    }


}
