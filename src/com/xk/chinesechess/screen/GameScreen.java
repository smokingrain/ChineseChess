package com.xk.chinesechess.screen;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

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
import com.xk.chinesechess.utils.StringUtil;

public class GameScreen extends ScreenAdapter implements MessageCallBack {
    private GameWStage stage = null;

    public GameScreen(int ismyplace,boolean islocal) {
        //true 为自适应分辨率
        stage = new GameWStage(720,1280, false,ismyplace,islocal);
    }

    public void rebuild(int isMyPlace,boolean isLocal){
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
				int version = pack.getVersion();
				if(version > Constant.msgVersion) {
					Constant.msgVersion = version;
				}
				if(Constant.MSG_JOIN.equals(pack.getType())){
					System.out.println(JSONUtil.toJosn(pack));
					String msg=pack.getMsg();
					Map<String, Object> obj = JSONUtil.fromJson(msg);
					List<Map<String, Object>> members = (List<Map<String, Object>>) obj.get("members");
					for(Map<String, Object> member : members) {
						Object id = member.get("id");
						if(!Constant.me.getCid().equals(id)) {
							Client c = new Client();
							c.setCid(id.toString());
							c.setCname(member.get("name").toString());
							c.setRoomid((String) member.get("room"));
							Constant.enamy = c;
							break;
						}
					}
					
				}else if(Constant.MSG_ACTION.equals(pack.getType())){
					String msg = pack.getMsg();
					Map<String, Object> cmdInfo = JSONUtil.fromJson(msg);
					String cmd = (String) cmdInfo.get("cmd");
					if(StringUtil.isBlank(cmd)) {
						return;
					}
					boolean result = false;
					if("move".equals(cmd)) {
						result = move(cmdInfo, pack);
					} else if("undo".equals(cmd)) {
						result = undo(cmdInfo, pack);
					} else if("givein".equals(cmd)) {
						result = givein(cmdInfo, pack);
					} else if("ready".equals(cmd)) {
						result = ready(cmdInfo, pack);
					}
					
				}else if(Constant.MSG_PING.equals(pack.getType())){
					stage.gameEnd(false);
					Constant.mSender.showInfo(pack.getMsg());
				}else if(Constant.MSG_REGRET.equals(pack.getType())){
//					stage.undo();
//					stage.undo();
//					Constant.mSender.showInfo(pack.getMsg());
				}else if(Constant.MSG_EXIT_ROOM.equals(pack.getType())){
					stage.enamyRunAway();
				}else if(Constant.MSG_DISCONNECT.equals(pack.getType())){
					Gdx.app.exit();
				}
				
			}
		});
		
		return false;
	}
	
	/**
	 * 下棋
	 * @param params
	 * @param info
	 * @return
	 */
	private boolean move(Map<String, Object> cmdInfo, PackageInfo info) {
		int src = (int) cmdInfo.get("src");
		int dest = (int) cmdInfo.get("dest");
		stage.moveQizi(src, dest);
		
		String fen = (String) cmdInfo.get("fen");
		String cap = (String) cmdInfo.get("cap");
		String chk = (String) cmdInfo.get("chk");
		String rep = (String) cmdInfo.get("rep");
		String peace = (String) cmdInfo.get("peace");
		String mat = (String) cmdInfo.get("mat");
		if("mat".equals(mat)) {//将死
			if(info.getFrom().equals(Constant.me.getCid())) {
				stage.gameEnd(true);
				Constant.mSender.showInfo("你赢了！");
			}else {
				stage.gameEnd(false);
				Constant.mSender.showInfo("你输了！");
			}
		}else if("peace".equals(peace)) {//平局
			stage.gameEnd(false);
			Constant.mSender.showInfo("和棋！");
		}else if("rep".equals(rep)) {//赖皮棋
			if(info.getFrom().equals(Constant.me.getCid())) {
				stage.gameEnd(false);
			}else {
				stage.gameEnd(true);
			}
			Constant.mSender.showInfo("三将不理！");
		}else if("chk".equals(chk)) {//将军
			Constant.mSender.showInfo("将军！");
		}
		return true;
	}
	
	/**
	 * 准备
	 * @param params
	 * @param info
	 * @return
	 */
	private boolean ready(Map<String, Object> params, PackageInfo info) {
		String from = info.getFrom();
		if(Constant.me.getCid().equals(from)) {
			Constant.mSender.showInfo("准备完毕");
		}else {
			Constant.mSender.showInfo("对方准备完毕");
		}
		if(null != params.get("fen")) {
			String fen = (String) params.get("fen");
			stage.gameReady();
			stage.initPieces(fen);
		}
		return true;
	}
	
	/**
	 * 悔棋
	 * @param params
	 * @param info
	 * @return
	 */
	private boolean undo(Map<String, Object> params, PackageInfo info) {
		String from = info.getFrom();
		if(Constant.me.getCid().equals(from)) {
			Constant.mSender.showInfo("准备完毕");
		}else {
			Constant.mSender.showInfo("对方准备完毕");
		}
		
		return false;
	}
	
	/**
	 * 认输
	 * @param params
	 * @param info
	 * @return
	 */
	private boolean givein(Map<String, Object> params, PackageInfo info) {
		String from = info.getFrom();
		if(Constant.me.getCid().equals(from)) {
			stage.gameEnd(false);
			Constant.mSender.showInfo("认输了。。。");
		}else {
			stage.gameEnd(true);
			Constant.mSender.showInfo("对方认输了。。。");
		}
		return false;
	}
	

    
}