package com.xk.chinesechess.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	public static byte[] toBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bytes=new byte[20480];
		int length=0;
		while ((length=in.read(bytes, 0, bytes.length))>0 ) {
			out.write(bytes,0,length);
		}
		byte buffer[] = out.toByteArray();
		out.close();
		return buffer;
	}
	
	public static byte[] toBytes(File file) throws FileNotFoundException, IOException{
		return toBytes(new FileInputStream(file));
	}
}
