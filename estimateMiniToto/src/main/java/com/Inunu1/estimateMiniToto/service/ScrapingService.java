package com.Inunu1.estimateMiniToto.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.Inunu1.estimateMiniToto.repository.TeamInfoCustomRepository;
import com.Inunu1.estimateMiniToto.repository.TeamInfoRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Inunu1.estimateMiniToto.code.GameResultCode;
import com.Inunu1.estimateMiniToto.model.table.GameResult;
import com.Inunu1.estimateMiniToto.model.table.TeamInfo;
import com.Inunu1.estimateMiniToto.repository.GameResultCustomRepository;
import com.Inunu1.estimateMiniToto.repository.GameResultRepository;
import com.Inunu1.estimateMiniToto.util.DateTimeUtil;
import org.springframework.transaction.annotation.Transactional;

import static com.Inunu1.estimateMiniToto.util.DateTimeUtil.getNowDateStr;

@Service
public class ScrapingService {
    private static final String J_LEAGUE_DATA_SITE_URL = "https://data.j-league.or.jp/SFMS01/search";
    private static final String J_LEAGUE_SEARCH_RESULT_URL = "https://data.j-league.or.jp/SFMS01/search?competition_years=";

    @Autowired
    private GameResultRepository gameResultRepository;
    @Autowired
    private TeamInfoRepository teamInfoRepository;
    
    // TODO 後で2つに集約したい
    @Autowired
    private GameResultCustomRepository gameResultCustomRepository;
    @Autowired
    private TeamInfoCustomRepository teamInfoCustomRepository;
    
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
    @Transactional
    public List<TeamInfo> scrapeTeamName(){
        List<TeamInfo> teamInfoList = new ArrayList<>();
        //DBを初期化するよ
        teamInfoRepository.deleteAll();
        try {
            //docて変数にHTMLを丸ごと入れるよ
            Document doc = Jsoup.connect(J_LEAGUE_DATA_SITE_URL).get();
            //team_ids-check内のチーム名の有るところ丸ごと取るよ
            Element teamInfoBox = doc.getElementsByClass("clearbox").get(1);

            Element teamInfoBoxChild = teamInfoBox.getElementsByClass("box-s-base").first();
            Element teamInfoArea = teamInfoBoxChild.getElementsByClass("box-overflow").last();
            //チーム名が入ったラベルタグ一覧を取るよ
            Elements teamElements = teamInfoArea.select("option");

            String nowDt = getNowDateStr("yyyyMMddHHmm");

            int teamId = teamInfoCustomRepository.getMaxTeamId();
            //ラベルからチーム名を抜き出すよ
            for (int i = 0; i < teamElements.size(); i++) {
                String teamName = teamElements.get(i).text();
                TeamInfo teamInfo = new TeamInfo();
                teamInfo.setTeamName(teamName);
                teamId ++;
                teamInfo.setTeamId(teamId);
                teamInfo.setRegistDate(nowDt);
                teamInfo.setUpdateDate(nowDt);
                teamInfo.setAwayRate(1500);
                teamInfo.setHomeRate(1500);

                teamInfoList.add(teamInfo);


                //System.out.println(teamName);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //DBにチーム情報を突っ込むよ
        teamInfoRepository.saveAll(teamInfoList);

        return teamInfoList;
    }

    public List<GameResult> scrapeResult(){
        List<GameResult> gameResults = new ArrayList<>();
        //現在の年度を取得するよ
        Integer currentYear = DateTimeUtil.getCurrentYear();
        Integer lastyear = currentYear -1;

        gameResults.addAll(scrapeResult(String.valueOf(lastyear)));
        gameResults.addAll(scrapeResult(String.valueOf(currentYear)));

        gameResultRepository.saveAll(gameResults);

        return gameResults;
    }

    private List<GameResult> scrapeResult(String year){




        List<GameResult> gameResults = new ArrayList<>();


        // 登録済みレコードの最大IDを取得
        int gameId = gameResultCustomRepository.getMaxGameId();
        // 現在日時の取得
        String nowDt = getNowDateStr("yyyyMMddHHmm");
        // スキップフラグ
        boolean skipFlg = false;
        
        try{
            //docて変数にHTMLを丸ごと入れるよ
            Document doc = Jsoup.connect(J_LEAGUE_SEARCH_RESULT_URL + year).get();
            //tableタグの試合結果丸ごと取るよ
            Element gameResultTable = doc.select("table").first();
            //全行丸ごと取るよ
            Elements gameResultTrs = gameResultTable.select("tr");
            //gameResultsに一行ずつ追加するよ
            for (int j = 0; j < gameResultTrs.size(); j++ ){
            	
            	// 収集対象チェック
            	if (skipFlg) {
            		break;
            	}
            	if (j == 0) {
            		// 1行目はヘッダ行で収集対象外のためスキップ
            		continue;
            	}
            	
            	Element gameResultTr = gameResultTrs.get(j);
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
					} else if (i == 4) {
						gameResult.setGameTime(tdText);
					} else if (i == 5) {
						gameResult.setHome(tdText);
					} else if (i == 6) {
						gameResult.setScore(tdText);
					} else if (i == 7) {
						gameResult.setAway(tdText);
					} else if (i == 8) {
						gameResult.setStadium(tdText);
					}

				}

				if (gameResult.getScore().equals("vs")) {
					// 試合前のレコードは"vs" 不要なレコードなので登録対象外にする
					skipFlg = true;
					continue;
				}
				
                gameId ++;
                // スクレイピング項目以外の設定
                gameResult.setGameId(gameId);
                gameResult.setRegistDate(nowDt);
                gameResult.setUpdateDate(nowDt);
                
                // 勝敗判定
                if (gameResult.getScore().equals("中止")) {
                	// 中止の場合は計算判定対象外
                	gameResult.setResult(GameResultCode.CANCEL.getCode());
                	
                } else {
                	String[] score = gameResult.getScore().split("-");
                	int homeScore = Integer.valueOf(score[0]);
                	int awayScore = Integer.valueOf(score[1]);
                	if (homeScore > awayScore) {
                		gameResult.setResult(GameResultCode.HOME_WIN.getCode());
                	} else if (homeScore < awayScore) {
                		gameResult.setResult(GameResultCode.AWAY_WIN.getCode());
                	} else {
                		gameResult.setResult(GameResultCode.DRAW.getCode());
                	}
                }
                
                gameResults.add(gameResult);
            }

        }catch (IOException e){
            throw new RuntimeException(e);
        }
        
        return gameResults;
    }

}
