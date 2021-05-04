package com.sprintpay.core.notification.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.core.notification.domain.EmailNotification} entity.
 */
public class EmailNotificationDTO implements Serializable {
    
    private Long id;

    private String message;

    private String sujet;


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

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
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
        if (!(o instanceof EmailNotificationDTO)) {
            return false;
        }

        return id != null && id.equals(((EmailNotificationDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailNotificationDTO{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", sujet='" + getSujet() + "'" +
            ", emetteurId=" + getEmetteurId() +
            "}";
    }
}
