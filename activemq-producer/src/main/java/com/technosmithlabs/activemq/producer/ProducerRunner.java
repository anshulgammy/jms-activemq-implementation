package com.technosmithlabs.activemq.producer;

import com.technosmithlabs.activemq.producer.config.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import java.util.Scanner;

@SpringBootApplication
public class ProducerRunner {

    private static ProducerConfig producerConfig = null;

    private static Scanner sc = new Scanner(System.in);

    @Autowired
    public void setProducerConfig(ProducerConfig producerConfig) {
        if (ProducerRunner.producerConfig == null) {
            ProducerRunner.producerConfig = producerConfig;
        }
    }

    private static Integer userDestinationInputSelection = null;

    public static void main(String[] args) {
        SpringApplication.run(ProducerRunner.class, args);
        getUserInputs();
        sendMessage();
    }

    private static void getUserInputs() {
        System.out.println("Please make a selection:");
        System.out.println("1. For Point-To-Point Connection");
        System.out.println("2. For Publisher-Subscriber Connection");
        try {
            userDestinationInputSelection = sc.nextInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void sendMessage() {
        try {
            final MessageProducer messageProducer =
                    producerConfig.getMessageProducer(userDestinationInputSelection);
            sc.nextLine();
            System.out.println("Please enter your message:");
            final String message = sc.nextLine();
            messageProducer.send(producerConfig.getSession().createTextMessage(message));
            System.out.println("Message sent successfully: " + message);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sc.close();
            try {
                producerConfig.closeResources();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
