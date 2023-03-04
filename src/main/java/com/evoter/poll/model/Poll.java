package com.evoter.poll.model;

import com.evoter.vote.model.Vote;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pollName;

    private Date pollDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }


}
