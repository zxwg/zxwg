package org.wg.core;

public class Kernel {
	static {
		System.loadLibrary("zxwgdll");
	}

	/** ---------------------------ϵͳAPI----------------------- */
	/**
	 * ��ȡ�����ڴ�
	 * 
	 * @param pid
	 * @param address
	 * @return
	 */
	public native static int readMemoryInt(int pid, int address);

	/**
	 * ��ȡ�ڴ��ֽ�
	 * 
	 * @param pid
	 * @param address
	 * @param length
	 * @return
	 */
	public native static byte[] readMemoryBytes(int pid, int address, int length);

	/**
	 * ��ȡ�ڴ��ַ���
	 * 
	 * @param pid
	 * @param address
	 * @param length
	 * @return
	 */
	public native static String readMemoryStr(int pid, int address);

	/**
	 * ��ȡ�ڴ渡��
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
	 * ת��16����byte�ַ���Ϊbyte[]
	 * 
	 * @param hexString
	 * @return
	 */
	final static public byte[] getHexToBytes(final String hexString) {
		return getHexToBytes(hexString.getBytes(), 0, hexString.length() >> 1);
	}

	/**
	 * ת��16����byte�ַ���Ϊbyte[]
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

	/** --------------------------����API ------------------------------ **/
	/**
	 * �����·
	 * 
	 * @param pid
	 * @param adress
	 * @return
	 */
	public native static boolean zoulu(int pid, int x, int y);

	/**
	 * ����
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
	 * ִ��lua
	 * 
	 * @param pid
	 * @param filePaht
	 * @return
	 */
	public native static boolean lua(int pid, String filePaht);

}
