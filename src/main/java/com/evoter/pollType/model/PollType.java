package com.evoter.pollType.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

/**
 * @author showunmioludotun
 */
@Entity
@Data
public class PollType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "PollType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
