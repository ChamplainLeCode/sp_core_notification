package com.sprintpay.core.notification.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.sprintpay.core.notification.domain.enumeration.Operator;

/**
 * A SmsParticipant.
 */
@Entity
@Table(name = "sms_participant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SmsParticipant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "operator")
    private Operator operator;

    @ManyToOne
    @JsonIgnoreProperties(value = "destinataires", allowSetters = true)
    private SmsNotification smsNotification;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public SmsParticipant phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Operator getOperator() {
        return operator;
    }

    public SmsParticipant operator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public SmsNotification getSmsNotification() {
        return smsNotification;
    }

    public SmsParticipant smsNotification(SmsNotification smsNotification) {
        this.smsNotification = smsNotification;
        return this;
    }

    public void setSmsNotification(SmsNotification smsNotification) {
        this.smsNotification = smsNotification;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsParticipant)) {
            return false;
        }
        return id != null && id.equals(((SmsParticipant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmsParticipant{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", operator='" + getOperator() + "'" +
            "}";
    }
}
