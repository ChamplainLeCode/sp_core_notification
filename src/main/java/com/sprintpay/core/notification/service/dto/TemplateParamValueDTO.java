package com.sprintpay.core.notification.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.sprintpay.core.notification.domain.TemplateParamValue} entity.
 */
public class TemplateParamValueDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String value;


    private Long paramId;

    private Long smsNotificationId;

    private Long emailNotificationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getParamId() {
        return paramId;
    }

    public void setParamId(Long templateParamId) {
        this.paramId = templateParamId;
    }

    public Long getSmsNotificationId() {
        return smsNotificationId;
    }

    public void setSmsNotificationId(Long smsNotificationId) {
        this.smsNotificationId = smsNotificationId;
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
        if (!(o instanceof TemplateParamValueDTO)) {
            return false;
        }

        return id != null && id.equals(((TemplateParamValueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TemplateParamValueDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", value='" + getValue() + "'" +
            ", paramId=" + getParamId() +
            ", smsNotificationId=" + getSmsNotificationId() +
            ", emailNotificationId=" + getEmailNotificationId() +
            "}";
    }
}
