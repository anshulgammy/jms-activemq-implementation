package com.technosmithlabs.activemq.consumer.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TSL_MESSAGES")
@Access(AccessType.FIELD)
public class MessageModel {

    @Id
    @Column(name = "MESSAGE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CONSUMER_NAME")
    private String consumerName;
    @Column(name = "MESSAGE_CONTENT")
    private String message;
    @Column(name = "MESSAGE_TIME")
    private LocalDateTime messageTime;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "communicationModeId", column = @Column(name = "COMMUNICATION_MODE_ID"))
    })
    private CommunicationMode communicationMode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public MessageModel() {
    }

    public MessageModel(String consumerName, String message, LocalDateTime messageTime) {
        this.consumerName = consumerName;
        this.message = message;
        this.messageTime = messageTime;
    }
}
