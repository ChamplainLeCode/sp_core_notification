package com.sprintpay.core.notification.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TemplateParamValue.
 */
@Entity
@Table(name = "template_param_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TemplateParamValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "value", nullable = false)
    private String value;

    @OneToOne
    @JoinColumn(unique = true)
    private TemplateParam param;

    @ManyToOne
    @JsonIgnoreProperties(value = "paramValues", allowSetters = true)
    private SmsNotification smsNotification;

    @ManyToOne
    @JsonIgnoreProperties(value = "paramValues", allowSetters = true)
    private EmailNotification emailNotification;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TemplateParamValue name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public TemplateParamValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TemplateParam getParam() {
        return param;
    }

    public TemplateParamValue param(TemplateParam templateParam) {
        this.param = templateParam;
        return this;
    }

    public void setParam(TemplateParam templateParam) {
        this.param = templateParam;
    }

    public SmsNotification getSmsNotification() {
        return smsNotification;
    }

    public TemplateParamValue smsNotification(SmsNotification smsNotification) {
        this.smsNotification = smsNotification;
        return this;
    }

    public void setSmsNotification(SmsNotification smsNotification) {
        this.smsNotification = smsNotification;
    }

    public EmailNotification getEmailNotification() {
        return emailNotification;
    }

    public TemplateParamValue emailNotification(EmailNotification emailNotification) {
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
        if (!(o instanceof TemplateParamValue)) {
            return false;
        }
        return id != null && id.equals(((TemplateParamValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateParamValue{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            "}";
    }
}
