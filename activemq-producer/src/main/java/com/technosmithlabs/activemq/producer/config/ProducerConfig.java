package com.technosmithlabs.activemq.producer.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.jms.*;

@Configuration
public class ProducerConfig {

    @Value("${activemq.transport.connector.uri}")
    private String brokerUrl;

    @Value("${activemq.session.queue.uri}")
    private String queueAddress;

    @Value("${activemq.session.topic.uri}")
    private String topicAddress;

    private ConnectionFactory connectionFactory = null;

    private Connection connection = null;

    private Session session = null;

    public ConnectionFactory getConnectionFactory() {
        if (this.connectionFactory == null) {
            // Create a ConnectionFactory
            this.connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        }
        return this.connectionFactory;
    }

    public Connection getConnection() throws JMSException {
        if (this.connection == null) {
            // Create a Connection
            this.connection = getConnectionFactory().createConnection();
            this.connection.start();
        }
        return this.connection;
    }

    public Session getSession() throws JMSException {
        if (this.session == null) {
            // Create a Session
            this.session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        }
        return this.session;
    }

    public MessageProducer getMessageProducer(int userInput) throws JMSException {
        Destination destination = null;
        MessageProducer messageProducer = null;
        // Create the destination (Topic or Queue)
        switch (userInput) {
            case 1: {
                destination = getSession().createQueue(queueAddress);
                break;
            }
            case 2: {
                destination = getSession().createTopic(topicAddress);
                break;
            }
            default: {
                System.out.println("Incorrect destination type selected");
                System.exit(0);
            }
        }
        if (destination != null) {
            // Create a MessageProducer from the Session to the Topic or Queue
            messageProducer = getSession().createProducer(destination);
            messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        }
        return messageProducer;
    }

    public void closeResources() throws JMSException {
        if (getSession() != null) {
            getSession().close();
        }
        if (getConnection() != null) {
            getConnection().close();
        }
    }
}
