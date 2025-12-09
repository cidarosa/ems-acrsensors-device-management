package com.github.cidarosa.acrsensor.device.management.api.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {

    private final RestClient.Builder builder;
    //com Builder, chama bind do Builder que o Spring já carregou, com config que o Spring adiciona
    public RestClient temperatureMonitoringRestClient() {

        return builder.baseUrl("http://localhost:8082")
                //alterando config do client restclient padrão (simpleClient) para forçar tempo espera abaixo 10seg
                .requestFactory(generateClientHttpRequestFactory())
                //tratamento exceptions, qualquer tipo de erro
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new SensorMonitoringClientBadGatewayException();
                })

                .build();
    }

    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(5)); //espera da resposta
        factory.setConnectTimeout(Duration.ofSeconds(3)); //espera para conseguir fazer conexão

        return factory;
    }

}
