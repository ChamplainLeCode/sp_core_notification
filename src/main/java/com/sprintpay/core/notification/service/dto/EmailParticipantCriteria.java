package com.sprintpay.core.notification.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sprintpay.core.notification.domain.EmailParticipant} entity. This class is used
 * in {@link com.sprintpay.core.notification.web.rest.EmailParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /email-participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmailParticipantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter mail;

    private LongFilter emailNotificationId;

    public EmailParticipantCriteria() {
    }

    public EmailParticipantCriteria(EmailParticipantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.mail = other.mail == null ? null : other.mail.copy();
        this.emailNotificationId = other.emailNotificationId == null ? null : other.emailNotificationId.copy();
    }

    @Override
    public EmailParticipantCriteria copy() {
        return new EmailParticipantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMail() {
        return mail;
    }

    public void setMail(StringFilter mail) {
        this.mail = mail;
    }

    public LongFilter getEmailNotificationId() {
        return emailNotificationId;
    }

    public void setEmailNotificationId(LongFilter emailNotificationId) {
        this.emailNotificationId = emailNotificationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmailParticipantCriteria that = (EmailParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(mail, that.mail) &&
            Objects.equals(emailNotificationId, that.emailNotificationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        mail,
        emailNotificationId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (mail != null ? "mail=" + mail + ", " : "") +
                (emailNotificationId != null ? "emailNotificationId=" + emailNotificationId + ", " : "") +
            "}";
    }

}
