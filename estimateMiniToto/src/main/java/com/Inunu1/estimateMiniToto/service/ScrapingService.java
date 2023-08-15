package com.Inunu1.estimateMiniToto.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Inunu1.estimateMiniToto.model.table.TeamInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.Inunu1.estimateMiniToto.model.table.GameResult;

@Service
public class ScrapingService {
    private static final String J_LEAGUE_DATA_SITE_URL = "https://data.j-league.or.jp/SFMS01/search";
    private static final String J_LEAGUE_SEARCH_RESULT_URL = "https://data.j-league.or.jp/SFMS01/search?competition_years=2023";

    /****************************************
     * 和名:チーム一覧取得処理
     * 概要:チーム名をスクレイピングする処理
     * 引数:無し
     * 戻り値:チーム一覧画面
     ****************************************/
    /**
     *
     *
     * @return
     */
    public List<TeamInfo> scrapeTeamName(){
        List<TeamInfo> teamInfoList = new ArrayList<>();
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
                TeamInfo teamInfo = new TeamInfo();
                teamInfo.setTeamName(teamName);
                teamInfoList.add(teamInfo);
                //System.out.println(teamName);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //DBにチーム情報を突っ込むよ

        return teamInfoList;
    }

    public List<GameResult> scrapeResult(){
        List<GameResult> gameResults = new ArrayList<>();
        try{
            //docて変数にHTMLを丸ごと入れるよ
            Document doc = Jsoup.connect(J_LEAGUE_SEARCH_RESULT_URL).get();
            //tableタグの試合結果丸ごと取るよ
            Element gameResultTable = doc.select("table").first();
            //全行丸ごと取るよ
            Elements gameResultTrs = gameResultTable.select("tr");
            //gameResultsに一行ずつ追加するよ
            for (Element gameResultTr : gameResultTrs){
                GameResult gameResult = new GameResult();

                //行のtdタグを全部取るよ
                Elements gameResultTds = gameResultTr.select("td");
                for (int i = 0; i < gameResultTds.size(); i++) {

                    Element gameResultTd = gameResultTds.get(i);

                    String tdText = gameResultTd.text();

                    if (i == 0) {
                        gameResult.setYear(tdText);
                    } else if (i == 1) {
                        gameResult.setTournament(tdText);
                    } else if (i == 2) {
                        gameResult.setSection(tdText);
                    } else if (i == 3) {
                        gameResult.setGameDate(tdText);
                    }else if (i == 4) {
                        gameResult.setGameTime(tdText);
                    }else if (i == 5) {
                        gameResult.setHome(tdText);
                    }else if (i == 6) {
                        gameResult.setScore(tdText);
                    }else if (i == 7) {
                        gameResult.setAway(tdText);
                    }else if (i == 8) {
                        gameResult.setStadium(tdText);
                    }else if (i == 9) {
                        gameResult.setResult(tdText);
                    }

                    // TODO 他の項目についても設定していく
                }

                gameResults.add(gameResult);

            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return gameResults;
    }
}
