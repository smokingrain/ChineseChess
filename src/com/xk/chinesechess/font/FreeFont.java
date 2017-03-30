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
 * @ʱ�䣺2015-6-11����11:24:59
 */
package com.xk.chinesechess.font;

/**
 * @��Ŀ���ƣ�Gobang
 * @�����ƣ�FreeFont.java
 * @��������
 * @�����ˣ�xiaokui
 * ʱ�䣺2015-6-11����11:24:59
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PixmapPacker.Page;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.xk.chinesechess.message.MessageSender;

/**
 * 
 * @author Var3D
 * 
 *         ���飺 1.�����ܵĹ���ʹ��BitmapFont�����Դ�������Ҫ�������ַ�����������Ҫ��ͬ��ɫ���ַ���
 *         ��������һ����ɫ�����BitmapFont ����ͨ���ı�Label��colorʵ���ַ��Ķ�ʻ���
 *         2.�����Ҫ��ͬ�������С������ֺ����� ���Կ��ǹ���ʹ���ֺŸ���ŵ�BitmapFont
 *         ��Ȼ��ͨ������label.setFontScale�ķ�ʽʵ���ı��Ĳ�ͬ��С������ֺ������࣬���ŵ�Ч�����ܻ��
 *         3.�����Ҫʹ���Զ�����飬���������Ϲ��ɡ� 4.VPaint�Ĳ�����ͬ����Ϊ��ͬһ��BitmapFont
 */
public class FreeFont implements Disposable {
	private int pageWidth = 512;
	private MessageSender listener;
	private Pixmap map;
	
	
	public FreeFont(MessageSender listener) {
		this.listener = listener;
	}

	/**
	 * �Զ���������
	 */
	private HashMap<String, String> emojis = new HashMap<String, String>();

	public void addEmoji(String str, String imgname) {
		emojis.put(str, imgname);
	}

	/**
	 * ����Ĭ��BitmapFont
	 */
	public BitmapFont getFont(String characters) {
		return getFont(new FreePaint(), characters);
	}

	/**
	 * ����һ��BitmapFont
	 * Vpaint������ͬ����Ϊ��ͬһ��BitmapFont�����vpaint�������BitmapFont�Ѿ����ڣ�����������������ַ�
	 * 
	 * @param vpaint
	 * @param characters
	 * @return
	 */
	public BitmapFont getFont(FreePaint vpaint, String characters) {
		BitmapFont font = createBitmapFont(vpaint, characters.length() == 0 ? "l"
				: characters);
		return font;
	}

	private void setGlyph(String txt, int c, Rectangle rect, 
			BitmapFontData data) {
		Glyph glyph = new Glyph();
		glyph.srcX = (int) rect.x;
		glyph.srcY = (int) rect.y;
		glyph.width = (int) rect.width;
		glyph.height = (int) rect.height;
		glyph.xadvance = glyph.width;
		data.setGlyph(c, glyph);
	}

	/**
	 * �����ַ�
	 */
	private BitmapFont createBitmapFont(FreePaint vpaint, String characters) {
		FreeFontParameter parameter = new FreeFontParameter();
		parameter.size = vpaint.getTextSize();
		parameter.packer = new PixmapPacker(pageWidth, pageWidth,
				Format.RGBA8888, 2, false);
		char[] cs = characters.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			parameter.charSet.add(cs[i] + "");
		}
		PixmapPacker packer = parameter.packer;
		FreeData data = new FreeData();
		data.down = -parameter.size;
		data.ascent = -parameter.size;
		data.capHeight = parameter.size;
		String packPrefix = parameter.size + "_";
		Iterator<String> it = parameter.charSet.iterator();
		while (it.hasNext()) {
			String txt = it.next();
			String emj = emojis.get(txt);
			Pixmap pixmap = null;
			if (emj == null) {
				pixmap = listener.getFontPixmap(txt, vpaint);
			} else {
				pixmap = new Pixmap(Gdx.files.internal(emj));
			}
			int c = txt.charAt(0);
			String name = packPrefix + c;
			Rectangle rect = packer.pack(name, pixmap);
			pixmap.dispose();
			setGlyph(txt, c, rect, data);
		}
		Glyph spaceGlyph = data.getGlyph(' ');
		if (spaceGlyph == null) {
			spaceGlyph = new Glyph();
			Glyph xadvanceGlyph = data.getGlyph('l');
			if (xadvanceGlyph == null)
				xadvanceGlyph = data.getFirstGlyph();
			spaceGlyph.xadvance = xadvanceGlyph.xadvance;
			data.setGlyph(' ', spaceGlyph);
		}
		data.spaceWidth = spaceGlyph != null ? spaceGlyph.xadvance
				+ spaceGlyph.width : 1;
		data.lineHeight=38;
		Pixmap pix=packer.getPages().get(0).getPixmap();
		map=new Pixmap(pix.getWidth(),pix.getHeight(),Pixmap.Format.RGBA8888);
		map.drawPixmap(pix, 0, 0);
		pix.dispose();
		Texture texture=new Texture(map);
        data.region = new TextureRegion(texture);
		return new BitmapFont(data, data.getTextureRegion(), false);
	}



	public static class FreeData extends BitmapFontData {
		TextureRegion region = new TextureRegion();

		public TextureRegion getTextureRegion() {
			return region;
		}
	}

	public void dispose() {
		if(null!=map){
			map.dispose();
		}
	}

	public static class FreeFontParameter {
		/** The size in pixels */
		public int size = 30;
		/** The characters the font should contain */
		// public String characters = "l";
		public Set<String> charSet = new HashSet<String>();
		/** The optional PixmapPacker to use */
		public PixmapPacker packer = null;
		/** Whether to flip the font horizontally */
		public boolean flip = false;
		/** Whether or not to generate mip maps for the resulting texture */
		public boolean genMipMaps = false;
		/** Minification filter */
		public TextureFilter minFilter = TextureFilter.Linear;
		/** Magnification filter */
		public TextureFilter magFilter = TextureFilter.Linear;
	}
}
