package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.entity.Criteria;

public interface CriteriaRepository extends JpaRepository<Criteria, Integer> {

}
