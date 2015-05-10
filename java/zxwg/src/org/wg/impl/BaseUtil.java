package org.wg.impl;

import java.util.Date;
import java.util.List;

import org.wg.core.Monster;
import org.wg.core.Role;

public class BaseUtil {
	/**
	 * 查找是否存在指定怪物名称。
	 * @param role
	 * @param monsterNames
	 * @return
	 */
	public static boolean isExistMonsterByName(Role role, List<String> monsterNameLst,int type){
		List<Monster> monLst = role.getMonsters();
		boolean isExist = false;
		int curX = (int) role.getX();
		int curY = (int) role.getY();
		for(Monster mon:monLst){
			String monName = mon.getName();
			if(type==mon.getType()){
				int wgX = (int)(mon.getX());
				int wgY = (int)(mon.getY());
				int xNewCha = Math.abs(wgX-curX);
				int yNewCha = Math.abs(wgY-curY);
				if(xNewCha>18 || yNewCha>18){
					continue;
				}
				//判断是否存在指定怪物。
				for(String bossName:monsterNameLst){
					if(monName.indexOf(bossName)>-1){
						isExist = true;
						break;
					}
				}
				
			}
		}
		return isExist;
	}
	
	/**
	 * 判断死亡并且回城。
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	public static boolean isDeadAndBack(Role role) throws Exception{
		int curHp=role.getHp();
		
		if(curHp==0){
			role.swhc();
			for(int i=0;i<40;i++){
				int newHp = role.getHp();
				if(newHp==0){
					Thread.sleep(1000);
				}else{
					break;
				}
			}
			role.trhy(true);
			Thread.sleep(2000);
			role.trhy(false);
			Thread.sleep(2000);
			
		}else{
			return false;
		}
		return true;
	}
	
	/**
	 * 返回
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	public static void goBack(Role role, List<int[]> backLst) throws Exception{
		int iSize = backLst.size();
		int zlNum = 2000;
		int zbIndex = iSize-1;
		do{
			int[] tarXY = backLst.get(zbIndex);
			boolean isOver = false;
			int tarX = tarXY[0];
			int tarY = tarXY[1];
			for(int i=0;i<zlNum;i++){
				float curX = role.getX();
				float curY = role.getY();
				role.zoulu(tarX,tarY);
				for(int j=0;j<2;j++){
					Thread.sleep(100);
				}
				float xNewCha = Math.abs(tarX-curX);
				float yNewCha = Math.abs(tarY-curY);
				if(xNewCha<4&&yNewCha<4){
					isOver = true;
					break;
				}							
			}
			if(isOver){
				zbIndex = zbIndex-1;
			}
		}while(zbIndex>-1);
	}
	
	/**
	 * 返回
	 * @param role
	 * @return
	 * @throws Exception 
	 */
	public static void goToTarField(Role role, List<int[]> backLst, List<int[]> tarLst) throws Exception{
		int iSize = tarLst.size();
		int zlNum = 2000;
		int zbIndex = 0;
		float lastX = role.getX();
		float lastY = role.getY();
		long oldSec = new Date().getTime();
		do{
			int[] tarXY = tarLst.get(zbIndex);
			boolean isOver = false;
			int tarX = tarXY[0];
			int tarY = tarXY[1];
			//保存第一个数据。
			rememberBackArray(role,backLst);
			long newSec = new Date().getTime();
			if(newSec-oldSec>60000){
				break;
			}
			int backCount = 0;
			for(int i=0;i<zlNum;i++){
				newSec = new Date().getTime();
				if(newSec-oldSec>60000){
					break;
				}
				float curX = role.getX();
				float curY = role.getY();
				if(lastX ==curX && lastY == curY){
					lastX = role.getX();
					lastY = role.getY();
					backCount = backCount +1;
				}else{
					backCount = 0;
				}
				role.zoulu(tarX,tarY);
				for(int j=0;j<10;j++){
					Thread.sleep(400);
					//如果无需记录，则不需要调用记录函数。
					rememberBackArray(role,backLst);
					float xNewCha = Math.abs(tarX-curX);
					float yNewCha = Math.abs(tarY-curY);
					if(xNewCha<4&&yNewCha<4){
						isOver = true;
						break;
					}
				}
				if(isOver){
					break;
				}
			}
			if(isOver){
				zbIndex = zbIndex+1;
			}
		}while(zbIndex<iSize);
	}
	
	/**
	 * 记录返回坐标。
	 * @param role
	 * @param backLst
	 */
	public static void rememberBackArray(Role role, List<int[]> backLst){
		int newX = (int) role.getX();
		int newY = (int) role.getY();
		boolean isExist = false;
		for(int[] iXY:backLst){
			if(iXY[0]==newX && iXY[1]==newY) {
				isExist = true;
			}
		}
		if (isExist){
			backLst.add(new int[]{newX,newY});
		}
	}
	
	/**
	 * 获取NPCuid
	 * @param role
	 * @param npcName
	 * @return
	 */
	public static int getNpcUID(Role role,String npcName){
	
		int npcUID = 0;
		List<Monster> monLst = role.getMonsters();
		for(Monster mon:monLst){
			String monName = mon.getName();
			//必须是NPC
			if(7==mon.getType()){
				//判断是否存在指定怪物。
				if(monName.startsWith(npcName)){
					npcUID = mon.getUid();
					break;
				}
			}
		}
		return npcUID;
	}

	
}
