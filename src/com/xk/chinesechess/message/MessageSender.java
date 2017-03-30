package com.xk.chinesechess.message;

import com.badlogic.gdx.graphics.Pixmap;
import com.xk.chinesechess.font.FreePaint;

public interface MessageSender {
	public void showInfo(String info);
	public boolean isAndroid();
	public String getMyIp();
	public void writeMessage(String info);
	public void openWindow(String title,String message,WindowCallback callback);
	public void openMask(String message);
	public void closeMask();
	public Pixmap getFontPixmap(String txt, FreePaint vpaint);
	public void saveData(String name,String value);
	public void saveData(String name,Integer value);
}
