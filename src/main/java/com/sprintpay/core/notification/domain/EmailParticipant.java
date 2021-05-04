package com.sprintpay.core.notification.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A EmailParticipant.
 */
@Entity
@Table(name = "email_participant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @ManyToOne
    @JsonIgnoreProperties(value = "destinataires", allowSetters = true)
    private EmailNotification emailNotification;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public EmailParticipant mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public EmailNotification getEmailNotification() {
        return emailNotification;
    }

    public EmailParticipant emailNotification(EmailNotification emailNotification) {
        this.emailNotification = emailNotification;
        return this;
    }

    public void setEmailNotification(EmailNotification emailNotification) {
        this.emailNotification = emailNotification;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailParticipant)) {
            return false;
        }
        return id != null && id.equals(((EmailParticipant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailParticipant{" +
            "id=" + getId() +
            ", mail='" + getMail() + "'" +
            "}";
    }
}
