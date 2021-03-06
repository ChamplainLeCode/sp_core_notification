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
 * Criteria class for the {@link com.sprintpay.core.notification.domain.MicroServiceParticipant} entity. This class is used
 * in {@link com.sprintpay.core.notification.web.rest.MicroServiceParticipantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /micro-service-participants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MicroServiceParticipantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter userId;

    private StringFilter userName;

    private StringFilter userRole;

    public MicroServiceParticipantCriteria() {
    }

    public MicroServiceParticipantCriteria(MicroServiceParticipantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userName = other.userName == null ? null : other.userName.copy();
        this.userRole = other.userRole == null ? null : other.userRole.copy();
    }

    @Override
    public MicroServiceParticipantCriteria copy() {
        return new MicroServiceParticipantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getUserId() {
        return userId;
    }

    public void setUserId(StringFilter userId) {
        this.userId = userId;
    }

    public StringFilter getUserName() {
        return userName;
    }

    public void setUserName(StringFilter userName) {
        this.userName = userName;
    }

    public StringFilter getUserRole() {
        return userRole;
    }

    public void setUserRole(StringFilter userRole) {
        this.userRole = userRole;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MicroServiceParticipantCriteria that = (MicroServiceParticipantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userName, that.userName) &&
            Objects.equals(userRole, that.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        userId,
        userName,
        userRole
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MicroServiceParticipantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
                (userName != null ? "userName=" + userName + ", " : "") +
                (userRole != null ? "userRole=" + userRole + ", " : "") +
            "}";
    }

}
