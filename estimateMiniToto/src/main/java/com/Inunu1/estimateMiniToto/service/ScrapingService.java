package com.Inunu1.estimateMiniToto.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ScrapingService {
    private static final String J_LEAGUE_DATA_SITE_URL = "https://data.j-league.or.jp/SFMS01/search";

    public void scrape(){
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
                System.out.println(teamName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
