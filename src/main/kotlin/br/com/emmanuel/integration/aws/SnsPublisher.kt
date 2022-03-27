package br.com.emmanuel.integration.aws

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.sns.SnsAsyncClient
import software.amazon.awssdk.services.sns.model.PublishRequest

@Component
class SnsPublisher(private val snsAsyncClient: SnsAsyncClient) {

    fun publish(message: String, topicArn: String): Mono<String?> {
        val request = PublishRequest.builder().run {
            message(message)
            topicArn(topicArn)
            build()
        }

        val result = snsAsyncClient.publish(request).get()

        return Mono.just(result.messageId())
    }
}