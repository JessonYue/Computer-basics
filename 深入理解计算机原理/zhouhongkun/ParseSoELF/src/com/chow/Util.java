package com.chow;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Util {
	
	/**
	 * 将4位字节数组转换为整型
	 * @param bytes 二进制数据
	 * @return
	 */
	public static int byte2int(byte[] bytes) { 
		return (bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00) | ((bytes[2] << 24) >>> 8) | (bytes[3] << 24);  
	}
	
	/**
	 * 将整型转换为字节数组
	 * @param integer 整型
	 * @return
	 */
	public static byte[] int2Byte(final int integer) {
		int byteNum = (40 -Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer))/ 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer>>> (n * 8));

		return (byteArray);
	}
	
	/**
	 * 将short类型数据转换为字节数组
	 * @param number short类型
	 * @return
	 */
    public static byte[] short2Byte(short number) { 
        int temp = number; 
        byte[] b = new byte[2]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Integer(temp & 0xff).byteValue();//将最低位保存在最低位 
            temp = temp >> 8; // 向右移8位 
        } 
        return b; 
    } 
	
    /**
     * 将2位字节数组转换为short类型
     * @param b 字节数组
     * @return
     */
    public static short byte2Short(byte[] b) {
        short s = 0; 
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff); 
        s1 <<= 8; 
        s = (short) (s0 | s1); 
        return s; 
    }
	
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
	 * 将二进制数组数据转换为char类型数据
	 * @param bytes 二进制数据
	 * @return
	 */
	public static char[] getChars(byte[] bytes) {
		Charset cs = Charset.forName ("UTF-8");
		ByteBuffer bb = ByteBuffer.allocate (bytes.length);
		bb.put (bytes);
		bb.flip ();
		CharBuffer cb = cs.decode (bb);
		return cb.array();
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
	
	/**
	 * 反转二进制数据
	 * @param src 源二进制数据
	 * @return
	 */
	public static byte[] reverseBytes(byte[] src){
		byte[] bytes = new byte[src.length];
		for(int i=0;i<src.length;i++){
			bytes[i] = src[i];
		}
    	if(bytes == null || (bytes.length % 2) != 0){
    		return bytes;
    	}
    	int i = 0, len = bytes.length;
    	while(i < (len/2)){
    		byte tmp = bytes[i];
    		bytes[i] = bytes[len-i-1];
    		bytes[len-i-1] = tmp;
    		i++;
    	}
    	return bytes;
    }
	
	/**
	 * 过滤空字符串
	 * @param str 字符串
	 * @return
	 */
	public static String filterStringNull(String str){
		if(str == null || str.length() == 0){
			return str;
		}
		byte[] strByte = str.getBytes();
		ArrayList<Byte> newByte = new ArrayList<Byte>();
		for(int i=0;i<strByte.length;i++){
			if(strByte[i] != 0){
				newByte.add(strByte[i]);
			}
		}
		byte[] newByteAry = new byte[newByte.size()];
		for(int i=0;i<newByteAry.length;i++){
			newByteAry[i] = newByte.get(i);
		}
		return new String(newByteAry);
	}
	
	/**
	 * 从二进制数组的指定位置获取字符串
	 * @param srcByte 源二进制数据
	 * @param start 开始位置
	 * @return
	 */
	public static String getStringFromByteAry(byte[] srcByte, int start){
		if(srcByte == null) return "";
		if(start < 0) return "";
		if(start >= srcByte.length) return "";
		byte val = srcByte[start];
		int i = 1;
		ArrayList<Byte> byteList = new ArrayList<Byte>();
		while(val != 0){
			byteList.add(srcByte[start+i]);
			val = srcByte[start+i];
			i++;
		}
		byte[] valAry = new byte[byteList.size()];
		for(int j=0;j<byteList.size();j++){
			valAry[j] = byteList.get(j); 
		}
		try{
			return new String(valAry, "UTF-8");
		}catch(Exception e){
			System.out.println("encode error:"+e.toString());
			return "";
		}
	}
	
	/**
	 * 读取C语言中的uleb类型
	 * 目的是解决整型数值浪费问题
	 * 长度不固定，在1~5个字节中浮动
	 * @param srcByte 源二进制
	 * @param offset 偏移位置
	 * @return
	 */
	public static byte[] readUnsignedLeb128(byte[] srcByte, int offset){
		List<Byte> byteAryList = new ArrayList<Byte>();
		byte bytes = Util.copyByte(srcByte, offset, 1)[0];
		byte highBit = (byte)(bytes & 0x80);
		byteAryList.add(bytes);
		offset ++;
		while(highBit != 0){
			bytes = Util.copyByte(srcByte, offset, 1)[0];
			highBit = (byte)(bytes & 0x80);
			offset ++;
			byteAryList.add(bytes);
		}
		byte[] byteAry = new byte[byteAryList.size()];
		for(int j=0;j<byteAryList.size();j++){
			byteAry[j] = byteAryList.get(j);
		}
		return byteAry;
	}
	
	/**
	 * 解码leb128数据
	 * 每个字节去除最高位，然后进行拼接，重新构造一个int类型数值，从低位开始
	 * @param byteAry 二进制数据
	 * @return
	 */
	public static int decodeUleb128(byte[] byteAry) {
		byteAry = reverseBytes(byteAry);
		int index = 0, cur;
	    byte result = byteAry[index];
	    index++;
	    
	    if(byteAry.length == 1){
	    }
	    
	    if(byteAry.length == 2){
	    	cur = byteAry[index];
	        index++;
	        result = (byte) ((result & 0x7f) | ((cur & 0x7f) << 7));
	    }
	    
	    if(byteAry.length == 3){
	    	cur = byteAry[index];
	        index++;
	        int num = cur & 0x7f;
	        result |= (num << 14);
	    }
	    
	    if(byteAry.length == 4){
	    	cur = byteAry[index];
	        index++;
	        result |= ((cur & 0x7f) << 21);
	    }
        
        if(byteAry.length == 5){
        	cur = byteAry[index];
            index++;
            result |= cur << 28;
        }
        return result;
	}

}
