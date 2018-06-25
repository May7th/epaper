package com.oyun.media.epaper.repository;

import com.oyun.media.epaper.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Authority 仓库.
 *
 */
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
