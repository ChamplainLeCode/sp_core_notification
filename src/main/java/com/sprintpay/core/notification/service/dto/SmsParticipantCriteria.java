package com.sprintpay.core.notification.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.sprintpay.core.notification.domain.enumeration.Operator;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sprintpay.core.notification.domain.SmsParticipant} entity. This class is used
 * in {@link com.sprintpay.core.notification.web.rest.SmsParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sms-participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SmsParticipantCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Operator
     */
    public static class OperatorFilter extends Filter<Operator> {

        public OperatorFilter() {
        }

        public OperatorFilter(OperatorFilter filter) {
            super(filter);
        }

        @Override
        public OperatorFilter copy() {
            return new OperatorFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter phone;

    private OperatorFilter operator;

    private LongFilter smsNotificationId;

    public SmsParticipantCriteria() {
    }

    public SmsParticipantCriteria(SmsParticipantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.operator = other.operator == null ? null : other.operator.copy();
        this.smsNotificationId = other.smsNotificationId == null ? null : other.smsNotificationId.copy();
    }

    @Override
    public SmsParticipantCriteria copy() {
        return new SmsParticipantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public OperatorFilter getOperator() {
        return operator;
    }

    public void setOperator(OperatorFilter operator) {
        this.operator = operator;
    }

    public LongFilter getSmsNotificationId() {
        return smsNotificationId;
    }

    public void setSmsNotificationId(LongFilter smsNotificationId) {
        this.smsNotificationId = smsNotificationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SmsParticipantCriteria that = (SmsParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(operator, that.operator) &&
            Objects.equals(smsNotificationId, that.smsNotificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        phone,
        operator,
        smsNotificationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SmsParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (phone != null ? "phone=" + phone + ", " : "") +
                (operator != null ? "operator=" + operator + ", " : "") +
                (smsNotificationId != null ? "smsNotificationId=" + smsNotificationId + ", " : "") +
            "}";
    }

}
