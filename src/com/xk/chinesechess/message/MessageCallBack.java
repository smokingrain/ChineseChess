package com.xk.chinesechess.message;


public interface MessageCallBack {
	/**
	 * @param info
	 * @return 消息是否处理完毕！
	 */
	public boolean callBack(PackageInfo info);
}
