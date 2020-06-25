package com.chow;

public class Util {
    /**
     * 将字节数组数据转换为16进制字符串
     * @param src 字节数组
     * @return
     */
	public static String bytesToHexString(byte[] src){  
		//byte[] src = reverseBytes(src1);
		StringBuilder stringBuilder = new StringBuilder("");  
		if (src == null || src.length <= 0) {  
			return null;  
		}  
		for (int i = 0; i < src.length; i++) {  
			int v = src[i] & 0xFF;  
			String hv = Integer.toHexString(v);  
			if (hv.length() < 2) {  
				stringBuilder.append(0);  
			}  
			stringBuilder.append(hv+" ");  
		}  
		return stringBuilder.toString();  
	}

	/**
	 * 拷贝二进制数据
	 * @param src 源数据
	 * @param start 开始位置
	 * @param len 拷贝长度
	 * @return
	 */
	public static byte[] copyByte(byte[] src, int start, int len){
		if(src == null) return null;
		if(start > src.length) return null;
		if((start + len) > src.length) return null;
		if(start < 0) return null;
		if(len <= 0) return null;
		byte[] resultByte = new byte[len];
		for(int i = 0;i < len;i++){
			resultByte[i] = src[i + start];
		}
		return resultByte;
	}
}
