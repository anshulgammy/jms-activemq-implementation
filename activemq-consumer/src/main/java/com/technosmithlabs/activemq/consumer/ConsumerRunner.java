package com.technosmithlabs.activemq.consumer;

import com.technosmithlabs.activemq.consumer.config.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import java.util.Scanner;

@SpringBootApplication
public class ConsumerRunner {

    private static ConsumerConfig consumerConfig = null;

    private static Integer userDestinationInputSelection = null;

    private static Scanner sc = new Scanner(System.in);

    @Autowired
    public void setConsumerConfig(ConsumerConfig consumerConfig) {
        if (ConsumerRunner.consumerConfig == null) {
            ConsumerRunner.consumerConfig = consumerConfig;
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerRunner.class, args);
        try {
            System.out.println("Please make a selection:");
            System.out.println("1. For Point-To-Point Connection");
            System.out.println("2. For Publisher-Subscriber Connection");
            userDestinationInputSelection = sc.nextInt();
            final MessageConsumer messageConsumer = consumerConfig.getMessageConsumer(userDestinationInputSelection);
            // Wait for a message
            final Message message = messageConsumer.receive(10000);
            if (message instanceof TextMessage) {
                final TextMessage textMessage = (TextMessage) message;
                final String messageText = textMessage.getText();
                System.out.println("Received: " + messageText);
            } else {
                System.out.println("Received: " + message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            sc.close();
            try {
                consumerConfig.closeResources();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
