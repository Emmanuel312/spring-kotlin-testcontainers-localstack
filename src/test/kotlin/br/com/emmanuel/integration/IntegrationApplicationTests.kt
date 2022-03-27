package br.com.emmanuel.integration

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriBuilder

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureWebTestClient
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationApplicationTests(
    @Autowired private val webTestClient: WebTestClient
) : LocalStackContainerIntegrationTest() {

    private var topicArn: String? = null

    @BeforeAll
    fun setup() {
        topicArn = createTopic("email")
    }

    @Test
    fun `Should return 2xx in receive email endpoint`() {
        val response = webTestClient.get()
            .uri("/email?message=Test")
            .exchange()
            .expectStatus()
            .is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        println(response)
    }

}
