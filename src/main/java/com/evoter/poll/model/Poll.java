package com.evoter.poll.model;

import com.evoter.candidate.model.Candidate;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

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
    @ManyToMany
    @JoinTable(
            name = "polls_candidates",
            joinColumns = @JoinColumn(
                    name = "poll_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "candidate_id", referencedColumnName = "id"))
    @ToString.Exclude
    private Set<Candidate> candidateList;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @PrePersist
    private void onCreate() {
        createdAt = new Date();
    }

    public void setCandidateList(List<Candidate> candidates) {
        this.candidateList = new HashSet<>(candidates);
    }


}
