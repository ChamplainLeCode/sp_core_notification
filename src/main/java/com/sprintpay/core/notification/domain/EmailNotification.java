package com.sprintpay.core.notification.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A EmailNotification.
 */
@Entity
@Table(name = "email_notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmailNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "sujet")
    private String sujet;

    @OneToOne
    @JoinColumn(unique = true)
    private MicroServiceParticipant emetteur;

    @OneToMany(mappedBy = "emailNotification")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<EmailParticipant> destinataires = new HashSet<>();

    @OneToMany(mappedBy = "emailNotification")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TemplateParamValue> paramValues = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public EmailNotification message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSujet() {
        return sujet;
    }

    public EmailNotification sujet(String sujet) {
        this.sujet = sujet;
        return this;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public MicroServiceParticipant getEmetteur() {
        return emetteur;
    }

    public EmailNotification emetteur(MicroServiceParticipant microServiceParticipant) {
        this.emetteur = microServiceParticipant;
        return this;
    }

    public void setEmetteur(MicroServiceParticipant microServiceParticipant) {
        this.emetteur = microServiceParticipant;
    }

    public Set<EmailParticipant> getDestinataires() {
        return destinataires;
    }

    public EmailNotification destinataires(Set<EmailParticipant> emailParticipants) {
        this.destinataires = emailParticipants;
        return this;
    }

    public EmailNotification addDestinataires(EmailParticipant emailParticipant) {
        this.destinataires.add(emailParticipant);
        emailParticipant.setEmailNotification(this);
        return this;
    }

    public EmailNotification removeDestinataires(EmailParticipant emailParticipant) {
        this.destinataires.remove(emailParticipant);
        emailParticipant.setEmailNotification(null);
        return this;
    }

    public void setDestinataires(Set<EmailParticipant> emailParticipants) {
        this.destinataires = emailParticipants;
    }

    public Set<TemplateParamValue> getParamValues() {
        return paramValues;
    }

    public EmailNotification paramValues(Set<TemplateParamValue> templateParamValues) {
        this.paramValues = templateParamValues;
        return this;
    }

    public EmailNotification addParamValues(TemplateParamValue templateParamValue) {
        this.paramValues.add(templateParamValue);
        templateParamValue.setEmailNotification(this);
        return this;
    }

    public EmailNotification removeParamValues(TemplateParamValue templateParamValue) {
        this.paramValues.remove(templateParamValue);
        templateParamValue.setEmailNotification(null);
        return this;
    }

    public void setParamValues(Set<TemplateParamValue> templateParamValues) {
        this.paramValues = templateParamValues;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailNotification)) {
            return false;
        }
        return id != null && id.equals(((EmailNotification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailNotification{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            ", sujet='" + getSujet() + "'" +
            "}";
    }
}
