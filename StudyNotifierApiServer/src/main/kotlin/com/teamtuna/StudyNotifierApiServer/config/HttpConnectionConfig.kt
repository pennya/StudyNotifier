package com.teamtuna.StudyNotifierApiServer.config

import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class HttpConnectionConfig {

    @Bean
    fun getCustomRestTemplate(): RestTemplate {
        val httpRequestFactory = HttpComponentsClientHttpRequestFactory().apply {
            setConnectTimeout(2000)
            setReadTimeout(3000)
            httpClient = HttpClientBuilder.create()
                    .setMaxConnTotal(200)
                    .setMaxConnPerRoute(20)
                    .build()
        }

        return RestTemplate(httpRequestFactory)
    }
}