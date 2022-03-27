package br.com.emmanuel.integration

import java.time.Duration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest

@Testcontainers
open class LocalStackContainerIntegrationTest {
    @Autowired
    private lateinit var snsAsyncClient: SnsAsyncClient

    companion object {
        private val localstack = LocalStackContainer(DockerImageName.parse("localstack/localstack")).apply {
            withServices(LocalStackContainer.Service.SNS)
            withStartupTimeout(Duration.ofMinutes(2))
            start()
        }

        private fun getSnsEndpoint(): String {
            val localStackHost = localstack.containerIpAddress
            val localStackSNSPort = localstack.getMappedPort(4566)
            return "http://$localStackHost:$localStackSNSPort"
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("aws.endpoint") { getSnsEndpoint() }
        }
    }

    fun createTopic(topicName: String): String? {
        val request = CreateTopicRequest.builder()
            .name(topicName)
            .build()

        return snsAsyncClient.createTopic(request).get().topicArn()
    }
}