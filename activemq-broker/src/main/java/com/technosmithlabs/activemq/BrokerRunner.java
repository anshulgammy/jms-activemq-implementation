package com.technosmithlabs.activemq;

import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrokerRunner {

    private static BrokerService brokerService = null;

    @Autowired
    public void setBrokerService(BrokerService brokerService) {
        if (BrokerRunner.brokerService == null) {
            BrokerRunner.brokerService = brokerService;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(BrokerRunner.class, args);
        startBroker();
    }

    private static void startBroker() {
        try {
            brokerService.start();
            System.out.println(brokerService.getBrokerName() + " started successfully");
            synchronized (brokerService) {
                brokerService.wait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
