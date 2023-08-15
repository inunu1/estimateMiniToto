package com.Inunu1.estimateMiniToto.code;

import org.apache.commons.lang3.StringUtils;

/**
 * 試合結果列挙
 */
public enum GameResultCode {

	BEFORE("0","試合前"),
	HOME_WIN("1","ホーム勝ち"),
	AWAY_WIN("2","アウェイ勝ち"),
	DRAW("3","引き分け"),
	CANCEL("9", "中止");
	
	GameResultCode(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	
	public static GameResultCode getGameResultByName (String name) {
		if (StringUtils.isEmpty(name)) {
			return null;
		}
		for (GameResultCode gameResult : GameResultCode.values()) {
			if (StringUtils.equals(name, gameResult.getName())) {
				return gameResult;
			}
		}
		return null;
	}
	
	public static GameResultCode getGameResultByCode (String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		for (GameResultCode gameResult : GameResultCode.values()) {
			if (StringUtils.equals(code, gameResult.getCode())) {
				return gameResult;
			}
		}
		return null;		
	}
	
}
