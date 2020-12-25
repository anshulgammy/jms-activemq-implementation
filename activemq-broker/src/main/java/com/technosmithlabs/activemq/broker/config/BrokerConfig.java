package com.technosmithlabs.activemq.broker.config;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class BrokerConfig {

    @Value("${activemq.broker.name}")
    private String brokerName;

    @Value("${activemq.transport.connector.uri}")
    private String transportConnectorURI;

    @Bean
    public BrokerService brokerService() {
        BrokerService brokerService = null;
        try {
            brokerService = new BrokerService();
            brokerService.setBrokerName(brokerName);
            final TransportConnector transportConnector = new TransportConnector();
            transportConnector.setUri(new URI(transportConnectorURI));
            brokerService.addConnector(transportConnector);
            System.out.println("Broker created successfully!");
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return brokerService;
    }

}
