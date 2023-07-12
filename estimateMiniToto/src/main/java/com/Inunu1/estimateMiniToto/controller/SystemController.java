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

    //チームをスクレイピングするコントローラー
    @GetMapping({"/", "/index"})
    public String getDefaultAccess(Model model) {
        return "index";
    }
    //試合結果をスクレイピングするコントローラー
    /**
     * スクレイピング画面の表示
     *
     * @param model Thymeleafモデルクラス
     * @return テンプレートマッピング用の文字列
     */
    @GetMapping("/scraping")
    public String dispScraping(Model model){
        generateTargetYears(model);
        return "scraping";
    }

    @PostMapping("/scraping")
    public String postScraping(Model model){
        scrapingService.scrape();
        model.addAttribute("info","取得成功");
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