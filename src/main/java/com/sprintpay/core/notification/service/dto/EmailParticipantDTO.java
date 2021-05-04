package com.sprintpay.core.notification.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.core.notification.domain.EmailParticipant} entity.
 */
public class EmailParticipantDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String mail;


    private Long emailNotificationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getEmailNotificationId() {
        return emailNotificationId;
    }

    public void setEmailNotificationId(Long emailNotificationId) {
        this.emailNotificationId = emailNotificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailParticipantDTO)) {
            return false;
        }

        return id != null && id.equals(((EmailParticipantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailParticipantDTO{" +
            "id=" + getId() +
            ", mail='" + getMail() + "'" +
            ", emailNotificationId=" + getEmailNotificationId() +
            "}";
    }
}
