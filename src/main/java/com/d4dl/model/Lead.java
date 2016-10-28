package com.d4dl.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joshuadeford on 7/31/16.
 */
@Data
@Entity
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
}
