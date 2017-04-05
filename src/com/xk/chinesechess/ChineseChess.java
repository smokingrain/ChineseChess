package com.xk.chinesechess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.xk.chinesechess.message.MessageListener;
import com.xk.chinesechess.message.MessageSender;
import com.xk.chinesechess.message.PackageInfo;
import com.xk.chinesechess.screen.GameScreen;
import com.xk.chinesechess.utils.Constant;
import com.xk.chinesechess.utils.JSONUtil;
import com.xk.chinesechess.utils.StringUtil;

public class ChineseChess extends Game {
	private GameScreen game;
	private static ChineseChess instance;
	public boolean isMyPlace;
	public boolean isLocal;
	public String roomid;
	
	public static ChineseChess getInstance(MessageSender mSender){
		if(null==instance){
			instance=new ChineseChess(mSender);
		}
		return instance;
	}
	
	public void setListener(MessageListener listener){
		Constant.listener=listener;
	}
	
	
	
	private ChineseChess(MessageSender mSender){
		Constant.mSender=mSender;
	}
	
	@Override
	public void create() {	
		Constant.loadResources();
		createGame();
	}
	
	public void createGame(){
		if(null==game){
			game=new GameScreen(isMyPlace, isLocal);
			if(null!=Constant.listener){
				Constant.listener.registListener(game);
			}
		}else{
			game.rebuild(isMyPlace, isLocal);
		}
		setScreen(game);
		
	}
	

	
	@Override
	public void dispose() {
		getScreen().dispose();
		if(null!=Constant.listener){
			Constant.listener.unregistListener(game);
		}
		if(!StringUtil.isBlank(roomid)){
			PackageInfo info=new PackageInfo(roomid, JSONUtil.toJosn(Constant.me), Constant.me.getCid(), Constant.MSG_EXIT_ROOM, Constant.APP, 0);
			Constant.mSender.writeMessage(JSONUtil.toJosn(info));
		}
		
		Constant.releaseResource();
		ChineseChess.instance=null;
		super.dispose();
	}

	@Override
	public void render() {		
		Gdx.gl.glClearColor(16f/255f, 109f/255f, 152f/255f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
