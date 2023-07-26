package com.Inunu1.estimateMiniToto.controller;

import com.Inunu1.estimateMiniToto.Util.DateTimeUtil;
import com.Inunu1.estimateMiniToto.service.ScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SystemController {
    @Autowired
    private ScrapingService scrapingService;

    /****************************************
     * 和名:メニュー画面表示処理
     * 概要:メニュー画面を表示する
     * 引数:無し
     * 戻り値:メニュー画面
     ****************************************/
    @GetMapping({"/", "/index"})
    public String getDefaultAccess(Model model) {
        return "index";
    }

    /****************************************
     * 和名:チーム一覧画面表示処理
     * 概要:チーム名をスクレイピングする処理を呼び出す
     * 引数:無し
     * 戻り値:チーム一覧画面
     ****************************************/
    @GetMapping("/scrape-team")
    public String getScrapingTeam(Model model){
        List<String> teamNames = scrapingService.scrapeTeamName();
        model.addAttribute("teamNames", teamNames);
        model.addAttribute("info", "取得成功");
        return "index";
    }

    /**
     *
     */
    @GetMapping("/scrape-result")
    public String getScrapingResult(Model model){
        List<String> teamNames = scrapingService.scrapeResult();
        model.addAttribute("teamNames", teamNames);
        model.addAttribute("info", "取得成功");
        return "index";
    }


    /**
     * 対象年を画面パラメータに設定する（現在年から過去5年を設定）
     * @param model Thymeleafモデルクラス
     */
    private void generateTargetYears(Model model) {
        List<Integer> targetYears = DateTimeUtil.getPastYears(5);
        model.addAttribute("targetYears", targetYears);
    }

    //レーティング計算を行うコントローラー

}