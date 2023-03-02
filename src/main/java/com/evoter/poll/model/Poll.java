package com.evoter.poll.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false
    )
    private Integer pollTypeId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            nullable = false
    )
    private Date pollDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }


}
