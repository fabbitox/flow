package edu.pnu.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDTO {
	private String name;
	private String contact;
	private Double wl;
	private Integer idcriteria;
	private LocalDateTime wlDt;
	private LocalDateTime alarmDt;
}
