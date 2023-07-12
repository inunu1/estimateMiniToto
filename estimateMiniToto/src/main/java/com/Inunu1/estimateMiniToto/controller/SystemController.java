package com.Inunu1.estimateMiniToto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.Inunu1.estimateMiniToto.;
import java.util.List;

@Controller
public class SystemController {

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