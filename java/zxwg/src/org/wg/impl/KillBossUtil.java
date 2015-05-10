package org.wg.impl;

import java.util.List;

import org.wg.core.Role;

public class KillBossUtil {
	/**
	 * 星盘挂机打BOSS
	 * @param helperVO
	 * @throws Exception
	 */
	public static void xpGuaji(HelperVO helperVO) throws Exception{

		Role role = helperVO.getRole();
		List<String> monsterNameLst = helperVO.getMonsterNameLst();
		int xpNum = helperVO.getXpNum(); 
		int queryMonsterNum = helperVO.getQueryMonsterNum();
		int maxDeadNum  = helperVO.getMaxDeadNum();
		//星盘目标点个数
		int dotOfXP = 8;
		do   
		 for(int i=0;i<xpNum;i++){
				//星盘点循环索引
				int dotIndex = 0;
				//每点最大死亡次数
				int maxDeadIndex = 0;
				do{
					Thread.sleep(1000);
					//若死亡则回城。
					boolean isDel = BaseUtil.isDeadAndBack(role);
					//如果没死，则取消天人合一。
					if (!isDel){
						role.trhy(false);
						Thread.sleep(1000);
					} 
					System.out.println("星盘点：" + dotIndex);
					//使用星盘
					role.xinpan(i,dotIndex);
					//获取当前人物x，y坐标
					int curX = (int) role.getX();
					int curY = (int) role.getY();
					Thread.sleep(3000);
					//循环40次判断是否当前人物坐标发生变化，如果未发生变化，则继续，20s内肯定有所变化。
					for(int j=0;j<40;j++){
						int newX = (int) role.getX();
						int newY = (int) role.getY();
						if (newX==curX && newY==curY){
							Thread.sleep(1000);
						}else{
							break;
						}
					}
					//开始天人合一
					role.trhy(true);
					//多次循环判断是否有怪物及是否死亡，如果怪物不存在，则进入下一个点，如果怪物在，则判断人物是否死亡
					for(int m=0;m<queryMonsterNum;m++) {
						boolean isDead = false;
						boolean isExist = BaseUtil.isExistMonsterByName(role, monsterNameLst,6);
						if (isExist){
							//每一秒都判断是否死亡。死亡则从新开始进入循环。没死亡则延时。
							for(int x=0;x<20;x++){ 
								int hp=role.getHp();
								if (hp > 0) {
									Thread.sleep(1000);
								}else{
									maxDeadIndex = maxDeadIndex + 1;
									isDead = true;
									break;
								}
							}
							//如果途中死亡，则退出整个查找怪物循环。
							if (isDead){
								break;
							}
							//如果已经达到最大死亡次数，则进入下一个点循环。
							if (maxDeadIndex>=maxDeadNum){
								dotIndex = dotIndex + 1;
								break;
							}
						}else{
							dotIndex = dotIndex + 1;
							break;
						}
					}
				}while( dotIndex <= dotOfXP);
			 
		 }
		while(true);
	}
	/**
	 * 寻路挂机打BOSS
	 * @param helperVO
	 * @throws Exception
	 */
	public static void zlGuaJi(HelperVO helperVO) throws Exception{
		Role role = helperVO.getRole();
		List<String> monsterNameLst = helperVO.getMonsterNameLst();
		int queryMonsterNum = helperVO.getQueryMonsterNum();
		List<List<int[]>> tarList = helperVO.getTarList();
		List<int[]> backLst = helperVO.getBackLst();
		for(List<int[]> tarXY:tarList){
			Thread.sleep(1000);
			//若死亡则回城。
			boolean isDel = BaseUtil.isDeadAndBack(role);
			//如果没死，则取消天人合一。
			if(!isDel) {
				role.trhy(false);
				Thread.sleep(1000);
			}else{
				break;
			} 
			BaseUtil.goToTarField(role, backLst, tarXY);;
			int hp=role.getHp();
			if (hp == 0){
				break;
			}
			//多次循环判断是否有怪物及是否死亡，如果怪物不存在，则进入下一个点，如果怪物在，则判断人物是否死亡
			for(int m=0;m<queryMonsterNum;m++) {
				boolean isDead = false;
				boolean isExist = BaseUtil.isExistMonsterByName(role, monsterNameLst,6);
				if (isExist){
					//开始天人合一
					role.trhy(true);
					//每一秒都判断是否死亡。死亡则从新开始进入循环。没死亡则延时。
					for(int x=0;x<20;x++){ 
						hp=role.getHp();
						if (hp > 0) {
							Thread.sleep(1000);
						}else{
							isDead = true;
							break;
						}
					}
					//如果途中死亡，则退出整个查找怪物循环。
					if (isDead){
						break;
					}

				}else{
					break;
				}
			}
		}
	}
	
	/**
	 * 寻路挂机打BOSS
	 * @param helperVO
	 * @throws Exception
	 */
	public static void zlGuajiAll(HelperVO helperVO) throws Exception{
		Role role = helperVO.getRole();
		String npcName = helperVO.getNpcName();
		int npcToXP = helperVO.getNpcToXP();
		List<Integer> taskLst = helperVO.getTaskLst();
		do{
			for(int tarkId:taskLst){
				role.trhy(false);
				//使用物品第三个包裹中的星盘,直接飞到NPC旁边。
				role.xinpan(npcToXP-1,0);
				Thread.sleep(5000);
				
				int npcUID = BaseUtil.getNpcUID(role, npcName);
				if (npcUID !=0){
					role.jhnpc(npcUID);
					Thread.sleep(5000);
					role.jrw(tarkId);
					Thread.sleep(12000);
					//按ESC键关闭窗口。
					role.closeNpc();
				}
				
				zlGuaJi(helperVO);
			}
		}while(true);
	}
	
}
