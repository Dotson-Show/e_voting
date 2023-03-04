package com.evoter.poll.model;

import com.evoter.candidate.model.Candidate;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pollTypeId;
    @OneToMany
    private Set<Candidate> candidateList;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public void setcandidateList(List<Candidate> candidates) {
        this.candidateList = new HashSet<>(candidates);
    }


}
