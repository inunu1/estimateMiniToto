package com.Inunu1.estimateMiniToto.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class TeamInfoCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public int getMaxTeamId() {
		Object maxTeamId = entityManager.createNativeQuery("SELECT MAX(team_id) FROM team_info;").getSingleResult();
		if (Objects.isNull(maxTeamId)) {
			return 0;
		}
		return (int) maxTeamId;
	}
}
