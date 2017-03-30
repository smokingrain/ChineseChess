/**
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
                                 ���汣��                                      ����BUG
 * @author xiaokui
 * @�汾 ��v1.0
 * @ʱ�䣺2015-6-11����11:25:39
 */
package com.xk.chinesechess.font;

/**
 * @��Ŀ���ƣ�Gobang
 * @�����ƣ�FreePaint.java
 * @��������
 * @�����ˣ�xiaokui
 * ʱ�䣺2015-6-11����11:25:39
 */
import com.badlogic.gdx.graphics.Color;

public class FreePaint {
	private int textSize = 30;// �ֺ�
	private Color color = Color.WHITE;// ��ɫ
	private boolean isFakeBoldText = false;// �Ƿ����
	private boolean isUnderlineText = false;// �Ƿ��»���
	private boolean isStrikeThruText = false;// �Ƿ�ɾ����
	private Color strokeColor = null;// �����ɫ
	private int strokeWidth = 3;// ��߿��

	public String getName() {
		StringBuffer name = new StringBuffer();
		name.append(textSize);
		name.append("_");
		name.append(color.toIntBits());
		name.append("_");
		name.append(booleanToInt(isFakeBoldText));
		name.append("_");
		name.append(booleanToInt(isUnderlineText));
		if (strokeColor != null) {
			name.append("_");
			name.append(strokeColor.toIntBits());
			name.append("_");
			name.append(strokeWidth);
		}
		return name.toString();
	}

	private int booleanToInt(boolean b) {
		return b == true ? 0 : 1;
	}

	public FreePaint() {
	}

	public FreePaint(int textSize, Color color, Color stroke, int strokeWidth,
			boolean bold, boolean line, boolean thru) {
		this.textSize = textSize;
		this.color = color;
		this.strokeColor = stroke;
		this.strokeWidth = strokeWidth;
		this.isFakeBoldText = bold;
		this.isUnderlineText = line;
		this.isStrikeThruText = thru;
	}

	public FreePaint(int size) {
		this.textSize = size;
	}

	public FreePaint(Color color) {
		this.color = color;
	}

	public FreePaint(int size, Color color) {
		this.textSize = size;
		this.color = color;
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean getFakeBoldText() {
		return isFakeBoldText;
	}

	public void setFakeBoldText(boolean isFakeBoldText) {
		this.isFakeBoldText = isFakeBoldText;
	}

	public boolean getUnderlineText() {
		return isUnderlineText;
	}

	public void setUnderlineText(boolean isUnderlineText) {
		this.isUnderlineText = isUnderlineText;
	}

	public boolean getStrikeThruText() {
		return isStrikeThruText;
	}

	public void setStrikeThruText(boolean isStrikeThruText) {
		this.isStrikeThruText = isStrikeThruText;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public int getStrokeWidth() {
		return strokeWidth;
	}

	public void setStrokeWidth(int strokeWidth) {
		this.strokeWidth = strokeWidth;
	}
}
