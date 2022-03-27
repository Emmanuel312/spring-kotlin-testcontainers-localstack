package br.com.emmanuel.integration.api

import br.com.emmanuel.integration.aws.SnsPublisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/email")
class EmailController(
    private val snsPublisher: SnsPublisher, @Value("\${sns.email.endpoint}") private val endpoint: String
) {

    @GetMapping
    fun receiveEmail(@RequestParam message: String): Mono<String?> {
        return snsPublisher.publish(message, endpoint)
    }
}