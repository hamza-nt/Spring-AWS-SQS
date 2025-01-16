package com.vde.notifications_app;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

public class AbstractIntegrationTest {
    static final LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:1.4.0"))
            .withServices(LocalStackContainer.Service.SQS);

    static {
        localStack.start();
    }
}