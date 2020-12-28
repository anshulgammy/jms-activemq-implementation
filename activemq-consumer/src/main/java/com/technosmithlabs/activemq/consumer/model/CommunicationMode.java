package com.technosmithlabs.activemq.consumer.model;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class CommunicationMode {
    private int communicationModeId;
    private String communicationModeName;

    public int getCommunicationType() {
        return this.communicationModeId;
    }

    public void setCommunicationType(int communicationType) {
        this.communicationModeId = communicationType;
    }

    public CommunicationMode() {
    }

    public CommunicationMode(int communicationModeId, String communicationModeName) {
        this.communicationModeId = communicationModeId;
        this.communicationModeName = communicationModeName;
    }
}
