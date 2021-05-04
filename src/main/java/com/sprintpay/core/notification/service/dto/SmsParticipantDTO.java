package com.sprintpay.core.notification.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.sprintpay.core.notification.domain.enumeration.Operator;

/**
 * A DTO for the {@link com.sprintpay.core.notification.domain.SmsParticipant} entity.
 */
public class SmsParticipantDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String phone;

    private Operator operator;


    private Long smsNotificationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Long getSmsNotificationId() {
        return smsNotificationId;
    }

    public void setSmsNotificationId(Long smsNotificationId) {
        this.smsNotificationId = smsNotificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SmsParticipantDTO)) {
            return false;
        }

        return id != null && id.equals(((SmsParticipantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmsParticipantDTO{" +
            "id=" + getId() +
            ", phone='" + getPhone() + "'" +
            ", operator='" + getOperator() + "'" +
            ", smsNotificationId=" + getSmsNotificationId() +
            "}";
    }
}
