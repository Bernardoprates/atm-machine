package com.zinkworks.atmmachine.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.zinkworks.atmmachine.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = {AccountRepository.class})
@Configuration
public class DatabaseConfiguration extends AbstractMongoClientConfiguration {

    private static final String DB_NAME = "AtmDB";

    @Value("${atmdb.mongodb.uri:mongodb://localhost:27017}")
    public String mongoUri;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        // customization hook
        builder.applyConnectionString(new ConnectionString(mongoUri));
    }

    @Override
    protected String getDatabaseName() {
        return DB_NAME;
    }
}
