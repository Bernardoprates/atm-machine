package com.zinkworks.atmmachine;

import com.mongodb.client.MongoClients;
import com.zinkworks.atmmachine.config.DatabaseTestConfig;
import com.zinkworks.atmmachine.resource.BalanceResource;
import com.zinkworks.atmmachine.resource.WithdrawalResource;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.ImmutableMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;

@EnabledOnOs(OS.SOLARIS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {DatabaseTestConfig.class})
class AtmMachineApplicationTests {

	@Autowired
	WithdrawalResource withdrawalResource;

	@Autowired
	BalanceResource balanceResource;

	@Autowired
	TestRestTemplate restTemplate;

	private static final String CONNECTION_STRING = "mongodb://%s:%d";

	private MongodExecutable mongodExecutable;
	private MongoTemplate mongoTemplate;

	@AfterEach
	void clean() {
		mongodExecutable.stop();
	}

	@BeforeEach
	void setup() throws Exception {
		String ip = "localhost";
		int port = 27017;

		ImmutableMongodConfig mongodConfig = MongodConfig
				.builder()
				.version(Version.Main.PRODUCTION)
				.net(new Net(ip, port, Network.localhostIsIPv6()))
				.build();

		MongodStarter starter = MongodStarter.getDefaultInstance();
		mongodExecutable = starter.prepare(mongodConfig);
		mongodExecutable.start();
		mongoTemplate = new MongoTemplate(MongoClients.create(String.format(CONNECTION_STRING, ip, port)), "test");
		//mongoTemplate.insert()
	}

}
