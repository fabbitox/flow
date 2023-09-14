package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.entity.Control;

public interface ControlRepository extends JpaRepository<Control, Integer> {
	public List<Control> findByCtrlType(int ctrlType);
}
