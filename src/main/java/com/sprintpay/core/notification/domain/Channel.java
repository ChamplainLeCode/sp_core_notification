package com.sprintpay.core.notification.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.sprintpay.core.notification.domain.enumeration.ChannelType;

/**
 * A Channel.
 */
@Entity
@Table(name = "channel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Channel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ChannelType type;

    @NotNull
    @Column(name = "template", nullable = false)
    private String template;

    @OneToMany(mappedBy = "channel")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<TemplateParam> params = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChannelType getType() {
        return type;
    }

    public Channel type(ChannelType type) {
        this.type = type;
        return this;
    }

    public void setType(ChannelType type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public Channel template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public Set<TemplateParam> getParams() {
        return params;
    }

    public Channel params(Set<TemplateParam> templateParams) {
        this.params = templateParams;
        return this;
    }

    public Channel addParams(TemplateParam templateParam) {
        this.params.add(templateParam);
        templateParam.setChannel(this);
        return this;
    }

    public Channel removeParams(TemplateParam templateParam) {
        this.params.remove(templateParam);
        templateParam.setChannel(null);
        return this;
    }

    public void setParams(Set<TemplateParam> templateParams) {
        this.params = templateParams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Channel)) {
            return false;
        }
        return id != null && id.equals(((Channel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Channel{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", template='" + getTemplate() + "'" +
            "}";
    }
}
