package com.Inunu1.estimateMiniToto.model.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "team_info")
public class TeamInfo {
    //主キー
    @Id
    @Column(name = "team_id")
    private int teamId;

    @Column(name = "team_short_name")
    private String teamName;

    @Column(name = "home_rate")
    private int homeRate;

    @Column(name = "away_rate")
    private int awayRate;

    @Column(name = "regist_date")
    private String registDate;

    @Column(name = "update_date")
    private String updateDate;
}
