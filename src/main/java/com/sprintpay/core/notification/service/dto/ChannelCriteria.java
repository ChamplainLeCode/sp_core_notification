package com.sprintpay.core.notification.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.sprintpay.core.notification.domain.enumeration.ChannelType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.sprintpay.core.notification.domain.Channel} entity. This class is used
 * in {@link com.sprintpay.core.notification.web.rest.ChannelResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /channels?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ChannelCriteria implements Serializable, Criteria {
    /**
     * Class for filtering ChannelType
     */
    public static class ChannelTypeFilter extends Filter<ChannelType> {

        public ChannelTypeFilter() {
        }

        public ChannelTypeFilter(ChannelTypeFilter filter) {
            super(filter);
        }

        @Override
        public ChannelTypeFilter copy() {
            return new ChannelTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ChannelTypeFilter type;

    private StringFilter template;

    private LongFilter paramsId;

    public ChannelCriteria() {
    }

    public ChannelCriteria(ChannelCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.template = other.template == null ? null : other.template.copy();
        this.paramsId = other.paramsId == null ? null : other.paramsId.copy();
    }

    @Override
    public ChannelCriteria copy() {
        return new ChannelCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ChannelTypeFilter getType() {
        return type;
    }

    public void setType(ChannelTypeFilter type) {
        this.type = type;
    }

    public StringFilter getTemplate() {
        return template;
    }

    public void setTemplate(StringFilter template) {
        this.template = template;
    }

    public LongFilter getParamsId() {
        return paramsId;
    }

    public void setParamsId(LongFilter paramsId) {
        this.paramsId = paramsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ChannelCriteria that = (ChannelCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(template, that.template) &&
            Objects.equals(paramsId, that.paramsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        template,
        paramsId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (template != null ? "template=" + template + ", " : "") +
                (paramsId != null ? "paramsId=" + paramsId + ", " : "") +
            "}";
    }

}
