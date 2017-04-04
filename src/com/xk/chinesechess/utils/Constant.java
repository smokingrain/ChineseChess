package com.xk.chinesechess.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.xk.chinesechess.font.FreeFont;
import com.xk.chinesechess.message.Client;
import com.xk.chinesechess.message.MessageListener;
import com.xk.chinesechess.message.MessageSender;

public class Constant {
	
	public static final String APP="cc";
	/*
	 * 
	 */
	public static final int CLOSE_MUSK=4;//显示遮罩
	public static final int SHOW_MUSK=3;//显示遮罩
	public static final int SHOW_MESSAGE=2;//显示消息
	public static final int SHOW_WINDOW=1;
	
	/*
	 * 
	 */
	public static final int CLOSE_XMASK=1;//关闭遮罩
	public static final int REFRESH_DATA=2;//刷新数据
	public static final int CONNECT_SERVER=3;//连接服务器
	public static final int GET_MESSAGE=4;//收到消息
	public static final int SHOULD_UPDATE=5;//收到消息
	public static final int UPDATA_DOWNLOAD=6;//正在下载
	public static final int DOWNLOAD_FINISH=7;//下载完毕
	public static final int DOWNLOAD_ERROR=8;//下载失败
	public static final int DOWNLOAD_TIMEOUT=9;//下载超时
	
	
	//消息类型
	public static final String MSG_EXIT_ROOM="exitRoom";
	public static final String MSG_CROOM="croom";
	public static final String MSG_ROOMS="rooms";
	public static final String MSG_LOGIN="login";
	public static final String MSG_ACTION="action";
	public static final String MSG_JOIN="join";
//	public static final String MSG_JOIN_RESULT="joinResult";
	public static final String MSG_XIAQI="move";
	public static final String MSG_WIN="win";
	public static final String MSG_PING="ping";
	public static final String MSG_REGRET="regret";
	public static final String MSG_READY="ready";
	public static final String MSG_LOSE="lose";
	public static final String MSG_EXIT="exit";
	public static final String MSG_DISCONNECT="disconnect";
	
	public static final int XIAQI_P1=1;//棋子属性
	public static final int XIAQI_P2=2;//棋子属性
	
	public static final Long SERVER=-1L;
	public static final String ALL_CLIENT="all_client";
	
	public static MessageListener listener;
	public static MessageSender mSender;
	public static Client me;
	public static String roomid;
	public static Client enamy;
	
	//游戏状态
	public static final int GAME_START=0;//开始
	public static final int GAME_SELECT=1;//选择房间
	public static final int GAME_RUNNING=2;//游戏中
	public static final int GAME_OVER=-1;//游戏结束
	public static int GameStatus=GAME_START;
	
	public static Map<String,Texture>texturePools=new HashMap<String,Texture>();
//	public static Map<String,Sound> sounds=new HashMap<String,Sound>();
	
	public static void loadResources(){
		Texture R1=new Texture(Gdx.files.internal("data/R1.png"));
		texturePools.put("R1", R1);
		Texture r2=new Texture(Gdx.files.internal("data/r2.png"));
		texturePools.put("r2", r2);
		Texture P1=new Texture(Gdx.files.internal("data/P1.png"));
		texturePools.put("P1", P1);
		Texture p2=new Texture(Gdx.files.internal("data/p2.png"));
		texturePools.put("p2", p2);
		Texture N1=new Texture(Gdx.files.internal("data/N1.png"));
		texturePools.put("N1", N1);
		Texture n2=new Texture(Gdx.files.internal("data/n2.png"));
		texturePools.put("n2", n2);
		Texture K1=new Texture(Gdx.files.internal("data/K1.png"));
		texturePools.put("K1", K1);
		Texture k2=new Texture(Gdx.files.internal("data/k2.png"));
		texturePools.put("k2", k2);
		Texture C1=new Texture(Gdx.files.internal("data/C1.png"));
		texturePools.put("C1", C1);
		Texture c2=new Texture(Gdx.files.internal("data/c2.png"));
		texturePools.put("c2", c2);
		Texture B1=new Texture(Gdx.files.internal("data/B1.png"));
		texturePools.put("B1", B1);
		Texture b2=new Texture(Gdx.files.internal("data/b2.png"));
		texturePools.put("b2", b2);
		Texture A1=new Texture(Gdx.files.internal("data/A1.png"));
		texturePools.put("A1", A1);
		Texture a2=new Texture(Gdx.files.internal("data/a2.png"));
		texturePools.put("a2", a2);
		Texture r_box=new Texture(Gdx.files.internal("data/r_box.png"));
		texturePools.put("r_box", r_box);
		Texture back=new Texture(Gdx.files.internal("data/back.png"));
		texturePools.put("back", back);
		Texture ccanniu=new Texture(Gdx.files.internal("data/ccanniu.png"));
		texturePools.put("ccanniu", ccanniu);
		Texture board=new Texture(Gdx.files.internal("data/board.png"));
		texturePools.put("board", board);
		for(int i=1;i<=4;i++){
			Texture head=new Texture(Gdx.files.internal("data/h"+i+".png"));
			texturePools.put("h"+i, head);
		}
//		Sound win=Gdx.audio.newSound(Gdx.files.internal("data/sounds/win.wav"));
//		sounds.put("win", win);
//		Sound move=Gdx.audio.newSound(Gdx.files.internal("data/sounds/move.wav"));
//		sounds.put("move", move);
//		Sound loss=Gdx.audio.newSound(Gdx.files.internal("data/sounds/loss.wav"));
//		sounds.put("loss", loss);
//		Sound illegal=Gdx.audio.newSound(Gdx.files.internal("data/sounds/illegal.wav"));
//		sounds.put("illegal", illegal);
//		Sound click=Gdx.audio.newSound(Gdx.files.internal("data/sounds/click.wav"));
//		sounds.put("click", click);
//		Sound check=Gdx.audio.newSound(Gdx.files.internal("data/sounds/check.wav"));
//		sounds.put("check", check);
//		Sound capture=Gdx.audio.newSound(Gdx.files.internal("data/sounds/capture.wav"));
//		sounds.put("capture", capture);
	}
	/**
	 * 销毁资源
	 */
	public static void releaseResource(){
		for(Texture texture:texturePools.values()){
			texture.dispose();
		}
		texturePools.clear();
//		for(Sound sound:sounds.values()){
//			sound.dispose();
//		}
//		sounds.clear();
	}
	
	public static BitmapFont getFont(String str){
		FreeFont font=new FreeFont(Constant.mSender);
		BitmapFont textFont=font.getFont(str);
		font.dispose();
		return textFont;
	}
}
