package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Criteria;

public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

}
