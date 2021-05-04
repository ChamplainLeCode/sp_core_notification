package com.sprintpay.core.notification.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import com.sprintpay.core.notification.domain.enumeration.ChannelType;

/**
 * A DTO for the {@link com.sprintpay.core.notification.domain.Channel} entity.
 */
public class ChannelDTO implements Serializable {
    
    private Long id;

    @NotNull
    private ChannelType type;

    @NotNull
    private String template;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChannelType getType() {
        return type;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChannelDTO)) {
            return false;
        }

        return id != null && id.equals(((ChannelDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChannelDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
