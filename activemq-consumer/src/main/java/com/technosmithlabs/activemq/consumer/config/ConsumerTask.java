package com.technosmithlabs.activemq.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;

@Component
public class ConsumerTask extends Thread {

    @Autowired
    private ConsumerConfig consumerConfig;

    private int userDestinationInputSelection;

    public void setUserDestinationInputSelection(int userDestinationInputSelection) {
        this.userDestinationInputSelection = userDestinationInputSelection;
    }

    @Override
    public void run() {
        try {
            final MessageConsumer messageConsumer = consumerConfig.getMessageConsumer(userDestinationInputSelection);
            // Wait for a message
            while (true) {
                final Message message = messageConsumer.receive(60000);
                if (message instanceof TextMessage) {
                    final TextMessage textMessage = (TextMessage) message;
                    final String messageText = textMessage.getText();
                    System.out.println("Received: " + messageText);
                } else {
                    System.out.println("Received: " + message);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                consumerConfig.closeResources();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

}
