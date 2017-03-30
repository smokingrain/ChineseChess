package com.xk.chinesechess.stage;

import com.xk.chinesechess.zujian.Qizi;

public interface MainStage {
	public boolean isIsrunning();
	public boolean isMyTurn();
	public Qizi getLastSelected();
	public boolean moveQizi(Qizi q,int x,int y);
	public void changeLocation(Qizi q,int x,int y);
	public void setMyTurn(boolean turn);
	public boolean isLocal();
	public void computerMove();
	public void decideFailer();
}
