package com.sprintpay.core.notification.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.core.notification.domain.SmsNotification} entity.
 */
public class SmsNotificationDTO implements Serializable {
    
    private Long id;

    private String message;


    private Long emetteurId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEmetteurId() {
        return emetteurId;
    }

    public void setEmetteurId(Long microServiceParticipantId) {
        this.emetteurId = microServiceParticipantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsNotificationDTO)) {
            return false;
        }

        return id != null && id.equals(((SmsNotificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmsNotificationDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", emetteurId=" + getEmetteurId() +
            "}";
    }
}
