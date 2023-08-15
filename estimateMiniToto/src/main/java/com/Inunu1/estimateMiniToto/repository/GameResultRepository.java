package com.Inunu1.estimateMiniToto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Inunu1.estimateMiniToto.model.table.GameResult;

public interface GameResultRepository extends JpaRepository<GameResult, Integer> {
    // カスタムなクエリや操作が必要な場合はここに追加します
}
