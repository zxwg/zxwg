package org.wg.core;

public class Kernel {
	static {
		System.loadLibrary("zxwgdll");
	}

	/** ---------------------------系统API----------------------- */
	/**
	 * 读取整形内存
	 * 
	 * @param pid
	 * @param address
	 * @return
	 */
	public native static int readMemoryInt(int pid, int address);

	/**
	 * 读取内存字节
	 * 
	 * @param pid
	 * @param address
	 * @param length
	 * @return
	 */
	public native static byte[] readMemoryBytes(int pid, int address, int length);

	/**
	 * 读取内存字符串
	 * 
	 * @param pid
	 * @param address
	 * @param length
	 * @return
	 */
	public native static String readMemoryStr(int pid, int address);

	/**
	 * 读取内存浮点
	 * 
	 * @param pid
	 * @param address
	 * @return
	 */
	public native static float readMemoryFloat(int pid, int address);

	public static float $f(int pid, int address) {
		return readMemoryFloat(pid, address);
	}

	/**
	 * 转换16进制byte字符串为byte[]
	 * 
	 * @param hexString
	 * @return
	 */
	final static public byte[] getHexToBytes(final String hexString) {
		return getHexToBytes(hexString.getBytes(), 0, hexString.length() >> 1);
	}

	/**
	 * 转换16进制byte字符串为byte[]
	 * 
	 * @param b
	 * @param offset
	 * @param len
	 * @return
	 */
	final static public byte[] getHexToBytes(final byte[] bytes,
			final int offset, final int len) {
		byte[] buffer = new byte[len];
		for (int i = 0; i < len * 2; i++) {
			int shift = i % 2 == 1 ? 0 : 4;
			buffer[i >> 1] |= Character.digit((char) bytes[offset + i], 16) << shift;
		}
		return buffer;
	}

	/** --------------------------诛仙API ------------------------------ **/
	/**
	 * 鼠标走路
	 * 
	 * @param pid
	 * @param adress
	 * @return
	 */
	public native static boolean zoulu(int pid, int x, int y);

	/**
	 * 发包
	 * 
	 * @param pid
	 * @param adress
	 * @return
	 */
	private native static boolean sendPack(int pid, byte[] bytes);

	public native static boolean swhc(int pid);

	public native static boolean xinpan(int pid, int bag, int num);

	public native static boolean jhnpc(int pid, int uid);

	public native static boolean trhy(int pid, int i);

	public native static boolean jrw(int pid, int uid);

	public native static boolean closeNpc(int pid);

	
	public static boolean sendPack(int pid, String str) {
		return sendPack(pid, getHexToBytes(str));
	}

	/**
	 * 执行lua
	 * 
	 * @param pid
	 * @param filePaht
	 * @return
	 */
	public native static boolean lua(int pid, String filePaht);

}
