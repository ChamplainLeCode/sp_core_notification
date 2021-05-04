package com.sprintpay.core.notification.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SmsNotification.
 */
@Entity
@Table(name = "sms_notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SmsNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @OneToOne
    @JoinColumn(unique = true)
    private MicroServiceParticipant emetteur;

    @OneToMany(mappedBy = "smsNotification")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<SmsParticipant> destinataires = new HashSet<>();

    @OneToMany(mappedBy = "smsNotification")
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

    public SmsNotification message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MicroServiceParticipant getEmetteur() {
        return emetteur;
    }

    public SmsNotification emetteur(MicroServiceParticipant microServiceParticipant) {
        this.emetteur = microServiceParticipant;
        return this;
    }

    public void setEmetteur(MicroServiceParticipant microServiceParticipant) {
        this.emetteur = microServiceParticipant;
    }

    public Set<SmsParticipant> getDestinataires() {
        return destinataires;
    }

    public SmsNotification destinataires(Set<SmsParticipant> smsParticipants) {
        this.destinataires = smsParticipants;
        return this;
    }

    public SmsNotification addDestinataires(SmsParticipant smsParticipant) {
        this.destinataires.add(smsParticipant);
        smsParticipant.setSmsNotification(this);
        return this;
    }

    public SmsNotification removeDestinataires(SmsParticipant smsParticipant) {
        this.destinataires.remove(smsParticipant);
        smsParticipant.setSmsNotification(null);
        return this;
    }

    public void setDestinataires(Set<SmsParticipant> smsParticipants) {
        this.destinataires = smsParticipants;
    }

    public Set<TemplateParamValue> getParamValues() {
        return paramValues;
    }

    public SmsNotification paramValues(Set<TemplateParamValue> templateParamValues) {
        this.paramValues = templateParamValues;
        return this;
    }

    public SmsNotification addParamValues(TemplateParamValue templateParamValue) {
        this.paramValues.add(templateParamValue);
        templateParamValue.setSmsNotification(this);
        return this;
    }

    public SmsNotification removeParamValues(TemplateParamValue templateParamValue) {
        this.paramValues.remove(templateParamValue);
        templateParamValue.setSmsNotification(null);
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
        if (!(o instanceof SmsNotification)) {
            return false;
        }
        return id != null && id.equals(((SmsNotification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmsNotification{" +
            "id=" + getId() +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
