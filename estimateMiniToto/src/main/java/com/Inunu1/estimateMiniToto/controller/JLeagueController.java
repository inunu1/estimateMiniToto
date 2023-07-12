package com.Inunu1.estimateMiniToto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class JLeagueController {

    @GetMapping("/teams")
    public String showTeamList(Model model) {
        // チーム一覧のデータを取得（仮のデータ）
        List<String> teamList = Arrays.asList("チームA", "チームB", "チームC", "チームD");

        // モデルにデータをセット
        model.addAttribute("teamList", teamList);

        return "teamList";
    }

    @GetMapping("/results")
    public String showMatchResults(Model model) {
        // 試合結果のデータを取得（仮のデータ）
        List<String> matchResults = Arrays.asList(
                "試合1: チームA vs チームB (1-0)",
                "試合2: チームC vs チームD (2-1)",
                "試合3: チームB vs チームC (0-0)"
        );

        // モデルにデータをセット
        model.addAttribute("matchResults", matchResults);

        return "matchResults";
    }
}
