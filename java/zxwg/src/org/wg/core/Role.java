package org.wg.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

public class Role extends MemoryObject {

	public Role(int pid) {
		super(pid, 0);
		this.address = $($($(AddressConstants.baseAdress) + 0x1C) + 0x2c);
	}

	public int getHp() {
		return $(address + 0x290);
	}

	public int getMp() {
		return $(address + 0x294);
	}

	public int getMaxHp() {
		return $(address + 0x2BC);
	}

	public int getMaxMp() {
		return $(address + 0x2C0);
	}

	public float getX() {
		return $f(address + 0x644);
	}

	public float getY() {
		return $f(address + 0x64C);
	}

	public String getName() {
		return $s($(address + 0x5F8)+0);
	}

	public boolean sendPack(String str) {
		return Kernel.sendPack(pid, str);
	}

	public boolean swhc() {
		logger.log(Level.DEBUG, "死亡回城");
		return Kernel.swhc(pid);
	}

	public boolean jrw(int uid) {
		logger.log(Level.DEBUG, "接任务 uid:" + uid);
		return Kernel.jrw(pid, uid);
	}

	public boolean jhnpc(int uid) {
		logger.log(Level.DEBUG, "激活NPC uid:" + uid);
		return Kernel.jhnpc(pid, uid);
	}

	public boolean closeNpc() {
		logger.log(Level.DEBUG, "关闭NPC");
		return Kernel.closeNpc(pid);
	}
	
	public boolean trhy(boolean flag) {
		logger.log(Level.DEBUG, "天人合一 :" + flag);
		return Kernel.trhy(pid, flag ? 1 : 0);
	}

	public boolean xinpan(int pagnum, int num) {
		logger.log(Level.DEBUG, "使用星盘 bagnum:" + pagnum + " num:" + num);
		return Kernel.xinpan(pid, pagnum, num);
	}

	public boolean zoulu(int x, int y) {
		logger.log(Level.DEBUG, "走路 x:" + x + " y:" + y);
		return Kernel.zoulu(pid, x, y);
	}

	public int getWPNum() {
		return $($($($($(0xCF7E64) + 0x1C) + 0x08) + 0x2C) + 0x14);
	}

	public List<Monster> getMonsters() {
		List<Monster> monsters = new ArrayList<Monster>();
		int address = $($($($(AddressConstants.baseAdress) + 0x1c) + 0x8) + 0x28);
		int num = $(address + 0x14);
		address = $(address + 0x50);
		for (int i = 0; i < num; i++) {
			int objAddress = $(address + (i * 4));
			Monster objMonster = new Monster(pid, objAddress);
			monsters.add(objMonster);
		}
		return monsters;
	}
}
