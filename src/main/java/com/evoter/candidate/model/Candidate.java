package com.evoter.candidate.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long partyId;

    private Long pollTypeId;

    private String name;

    private String sex;

    private int age;
    private Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

}
