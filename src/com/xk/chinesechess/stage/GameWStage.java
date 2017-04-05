package com.xk.chinesechess.stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.xk.chinesechess.message.PackageInfo;
import com.xk.chinesechess.message.Xiaqi;
import com.xk.chinesechess.stage.ui.Qipan;
import com.xk.chinesechess.utils.Constant;
import com.xk.chinesechess.utils.JSONUtil;
import com.xk.chinesechess.utils.StringUtil;
import com.xk.chinesechess.wlight.Position;
import com.xk.chinesechess.wlight.Search;
import com.xk.chinesechess.zujian.PieceArray;
import com.xk.chinesechess.zujian.Qizi;

public class GameWStage extends Stage implements MainStage{
	
	//AI
//	private Map<Character,String> nameMap=new HashMap<Character,String>();
	private PieceArray pieceArr;
	private boolean computerIsThingking=false;
//	private Position pos = new Position();
//	private Search search = new Search(pos, 12);
	private Qizi[] pieceIndex;
	private PieceArray capturedArr;
	private Qizi lastSelected;
//	private int sqSelected;
//	private int mvLast;
	private int level=0;
	
	
	//UI
	private Texture board;//棋盘背景
	private TextureRegion boardr;
	private Qipan qipan;//棋盘对象
	private Image back;//背景颜色
	private Image box;//选择棋子背景
	private Image destBox;//选择棋子背景2
	private Label hisTimer;//对手用时
	private Label myTimer;//自己用时
	private Label hisThinking;
	private Label myThinking;
	private Label hisLable;
	private Label myLabel;
	private Button btnReady;//准备按钮
	private Button btnHuiq;//悔棋按钮
	private Button btnLose;//认输按钮
	
	//VAR
	private boolean isLocal=false;//单机
	private boolean isMyPlace=false;//主场
	private boolean myTurn=false;
	private boolean p1Ready=false;
	private boolean p2Ready=false;
	private boolean isrunning=false;
	private boolean loadBook=false;
	private long hisTime=0;
	private long myTime=0;
	float totalTime=0;
	

	public GameWStage(float width,float height,boolean size,boolean isMyPlace,boolean isLocal){
		super(width,height,size);
		this.isMyPlace=isMyPlace;//是否主场
		this.isLocal=isLocal;//是否单机
		p2Ready=isLocal;
		initMaps();
		createButtons();
		createMyInfo();
		initListner();
		loadBook();
	}
	
	
	public void rebuild(boolean isMyPlace,boolean isLocal){
		this.isMyPlace=isMyPlace;//是否主场
		this.isLocal=isLocal;//是否单机
	}
	
	public void gameReady(){
		hisTime=0;
		myTime=0;
		isrunning=true;
		p1Ready=true;
		p2Ready=true;
		myTurn=isMyPlace;
		start();
	}
	
	private void start() {
		if(null!=box){
			box.setVisible(false);
		}
		if(null!=destBox){
			destBox.setVisible(false);
		}
		clearMap();
		pieceArr = new PieceArray();
		pieceIndex = new Qizi[90];;// [90]
		capturedArr = new PieceArray();
//		initActiveBoard();
		lastSelected=null;
//		initPieces();
	}
	
	public void initPieces(String s) {
		QiziSelection listener=new QiziSelection();
//		String s = pos.toFen();
		System.out.println("fenstr:"+s);
		Qizi tmpQizi;
		int row = 9;//
		int col = 0;
		for (int i = 0; i < 90; i++) {
			pieceIndex[i] = null;
		}
		for (int i = 0; i < s.length() && row >= 0;) {
			char tmpChar = s.charAt(i);
			if (tmpChar >= '1' && tmpChar <= '9') {
				i++;
				col += tmpChar - '0';
			}
			if (s.charAt(i) == '/' || s.charAt(i) == ' ') {
				row--;
				col = 0;
			} else {
				char c=s.charAt(i);
				Texture temp=Constant.texturePools.get(c+""+((c+"").equals((c+"").toLowerCase())?2:1));
				TextureRegion tempr=new TextureRegion(temp,0,0,54,54);
				tmpQizi =new Qizi(tempr,(c+"").equals((c+"").toLowerCase())?1:0); 
				tmpQizi.setPosition(72*col+45, 72*row+340);
				tmpQizi.addListener(listener);
				pieceArr.add(tmpQizi);
				pieceIndex[col * 10 + row] = pieceArr
						.getPiece(pieceArr.size() - 1);
				col++;
				addActor(tmpQizi);
			}
			i++;
		}
	}
	
//	public void initActiveBoard() {
//		pos.fromFen(Position.STARTUP_FEN[0]);
//		pos.changeSide();
//	}
	
