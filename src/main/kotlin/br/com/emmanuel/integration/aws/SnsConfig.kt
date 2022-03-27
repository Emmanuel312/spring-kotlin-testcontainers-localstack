package br.com.emmanuel.integration.aws

import java.net.URI
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sns.SnsAsyncClient

@Configuration
class SnsConfig(@Value("\${aws.endpoint}") private val endpoint: String) {

    @Bean
    fun snsClient(): SnsAsyncClient = SnsAsyncClient.builder()
        .region(Region.US_EAST_1)
        .credentialsProvider(DefaultCredentialsProvider.create())
        .endpointOverride(URI.create(endpoint))
        .build()

}