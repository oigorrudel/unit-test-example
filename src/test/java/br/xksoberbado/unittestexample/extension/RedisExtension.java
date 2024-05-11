package br.xksoberbado.unittestexample.extension;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.atomic.AtomicBoolean;

public class RedisExtension implements BeforeAllCallback {

    private final AtomicBoolean containerStarted = new AtomicBoolean(false);
    private final GenericContainer container = new GenericContainer(DockerImageName.parse("redis"))
        .withExposedPorts(6379)
        .withCommand("redis-server");

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        if (!containerStarted.get()) {
            container.start();

            System.setProperty("spring.data.redis.host", container.getHost());
            System.setProperty("spring.data.redis.port", container.getFirstMappedPort().toString());
            System.setProperty("spring.data.redis.timeout", "30000");

            containerStarted.set(true);
        }
    }
}
