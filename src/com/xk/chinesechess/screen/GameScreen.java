package com.xk.chinesechess.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.xk.chinesechess.message.Client;
import com.xk.chinesechess.message.MessageCallBack;
import com.xk.chinesechess.message.PackageInfo;
import com.xk.chinesechess.message.Xiaqi;
import com.xk.chinesechess.stage.GameWStage;
import com.xk.chinesechess.utils.Constant;
import com.xk.chinesechess.utils.FileUtil;
import com.xk.chinesechess.utils.JSONUtil;

public class GameScreen extends ScreenAdapter implements MessageCallBack {
    private GameWStage stage = null;

    public GameScreen(boolean ismyplace,boolean islocal) {
        //true 为自适应分辨率
        stage = new GameWStage(720,1280, false,ismyplace,islocal);
    }

    public void rebuild(boolean isMyPlace,boolean isLocal){
    	stage.rebuild(isMyPlace, isLocal);
    }
    
    private Pixmap getHeadImage(String string) {
    	InputStream in=Gdx.files.internal(string).read();
		byte[] arr=null;
		try {
			arr=FileUtil.toBytes(in);
		} catch (FileNotFoundException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return new Pixmap(arr,0,arr.length);
	}

	@Override
    public void render(float arg0) {
        stage.act();
        stage.draw();
		Gdx.input.setInputProcessor(stage);
    }

	@Override
	public void dispose() {
		stage.destroy();
		stage.dispose();
		super.dispose();
	}

	@Override
	public boolean callBack(final PackageInfo pack) {
		Gdx.app.postRunnable(new Runnable() {
			
			@Override
			public void run() {
				if(Constant.MSG_JOIN.equals(pack.getType())){
					String msg=pack.getMsg();
					Client c=JSONUtil.toBean(msg, Client.class);
					Constant.enamy=c;
				}else if(Constant.MSG_XIAQI.equals(pack.getType())){
					Xiaqi xiaqi=JSONUtil.toBean(pack.getMsg(), Xiaqi.class);
					stage.xiaqi(xiaqi.x, xiaqi.y,xiaqi.destX,xiaqi.destY);
				}else if(Constant.MSG_LOSE.equals(pack.getType())){
					stage.gameEnd(false);
					Constant.mSender.showInfo("你输了！");
				}else if(Constant.MSG_PING.equals(pack.getType())){
					stage.gameEnd(false);
					Constant.mSender.showInfo(pack.getMsg());
				}else if(Constant.MSG_REGRET.equals(pack.getType())){
					stage.undo();
					stage.undo();
					Constant.mSender.showInfo(pack.getMsg());
				}else if(Constant.MSG_WIN.equals(pack.getType())){
					stage.gameEnd(true);
					Constant.mSender.showInfo(pack.getMsg());
				}else if(Constant.MSG_READY.equals(pack.getType())){
					stage.setP2Ready();
				}else if(Constant.MSG_EXIT_ROOM.equals(pack.getType())){
					stage.enamyRunAway();
				}else if(Constant.MSG_DISCONNECT.equals(pack.getType())){
					Gdx.app.exit();
				}
				
			}
		});
		
		return false;
	}
    
}