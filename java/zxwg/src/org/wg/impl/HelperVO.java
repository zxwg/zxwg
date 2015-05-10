package org.wg.impl;

import java.util.List;

import org.wg.core.Role;

public class HelperVO {
	/**基础类对象*/
	private Role role;
	/**任务ID集合*/
	private List<Integer> taskLst;
	/**boss怪物字符串*/
	private List<String> monsterNameLst;
	/**查询无限循环，进行自动打boss。每次是20s，新区老区看装备进行修改。*/
	private int queryMonsterNum;
	/**boss怪物坐标点集合*/
	private List<List<int[]>> tarList;
	/**打怪时容错防护坐标*/
	private List<int[]> backLst;
	/**激活任务NPC名称*/
	private String npcName;
	/**星盘数量*/
	private int xpNum;
	/**使用物品第N个包裹中的星盘,直接飞到所需接任务NPC旁边。*/
	private int npcToXP;	
	/**每个boss点最大死亡次数，只限于星盘挂使用*/
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
