package com.Inunu1.estimateMiniToto.repository;

import java.util.Objects;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class GameResultCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	public int getMaxGameId() {
		Object maxGmaeId = entityManager.createNativeQuery("SELECT MAX(game_id) FROM game_result;").getSingleResult();
		if (Objects.isNull(maxGmaeId)) {
			return 0;
		}
		return (int) maxGmaeId;
	}
}
