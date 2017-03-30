package com.xk.chinesechess.stage.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.xk.chinesechess.message.PackageInfo;
import com.xk.chinesechess.message.Xiaqi;
import com.xk.chinesechess.stage.MainStage;
import com.xk.chinesechess.utils.Constant;
import com.xk.chinesechess.utils.JSONUtil;

public class Qipan extends Image {
	private MainStage stage;
	public Qipan(TextureRegion bgt,MainStage stage){
		super(bgt);
		this.stage=stage;
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
	}
	
	
	public void initListener(){
		this.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float screenX, float screenY,
					int pointer, int button) {
				if(!stage.isIsrunning()||!stage.isMyTurn()){
					System.out.println("还不能下棋");
					return super.touchDown(event,screenX, screenY, pointer, button);
				}
				int x=((int)screenX+45)/72-1;
				int y=(((int)screenY+45)/72-1);
				if(stage.getLastSelected()!=null&&x>=0&&x<=9&&y>=0&&y<=9){
					int srcX=(int) stage.getLastSelected().getCoordinateX();
					int srcY=(int) stage.getLastSelected().getCoordinateY();
					if (stage.moveQizi(stage.getLastSelected(), x, y)) {
						stage.changeLocation(stage.getLastSelected(), x, y);
						stage.setMyTurn(false);
						if(!stage.isLocal()){
							PackageInfo pi=new PackageInfo();
							pi.setApp(Constant.APP);
							pi.setFrom(Constant.me.getCid());
							pi.setType(Constant.MSG_XIAQI);
							pi.setTo(Constant.enamy.getCid());
							pi.setMsg(JSONUtil.toJosn(new Xiaqi(srcX,srcY,x, y)));
							Constant.mSender.writeMessage(JSONUtil.toJosn(pi));
						}else{
							stage.computerMove();
						}
						stage.decideFailer();
					}else{
//						Constant.sounds.get("illegal").play();
					}
				}else{
//					Constant.sounds.get("illegal").play();
				}
				return true;
			}
			
		});
	}
	
}
