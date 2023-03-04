package com.evoter.vote.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voterEmail;

    private String candidateName;

    private Integer pollId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            nullable = false
    )
    private Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

}
