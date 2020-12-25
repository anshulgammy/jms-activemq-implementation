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
        final Scanner sc = new Scanner(System.in);
        try {
            userDestinationInputSelection = sc.nextInt();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void sendMessage() {
        final Scanner scc = new Scanner(System.in);
        try {
            final MessageProducer messageProducer =
                    producerConfig.getMessageProducer(userDestinationInputSelection);
            System.out.print("Please enter your message: ");
            String message = scc.nextLine();;
            messageProducer.send(producerConfig.getSession().createTextMessage(message));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            scc.close();
            try {
                producerConfig.closeResources();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
