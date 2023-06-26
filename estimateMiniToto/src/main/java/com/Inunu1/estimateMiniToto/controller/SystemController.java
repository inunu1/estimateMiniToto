package com.Inunu1.estimateMiniToto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    //チームをスクレイピングするコントローラー
    @GetMapping({"/", "/index"})
    public String getDefaultAccess(Model model) {
        return "index";
    }
    //試合結果をスクレイピングするコントローラー


    //レーティング計算を行うコントローラー

}