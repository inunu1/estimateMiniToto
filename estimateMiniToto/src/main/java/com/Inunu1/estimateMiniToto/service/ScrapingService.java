package com.Inunu1.estimateMiniToto.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapingService {
    private static final String J_LEAGUE_DATA_SITE_URL = "https://data.j-league.or.jp/SFMS01/search";

    /****************************************
     * 和名:チーム一覧取得処理
     * 概要:チーム名をスクレイピングする処理
     * 引数:無し
     * 戻り値:チーム一覧画面
     ****************************************/
    public List<String> scrapeTeamName(){
        List<String> teamNames = new ArrayList<>();
        try {
            //docて変数にHTMLを丸ごと入れるよ
            Document doc = Jsoup.connect(J_LEAGUE_DATA_SITE_URL).get();
            //team_ids-check内のチーム名の有るところ丸ごと取るよ
            Element teamInfoBox = doc.getElementsByClass("clearbox").get(1);

            Element teamInfoBoxChild = teamInfoBox.getElementsByClass("box-s-base").first();
            Element teamInfoArea = teamInfoBoxChild.getElementsByClass("box-overflow").last();
            //チーム名が入ったラベルタグ一覧を取るよ
            Elements teamElements = teamInfoArea.select("option");
            //ラベルからチーム名を抜き出すよ
            for (Element teamElement : teamElements){
                String teamName = teamElement.text();
                teamNames.add(teamName);
                //System.out.println(teamName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return teamNames;
    }
}
