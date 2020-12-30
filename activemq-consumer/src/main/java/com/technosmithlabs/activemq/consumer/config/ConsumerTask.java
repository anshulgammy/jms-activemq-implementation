package com.technosmithlabs.activemq.consumer.config;

import com.technosmithlabs.activemq.consumer.model.CommunicationMode;
import com.technosmithlabs.activemq.consumer.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.TextMessage;
import java.time.LocalDateTime;

@Component
public class ConsumerTask extends Thread {

    @Autowired
    private ConsumerConfig consumerConfig;

    @Autowired
    private EntityManagerConfig entityManagerConfig;

    @Autowired
    private MessageModel messageModel;

    private int userDestinationInputSelection;

    public void setUserDestinationInputSelection(int userDestinationInputSelection) {
        this.userDestinationInputSelection = userDestinationInputSelection;
    }

    @Override
    public void run() {
        try {
            final MessageConsumer messageConsumer = consumerConfig.getMessageConsumer(userDestinationInputSelection);
            // Wait for a message
            while (messageConsumer != null) {
                final Message message = messageConsumer.receive(1000);
                if (message instanceof TextMessage) {
                    final TextMessage textMessage = (TextMessage) message;
                    final String messageText = textMessage.getText();
                    final String uniqueThreadName = String.valueOf(Thread.currentThread().getId());
                    System.out.println("Message received by Consumer thread " + uniqueThreadName + " : " + messageText);
                    persistMessageToDb(uniqueThreadName, messageText, LocalDateTime.now(), userDestinationInputSelection);
                    if ("exit".equals(messageText)) {
                        System.out.println("Shutting down the consumer");
                        break;
                    }
                }/* else {
                    System.out.println("Received by " + this.getName() + ": " + message);
                }*/
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

    private synchronized void persistMessageToDb(String consumerName, String message, LocalDateTime messageTime, int userDestinationInputSelection) {
        final CommunicationMode communicationMode = new CommunicationMode(userDestinationInputSelection, messageModel.getCommunicationModes().get(userDestinationInputSelection));
        final MessageModel messageModel = new MessageModel(consumerName, message, messageTime, communicationMode);
        final Boolean status = entityManagerConfig.persist(messageModel);
        if (status) {
            System.out.println("Message stored in DB successfully by: " + consumerName);
        }
    }

}
