package com.evoter.vote.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class Vote {

    @Id
    @SequenceGenerator(
            name = "vote_sequence",
            sequenceName = "vote_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vote_sequence"
    )

    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
        nullable = false
    )
    private Long userId;

    @Column(
        nullable = false
    )
    private Integer candidateId;

    @Column(
        nullable = false
    )
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

    public Vote(Long id, Long userId, Integer candidateId, Integer pollId, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.candidateId = candidateId;
        this.pollId = pollId;
        this.createdAt = createdAt;
    }

    public Vote() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getPollId() {
        return pollId;
    }

    public void setPollId(Integer pollId) {
        this.pollId = pollId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id) && Objects.equals(userId, vote.userId) && Objects.equals(candidateId, vote.candidateId) && Objects.equals(pollId, vote.pollId) && Objects.equals(createdAt, vote.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, candidateId, pollId, createdAt);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", userId=" + userId +
                ", candidateId=" + candidateId +
                ", pollId=" + pollId +
                ", createdAt=" + createdAt +
                '}';
    }
}
