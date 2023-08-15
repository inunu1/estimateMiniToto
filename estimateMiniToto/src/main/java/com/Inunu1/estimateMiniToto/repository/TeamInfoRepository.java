package com.Inunu1.estimateMiniToto.repository;

import com.Inunu1.estimateMiniToto.model.table.TeamInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamInfoRepository extends JpaRepository<TeamInfo, Integer> {
    // カスタムなクエリや操作が必要な場合はここに追加します
}
