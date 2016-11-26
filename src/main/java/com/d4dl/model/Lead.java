package com.d4dl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuadeford on 7/31/16.
 */
@Data
@Entity
@JsonIgnoreProperties({"_links"})
public class Lead extends BaseEntity {

    private String name;

    public enum LeadStatus {
        QUOTED,
        SOLD,
        CANCELED
    }

    @Enumerated(EnumType.STRING)
    private LeadStatus leadStatus;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Category> categories = new ArrayList();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LeadStatus getLeadStatus() { return leadStatus; }
    public void setLeadStatus(LeadStatus leadStatus) { this.leadStatus = leadStatus; }
}
