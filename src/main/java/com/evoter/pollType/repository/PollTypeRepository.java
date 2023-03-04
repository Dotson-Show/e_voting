package com.evoter.pollType.repository;

import com.evoter.pollType.model.PollType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author showunmioludotun
 */
public interface PollTypeRepository extends JpaRepository<PollType, Long> {

    boolean existsById(Long id);

    boolean existsByName(String name);
}
