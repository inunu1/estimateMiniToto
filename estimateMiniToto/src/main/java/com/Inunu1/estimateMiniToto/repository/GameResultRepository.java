package com.Inunu1.estimateMiniToto.repository;

import com.Inunu1.estimateMiniToto.model.table.GameResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameResultRepository extends JpaRepository<GameResult, Integer> {
    // カスタムなクエリや操作が必要な場合はここに追加します
}
