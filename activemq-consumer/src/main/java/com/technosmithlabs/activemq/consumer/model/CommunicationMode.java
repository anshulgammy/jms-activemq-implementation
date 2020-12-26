package com.technosmithlabs.activemq.consumer.model;

import javax.persistence.Embeddable;

@Embeddable
public class CommunicationMode {
    private Long communicationModeId;
    private String communicationModeName;

    public Long getCommunicationType() {
        return communicationModeId;
    }

    public void setCommunicationType(Long communicationType) {
        this.communicationModeId = communicationType;
    }

    public CommunicationMode() {
    }
}
