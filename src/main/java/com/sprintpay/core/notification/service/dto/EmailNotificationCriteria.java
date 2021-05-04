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
 * Criteria class for the {@link com.sprintpay.core.notification.domain.EmailNotification} entity. This class is used
 * in {@link com.sprintpay.core.notification.web.rest.EmailNotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /email-notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmailNotificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter message;

    private StringFilter sujet;

    private LongFilter emetteurId;

    private LongFilter destinatairesId;

    private LongFilter paramValuesId;

    public EmailNotificationCriteria() {
    }

    public EmailNotificationCriteria(EmailNotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.message = other.message == null ? null : other.message.copy();
        this.sujet = other.sujet == null ? null : other.sujet.copy();
        this.emetteurId = other.emetteurId == null ? null : other.emetteurId.copy();
        this.destinatairesId = other.destinatairesId == null ? null : other.destinatairesId.copy();
        this.paramValuesId = other.paramValuesId == null ? null : other.paramValuesId.copy();
    }

    @Override
    public EmailNotificationCriteria copy() {
        return new EmailNotificationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMessage() {
        return message;
    }

    public void setMessage(StringFilter message) {
        this.message = message;
    }

    public StringFilter getSujet() {
        return sujet;
    }

    public void setSujet(StringFilter sujet) {
        this.sujet = sujet;
    }

    public LongFilter getEmetteurId() {
        return emetteurId;
    }

    public void setEmetteurId(LongFilter emetteurId) {
        this.emetteurId = emetteurId;
    }

    public LongFilter getDestinatairesId() {
        return destinatairesId;
    }

    public void setDestinatairesId(LongFilter destinatairesId) {
        this.destinatairesId = destinatairesId;
    }

    public LongFilter getParamValuesId() {
        return paramValuesId;
    }

    public void setParamValuesId(LongFilter paramValuesId) {
        this.paramValuesId = paramValuesId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmailNotificationCriteria that = (EmailNotificationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(message, that.message) &&
            Objects.equals(sujet, that.sujet) &&
            Objects.equals(emetteurId, that.emetteurId) &&
            Objects.equals(destinatairesId, that.destinatairesId) &&
            Objects.equals(paramValuesId, that.paramValuesId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        message,
        sujet,
        emetteurId,
        destinatairesId,
        paramValuesId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailNotificationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (message != null ? "message=" + message + ", " : "") +
                (sujet != null ? "sujet=" + sujet + ", " : "") +
                (emetteurId != null ? "emetteurId=" + emetteurId + ", " : "") +
                (destinatairesId != null ? "destinatairesId=" + destinatairesId + ", " : "") +
                (paramValuesId != null ? "paramValuesId=" + paramValuesId + ", " : "") +
            "}";
    }

}
