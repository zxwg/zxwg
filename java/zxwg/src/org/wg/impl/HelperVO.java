package org.wg.impl;

import java.util.List;

import org.wg.core.Role;

public class HelperVO {
	/**���������*/
	private Role role;
	/**����ID����*/
	private List<Integer> taskLst;
	/**boss�����ַ���*/
	private List<String> monsterNameLst;
	/**��ѯ����ѭ���������Զ���boss��ÿ����20s������������װ�������޸ġ�*/
	private int queryMonsterNum;
	/**boss��������㼯��*/
	private List<List<int[]>> tarList;
	/**���ʱ�ݴ��������*/
	private List<int[]> backLst;
	/**��������NPC����*/
	private String npcName;
	/**��������*/
	private int xpNum;
	/**ʹ����Ʒ��N�������е�����,ֱ�ӷɵ����������NPC�Աߡ�*/
	private int npcToXP;	
	/**ÿ��boss���������������ֻ�������̹�ʹ��*/
	private int maxDeadNum;
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Integer> getTaskLst() {
		return taskLst;
	}
	public void setTaskLst(List<Integer> taskLst) {
		this.taskLst = taskLst;
	}

	public List<String> getMonsterNameLst() {
		return monsterNameLst;
	}
	public void setMonsterNameLst(List<String> monsterNameLst) {
		this.monsterNameLst = monsterNameLst;
	}
	public int getQueryMonsterNum() {
		return queryMonsterNum;
	}
	public void setQueryMonsterNum(int queryMonsterNum) {
		this.queryMonsterNum = queryMonsterNum;
	}
	public List<List<int[]>> getTarList() {
		return tarList;
	}
	public void setTarList(List<List<int[]>> tarList) {
		this.tarList = tarList;
	}
	public List<int[]> getBackLst() {
		return backLst;
	}
	public void setBackLst(List<int[]> backLst) {
		this.backLst = backLst;
	}
	public String getNpcName() {
		return npcName;
	}
	public void setNpcName(String npcName) {
		this.npcName = npcName;
	}
	public int getXpNum() {
		return xpNum;
	}
	public void setXpNum(int xpNum) {
		this.xpNum = xpNum;
	}
	public int getNpcToXP() {
		return npcToXP;
	}
	public void setNpcToXP(int npcToXP) {
		this.npcToXP = npcToXP;
	}
	public int getMaxDeadNum() {
		return maxDeadNum;
	}
	public void setMaxDeadNum(int maxDeadNum) {
		this.maxDeadNum = maxDeadNum;
	}
	

}
