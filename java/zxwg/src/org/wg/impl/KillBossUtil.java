package org.wg.impl;

import java.util.List;

import org.wg.core.Role;

public class KillBossUtil {
	/**
	 * ���̹һ���BOSS
	 * @param helperVO
	 * @throws Exception
	 */
	public static void xpGuaji(HelperVO helperVO) throws Exception{

		Role role = helperVO.getRole();
		List<String> monsterNameLst = helperVO.getMonsterNameLst();
		int xpNum = helperVO.getXpNum(); 
		int queryMonsterNum = helperVO.getQueryMonsterNum();
		int maxDeadNum  = helperVO.getMaxDeadNum();
		//����Ŀ������
		int dotOfXP = 8;
		do   
		 for(int i=0;i<xpNum;i++){
				//���̵�ѭ������
				int dotIndex = 0;
				//ÿ�������������
				int maxDeadIndex = 0;
				do{
					Thread.sleep(1000);
					//��������سǡ�
					boolean isDel = BaseUtil.isDeadAndBack(role);
					//���û������ȡ�����˺�һ��
					if (!isDel){
						role.trhy(false);
						Thread.sleep(1000);
					} 
					System.out.println("���̵㣺" + dotIndex);
					//ʹ������
					role.xinpan(i,dotIndex);
					//��ȡ��ǰ����x��y����
					int curX = (int) role.getX();
					int curY = (int) role.getY();
					Thread.sleep(3000);
					//ѭ��40���ж��Ƿ�ǰ�������귢���仯�����δ�����仯���������20s�ڿ϶������仯��
					for(int j=0;j<40;j++){
						int newX = (int) role.getX();
						int newY = (int) role.getY();
						if (newX==curX && newY==curY){
							Thread.sleep(1000);
						}else{
							break;
						}
					}
					//��ʼ���˺�һ
					role.trhy(true);
					//���ѭ���ж��Ƿ��й��Ｐ�Ƿ�������������ﲻ���ڣ��������һ���㣬��������ڣ����ж������Ƿ�����
					for(int m=0;m<queryMonsterNum;m++) {
						boolean isDead = false;
						boolean isExist = BaseUtil.isExistMonsterByName(role, monsterNameLst,6);
						if (isExist){
							//ÿһ�붼�ж��Ƿ���������������¿�ʼ����ѭ����û��������ʱ��
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
							//���;�����������˳��������ҹ���ѭ����
							if (isDead){
								break;
							}
							//����Ѿ��ﵽ��������������������һ����ѭ����
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
	 * Ѱ·�һ���BOSS
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
			//��������سǡ�
			boolean isDel = BaseUtil.isDeadAndBack(role);
			//���û������ȡ�����˺�һ��
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
			//���ѭ���ж��Ƿ��й��Ｐ�Ƿ�������������ﲻ���ڣ��������һ���㣬��������ڣ����ж������Ƿ�����
			for(int m=0;m<queryMonsterNum;m++) {
				boolean isDead = false;
				boolean isExist = BaseUtil.isExistMonsterByName(role, monsterNameLst,6);
				if (isExist){
					//��ʼ���˺�һ
					role.trhy(true);
					//ÿһ�붼�ж��Ƿ���������������¿�ʼ����ѭ����û��������ʱ��
					for(int x=0;x<20;x++){ 
						hp=role.getHp();
						if (hp > 0) {
							Thread.sleep(1000);
						}else{
							isDead = true;
							break;
						}
					}
					//���;�����������˳��������ҹ���ѭ����
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
	 * Ѱ·�һ���BOSS
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
				//ʹ����Ʒ�����������е�����,ֱ�ӷɵ�NPC�Աߡ�
				role.xinpan(npcToXP-1,0);
				Thread.sleep(5000);
				
				int npcUID = BaseUtil.getNpcUID(role, npcName);
				if (npcUID !=0){
					role.jhnpc(npcUID);
					Thread.sleep(5000);
					role.jrw(tarkId);
					Thread.sleep(12000);
					//��ESC���رմ��ڡ�
					role.closeNpc();
				}
				
				zlGuaJi(helperVO);
			}
		}while(true);
	}
	
}