	public void loadBook() {
		
		if(!loadBook){
			Constant.mSender.openMask("正在加载资源...");
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					Position.init();
					Constant.mSender.closeMask();
					loadBook=true;
					
				}
			});
			t.start();;
			
		}
			
	}
	

	
	private void clearMap(){
		if(null!=pieceArr){
			for(int i=pieceArr.size();i>0;i--){
				pieceArr.getPiece(i-1).remove();
			}
		}
	}
	
	private void createMyInfo(){
		Texture headT=Constant.texturePools.get("h1");
		TextureRegion headR=new TextureRegion(headT,0,0,220,220);
		Image head=new Image(headR);
		head.setSize(220, 220);
		head.setPosition(368, 110);
		addActor(head);
		
		Texture headT2=Constant.texturePools.get("h2");
		TextureRegion headR2=new TextureRegion(headT2,0,0,220,220);
		Image head2=new Image(headR2);
		head2.setSize(220, 220);
		head2.setPosition(368, 1050);
		addActor(head2);
		
		String useTime="已用时:";
		String thinking="正在思考...";
		
		BitmapFont slFont=Constant.getFont(useTime);
		slFont.setColor(0.9f, 0.1f, 0.1f, 1);
		slFont.setScale(1.4f);
		LabelStyle ls=new LabelStyle(slFont, slFont.getColor());
		
		myLabel=new Label(useTime, ls);
		myLabel.setSize(135, 35);
		myLabel.setPosition(25, 60);
		addActor(myLabel);
		
		BitmapFont tlFont=Constant.getFont(thinking);
		tlFont.setColor(0.3f, 0.4f, 0.3f, 1);
		tlFont.setScale(1.4f);
		LabelStyle ts=new LabelStyle(tlFont, tlFont.getColor());
		
		myThinking=new Label(thinking,ts);
		myThinking.setSize(150, 35);
		myThinking.setPosition(25, 100);
		myThinking.setVisible(false);
		addActor(myThinking);
		
		hisLable=new Label(useTime, ls);
		hisLable.setSize(135, 35);
		hisLable.setPosition(25, 1100);
		addActor(hisLable);
		slFont.dispose();
		
		hisThinking=new Label(thinking, ts);
		hisThinking.setSize(150, 35);
		hisThinking.setPosition(25, 1140);
		hisThinking.setVisible(false);
		addActor(hisThinking);
		tlFont.dispose();
		
		
	}
	
	private void initMaps() {
		Texture backT=Constant.texturePools.get("back");
		TextureRegion backRegion=new TextureRegion(backT,0,0,10,10);
		back=new Image(backRegion);
		back.setSize(720, 1280);
		back.setPosition(0, 0);
		addActor(back);
		board=Constant.texturePools.get("board");
		boardr=new TextureRegion(board,0,0,720,790);
		qipan=new Qipan(boardr,this);
		qipan.setPosition(0, 300);
		qipan.setSize(720, 790);
		addActor(qipan);
		qipan.initListener();
		Texture texture=Constant.texturePools.get("r_box");
		TextureRegion region=new TextureRegion(texture, 0, 0, 54, 54);
		box=new Image(region);
		box.setSize(54, 54);
		box.setVisible(false);
		addActor(box);
		destBox=new Image(region);
		destBox.setSize(54, 54);
		destBox.setVisible(false);
		addActor(destBox);
//		nameMap.put('K', "皇");
//		nameMap.put('R', "车");
//		nameMap.put('C', "炮");
//		nameMap.put('N', "马");
//		nameMap.put('P', "兵");
//		nameMap.put('A', "仕");
//		nameMap.put('B', "相");
//		nameMap.put('k', "将");
//		nameMap.put('r', "车");
//		nameMap.put('c', "炮");
//		nameMap.put('n', "马");
//		nameMap.put('p', "卒");
//		nameMap.put('a', "士");
//		nameMap.put('b', "象");
		
	}
	
	public void gameEnd(boolean win){
//		if(win){
//			Constant.sounds.get("win").play();
//		}else{
//			Constant.sounds.get("loss").play();
//		}
		if(null!=box){
			box.setVisible(false);
		}
		if(isLocal){
			Constant.me.setComputer(Constant.me.getComputer()+1);
			if(win){
				Constant.me.setCwin(Constant.me.getCwin()+1);
			}
			Constant.mSender.saveData("Computer", Constant.me.getComputer());
			Constant.mSender.saveData("cwin", Constant.me.getCwin());
		}else{
			Constant.me.setDuizhan(Constant.me.getDuizhan()+1);
			if(win){
				Constant.me.setDwin(Constant.me.getDwin()+1);
			}
			Constant.mSender.saveData("duizhan", Constant.me.getDuizhan());
			Constant.mSender.saveData("dwin", Constant.me.getDwin());
		}
		isrunning=false;
		p1Ready=false;
		p2Ready=false;
		myTurn=false;
	}
	
	private void initListner() {
		btnHuiq.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
//				if(!isMyTurn()||!isrunning){
//					return super.touchDown(event, x, y, pointer, button);
//				}
				if(!isLocal){
					PackageInfo pi=new PackageInfo();
					pi.setApp(Constant.APP);
					pi.setFrom(Constant.me.getCid());
					pi.setType(Constant.MSG_REGRET);
					pi.setTo(Constant.enamy.getRoomid());
					pi.setMsg("对方悔棋");
					Constant.mSender.writeMessage(JSONUtil.toJosn(pi));
				}
//				undo();
//				undo();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		btnReady.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				p1Ready=true;
				System.out.println("ready!!!");
				if(!isrunning){
					if(null!=Constant.enamy){
						PackageInfo info=new PackageInfo();
						info.setApp(Constant.APP);
						info.setFrom(Constant.me.getCid());
						info.setTo(Constant.enamy.getRoomid());
						info.setType(Constant.MSG_ACTION);
						Map<String, Object> cmd = new HashMap<String, Object>();
						cmd.put("cmd", Constant.MSG_READY);
						info.setMsg(JSONUtil.toJosn(cmd));
						Constant.mSender.writeMessage(JSONUtil.toJosn(info));
					}else{
						Constant.mSender.showInfo("请等待玩家加入...");
						return super.touchDown(event, x, y, pointer, button);
					}
				}
				Constant.mSender.showInfo("准备好了");
				rebuild(GameWStage.this.isMyPlace,GameWStage.this.isLocal);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		btnLose.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				if(isrunning&&!computerIsThingking){
					gameEnd(false);
					Constant.mSender.showInfo("认输了...");
					if(!isLocal){
						if(null!=Constant.enamy){
							PackageInfo pi=new PackageInfo();
							pi.setApp(Constant.APP);
							pi.setFrom(Constant.me.getCid());
							pi.setType(Constant.MSG_WIN);
							pi.setTo(Constant.enamy.getRoomid());
							pi.setMsg("你赢了，我认输！");
							Constant.mSender.writeMessage(JSONUtil.toJosn(pi));
						}else{
							Constant.mSender.showInfo("请等待玩家加入...");
						}
						
					}
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
	}

	private void createButtons() {
		Texture ccanniu=Constant.texturePools.get("ccanniu");
		TextureRegion[][] temp=TextureRegion.split(ccanniu, ccanniu.getWidth()/3, ccanniu.getHeight()/3);
		TextureRegionDrawable upHuiq=new TextureRegionDrawable(temp[0][1]);
		TextureRegionDrawable clkHuiq=new TextureRegionDrawable(new TextureRegion(temp[1][1]));
		TextureRegionDrawable downHuiq=new TextureRegionDrawable(new TextureRegion(temp[2][1]));
		ButtonStyle bs=new ButtonStyle(upHuiq, downHuiq, clkHuiq);
		btnHuiq=new Button(bs);
		btnHuiq.setSize(123, 46);
		btnHuiq.setPosition(25,206);
		addActor(btnHuiq);
		TextureRegionDrawable upbtnlose=new TextureRegionDrawable(temp[0][2]);
		TextureRegionDrawable clklose=new TextureRegionDrawable(temp[1][2]);
		TextureRegionDrawable downbtnlose=new TextureRegionDrawable(temp[2][2]);
		ButtonStyle bsbtnlose=new ButtonStyle(upbtnlose, downbtnlose, clklose);
		btnLose=new Button(bsbtnlose);
		btnLose.setSize(123, 46);
		btnLose.setPosition(195,206);
		addActor(btnLose);
		TextureRegionDrawable upbtnready=new TextureRegionDrawable(temp[0][0]);
		TextureRegionDrawable clktnready=new TextureRegionDrawable(temp[1][0]);
		TextureRegionDrawable downbtnready=new TextureRegionDrawable(temp[2][0]);
		ButtonStyle bsbtnready=new ButtonStyle(upbtnready, downbtnready, clktnready);
		btnReady=new Button(bsbtnready);
		btnReady.setSize(123, 46);
		btnReady.setPosition(417,50);
		addActor(btnReady);
	}

	public boolean isLocal() {
		return isLocal;
	}

	public boolean isMyPlace() {
		return isMyPlace;
	}
	
	public boolean isMyTurn() {
		return myTurn;
	}

	public void setMyTurn(boolean myTurn) {
		this.myTurn = myTurn;
	}
	
	public boolean isIsrunning() {
		return isrunning;
	}

	public void setIsrunning(boolean isrunning) {
		this.isrunning = isrunning;
	}
	public boolean isP1Ready() {
		return p1Ready;
	}

	public void setP1Ready(boolean p1Ready) {
		this.p1Ready = p1Ready;
	}

	public boolean isP2Ready() {
		return p2Ready;
	}

	public void setP2Ready() {
		Constant.mSender.showInfo("对方准备完毕");
		this.p2Ready = true;
//		if(p1Ready){
//			gameReady();
//		}
	}

	public void destroy(){
		
	}
	
	public void enamyRunAway(){
		gameEnd(true);
		Constant.enamy=null;
		isMyPlace=true;
		Constant.mSender.showInfo("对面的吓跑了！");
	}
	
	private void selectQz(Qizi qz) {
		int x1 = (int) qz.getCoordinateX();
		int y1 = (int) qz.getCoordinateY();
		Position.COORD_XY(x1 + Position.FILE_LEFT, y1 + Position.RANK_TOP);
		lastSelected=qz;
		destBox.setVisible(true);
		destBox.setPosition(qz.getX(), qz.getY());
	}
	
	/**
	 *	对方下棋
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public boolean xiaqi(int x1,int y1,int x2,int y2){
		int src=x1 * 10 + y1;
		Qizi qizi=pieceIndex[src];
		if (moveQizi(qizi, x2, y2)) {
			changeLocation(qizi, x2, y2);
			setMyTurn(true);
//			decideFailer();
			return true;
		}
		return false;
	}
	
	public boolean moveQizi(Qizi qz,int dstX,int dstY){
		if (qz == null){
			return false;
		}
//		int srcX = (int) qz.getCoordinateX(), srcY = (int) qz.getCoordinateY();
//		int sqSrc=Position.COORD_XY(srcX + Position.FILE_LEFT, srcY + Position.RANK_TOP);
//		int sqDst = Position.COORD_XY(dstX + Position.FILE_LEFT, dstY + Position.RANK_TOP);
//		int mv=Position.MOVE(sqSrc, sqDst);
//		if (pos.legalMove(mv)) {
//			if (pos.makeMove(mv)) {
//				if (pos.captured()) {
//					pos.setIrrev();
//				}
//				return true;
//			}
//		}
		return true;
	}
	
	public boolean undo(int src, int dst, boolean chk) {
//		if(pos.mvList.size()<1){
//			return false;
//		}
//		int mv=pos.mvList.get(pos.mvList.size()-1);
//		if(mv<=0){
//			return false;
//		}
//		int srcx=Position.FILE_X(Position.SRC(mv))-Position.FILE_LEFT;
//		int srcy=Position.RANK_Y(Position.SRC(mv))-Position.RANK_TOP;
//		int dstx=Position.FILE_X(Position.DST(mv))-Position.FILE_LEFT;
//		int dsty=Position.RANK_Y(Position.DST(mv))-Position.RANK_TOP;
//		int src=srcx*10+srcy;
//		int dst=dstx*10+dsty;
		if (src < 0 || dst < 0) {
			return false;
		}
		lastSelected=pieceIndex[dst];
		selectQz(getLastSelected());
		changeLocation(getLastSelected(), src / 10, src % 10);
		if (chk) {
			pieceIndex[dst] = capturedArr.remove(capturedArr.size() - 1);
			pieceIndex[dst].setVisible(true);
		}
		return true;
	}
	
	public void changeLocation(final Qizi srcQz, int dstX, int dstY) {
		// if dst has a qizi, move it to captured place[dst][1] and set to
		// visible to false
		int src = (int) srcQz.getCoordinate();
		final int dst = dstX * 10 + dstY;
		box.setVisible(true);
		box.setPosition(srcQz.getX(), srcQz.getY());
		Action move=Actions.moveTo(72*dstX+45, 72*(dstY)+340, 0.4f);
		Action bigger=Actions.scaleTo(1.3f, 1.3f, 0.2f);
		Action reset=Actions.scaleTo(1.0f, 1.0f, 0.2f);
		Action endAction=Actions.run(new Runnable() {
			
			@Override
			public void run() {
//				Constant.sounds.get("move").play();
				selectQz(srcQz);
			}
		});
		Action seqAction=Actions.sequence(bigger, reset,endAction);
		Action all=Actions.parallel(seqAction, move);
		srcQz.addAction(all);
		
		if (pieceIndex[dst] != null) {
			pieceIndex[dst].setVisible(false);
			capturedArr.add(pieceIndex[dst]);
			pieceIndex[dst] = null;
//			Constant.sounds.get("capture").play();
		}
		pieceIndex[dst] = srcQz;
		
		pieceIndex[src] = null;
	}
	
	public void moveQizi(int src, int dst) {
		Qizi qz = pieceIndex[src];
		int dstX = dst / 10;
		int dstY = dst % 10;
		if (qz == null)
			return;
		if (moveQizi(qz, dstX, dstY)) {
			System.out.println("成功下棋！");
			changeLocation(qz, dstX, dstY);
			selectQz(qz);
			return;
		}
		System.out.println("下棋不成功！");
	}
	
//	public void decideFailer() {// send lose message
//		if(pos.inCheck()){
//			Constant.mSender.showInfo("将军");
//		}
//		
//		if(pos.isMate()&&isMyPlace){
//			if (isMyTurn()){
//				gameEnd(false);
//				Constant.mSender.showInfo("你输了！");
//				sendResult(-1);
//			}else{
//				gameEnd(true);
//				Constant.mSender.showInfo("你赢了！");
//				sendResult(1);
//				
//			}
//			return;
//		}
//		
//		int vlRep = pos.repStatus(3);
//		if (vlRep > 0) {
//			vlRep = (!isMyTurn() ? pos.repValue(vlRep) : -pos.repValue(vlRep));
//			int rst = (vlRep > Position.WIN_VALUE ? -1 :
//				vlRep < -Position.WIN_VALUE ? 1 : 0);
//			String message = (vlRep > Position.WIN_VALUE ? "长打作负，请不要气馁！" :
//					vlRep < -Position.WIN_VALUE ? "电脑长打作负，祝贺你取得胜利！" : "双方不变作和，辛苦了！");
//			gameEnd(rst>0);
//			Constant.mSender.showInfo(message);
//			sendResult(rst);
//			return;
//		}
//		if (pos.mvList.size() > 100) {
//			String message = "超过自然限着作和，辛苦了！";
//			gameEnd(false);
//			Constant.mSender.showInfo(message);
//			sendResult(0);
//			return;
//		}
//		
//	}
	
	private void sendResult(int rst){
		if(!isLocal()){
			PackageInfo pi=new PackageInfo();
			pi.setApp(Constant.APP);
			pi.setFrom(Constant.me.getCid());
			pi.setType(rst>0?Constant.MSG_LOSE:(rst<0?Constant.MSG_WIN:Constant.MSG_PING));
			pi.setTo(Constant.enamy.getRoomid());
			pi.setMsg(rst>0?"你输了":(rst<0?"你赢了！":"平局"));
			Constant.mSender.writeMessage(JSONUtil.toJosn(pi));
		}
	}
	
//	public void computerMove(){
//		Thread t=new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				System.out.println("电脑下棋");
//				computerIsThingking = true;
//				long cur=System.currentTimeMillis();
//				int mvLast=search.searchMain(1000 << (level << 1));
//				System.out.println("cost time "+((System.currentTimeMillis()-cur)));
//				int srcx=Position.FILE_X(Position.SRC(mvLast))-Position.FILE_LEFT;
//				int srcy=Position.RANK_Y(Position.SRC(mvLast))-Position.RANK_TOP;
//				int dstx=Position.FILE_X(Position.DST(mvLast))-Position.FILE_LEFT;
//				int dsty=Position.RANK_Y(Position.DST(mvLast))-Position.RANK_TOP;
//				final int src=srcx*10+srcy;
//				final int dst=dstx*10+dsty;
//				Gdx.app.postRunnable(new Runnable() {
//					
//					@Override
//					public void run() {
//						
//						moveQizi(src, dst);
//						setMyTurn(true);
////						decideFailer();
//						computerIsThingking = false;
//					}
//				});
//				
//				
//			}
//		});
//		t.setDaemon(true);
//		t.setPriority(Thread.MAX_PRIORITY);
//		t.start();
//		
//	}
	
	public Qizi getLastSelected() {
		return lastSelected;
	}

	
	private void flushTimeLabel(){
		if(myTurn){
			myTime++;
			
		}else{
			hisTime++;
		}
		myThinking.setVisible(myTurn);
		hisThinking.setVisible(!myTurn);
		setTime(myTimer,myTime,160,62,true);
		setTime(hisTimer,hisTime,160,1102,false);
		
	}
	
	private void setTime(Label timeLabel,long time,float x,float y,boolean my){
		if(null==timeLabel){
			String all="小时分秒1234567890";
			String timeStr=StringUtil.getTimeStr(time);
			BitmapFont slFont=Constant.getFont(all);
			slFont.setColor(0.9f, 0.3f, 0.6f, 1);
			slFont.setScale(1.2f);
			LabelStyle ls=new LabelStyle(slFont, slFont.getColor());
			
			timeLabel=new Label(timeStr, ls);
			timeLabel.setSize(235, 35);
			timeLabel.setPosition(x, y);
			addActor(timeLabel);
			slFont.dispose();
			if(my){
				myTimer=timeLabel;
			}else{
				hisTimer=timeLabel;
			}
		}else{
			String timeStr=StringUtil.getTimeStr(time);
			timeLabel.setText(timeStr);
		}
	}
	
	@Override
	public void draw() {
		if(isrunning){
			totalTime+=Gdx.graphics.getDeltaTime();
			if(totalTime>=1){
				flushTimeLabel();
				totalTime-=1;
			}
		}
		super.draw();
	}



	private class QiziSelection extends InputListener{

		@Override
		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			if(computerIsThingking||!isrunning){
				return true;
			}
			box.setVisible(false);
			Qizi tmpQizi=(Qizi) event.getTarget();
			if(null==getLastSelected()){
				selectQz(tmpQizi);
			}else{
				int x1 = (int) tmpQizi.getCoordinateX();
				int y1 = (int) tmpQizi.getCoordinateY();
				int srcX=(int) getLastSelected().getCoordinateX();
				int srcY=(int) getLastSelected().getCoordinateY();
				int src = srcX * 10 + srcY;
				int dest = x1 * 10 + y1;
				Map<String, Object> cmd = new HashMap<String, Object>();
				cmd.put("cmd", Constant.MSG_XIAQI);
				cmd.put("src", src);
				cmd.put("dest", dest);
				PackageInfo pi=new PackageInfo();
				pi.setApp(Constant.APP);
				pi.setFrom(Constant.me.getCid());
				pi.setType(Constant.MSG_ACTION);
				pi.setTo(Constant.enamy.getRoomid());
				pi.setMsg(JSONUtil.toJosn(cmd));
				Constant.mSender.writeMessage(JSONUtil.toJosn(pi));
				System.out.println(src + "  " + dest);
			}
			return super.touchDown(event, x, y, pointer, button);
		}
		
	}
}
