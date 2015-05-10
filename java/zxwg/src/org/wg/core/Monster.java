package org.wg.core;

public class Monster extends MemoryObject {
	private String name;
	private int type;
	private int uid;
	private int tid;
	private float x;
	private float y;
	private float juli;

	public Monster(int pid, int address) {
		super(pid, address);
		this.type = $(address + 0xB4);
		this.uid = $(address + 0x11c);
		this.tid = $(address + 0x120);
		this.name = $s($(address + 0x2CC) + 0);
		this.x = $f(address + 0x314);
		this.y = $f(address + 0x31C);
		this.juli = $f(address + 0x2FC);
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		x = $f(address + 0x314);
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		y = $f(address + 0x31C);
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getJuli() {
		juli = $f(address + 0x2FC);
		return juli;
	}

	public void setJuli(float juli) {
		this.juli = juli;
	}

}
