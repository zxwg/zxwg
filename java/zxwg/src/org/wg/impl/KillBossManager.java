package org.wg.impl;

import java.util.ArrayList;
import java.util.List;

import org.wg.core.Role;

public class KillBossManager {

	public static void main(String[] args) {
		HelperVO helperVO = new HelperVO();
		
		/**���������*/
		Role role = new Role(7552);
		/**����ID����*/
		List<Integer> taskLst = new ArrayList<Integer>();
		taskLst.add(60167);
		taskLst.add(60167);
		taskLst.add(60165);
		/**boss�����ַ���*/
		List<String> monsterNameLst = new ArrayList<String>();
		monsterNameLst.add("ڤ��");
		monsterNameLst.add("�ع�");
		monsterNameLst.add("ս��");
		monsterNameLst.add("����");
		monsterNameLst.add("а��");
		monsterNameLst.add("��̽");
		monsterNameLst.add("����");
		monsterNameLst.add("���");
		/**��ѯ����ѭ���������Զ���boss��ÿ����20s������������װ�������޸ġ�*/
		int queryMonsterNum = 9;
		/**boss��������㼯��*/
		List<List<int[]>> tarList = new ArrayList<List<int[]>>();
		List<int[]> lst1 = new ArrayList<int[]>();
		lst1.add(new int[]{-323,-183});
		tarList.add(lst1);

		List<int[]> lst2 = new ArrayList<int[]>();
		lst2.add(new int[]{-339,-223});
		tarList.add(lst2);
	
		List<int[]> lst3 = new ArrayList<int[]>();
		lst3.add(new int[]{-378,-230});
		tarList.add(lst3);
		
		List<int[]> lst4 = new ArrayList<int[]>();
		lst4.add(new int[]{-385,-220});
		lst4.add(new int[]{-389,-185});
		tarList.add(lst4);
		
		
		List<int[]> lst5 = new ArrayList<int[]>();
		lst5.add(new int[]{-384,-139});
		tarList.add(lst5);
		
		List<int[]> lst6 = new ArrayList<int[]>();
		lst6.add(new int[]{-349,-146});
		tarList.add(lst6);
		
		List<int[]> lst7 = new ArrayList<int[]>();
		lst7.add(new int[]{-316,-122});
		tarList.add(lst7);
		
		List<int[]> lst8 = new ArrayList<int[]>();
		lst8.add(new int[]{-307,-106});
		tarList.add(lst8);
		
		List<int[]> lst9 = new ArrayList<int[]>();
		lst9.add(new int[]{-274,-114});
		tarList.add(lst9);
		
		List<int[]> lst10 = new ArrayList<int[]>();
		lst10.add(new int[]{-249,-119});
		lst10.add(new int[]{-223,-107});
		tarList.add(lst10);
		List<int[]> lst11 = new ArrayList<int[]>();
		lst11.add(new int[]{-179,-145});
		tarList.add(lst11);
		
		List<int[]> lst12 = new ArrayList<int[]>();
		lst12.add(new int[]{-143,-141});
		lst12.add(new int[]{-132,-137});
		tarList.add(lst12);
		
		List<int[]> lst13 = new ArrayList<int[]>();
		lst13.add(new int[]{-127,-155});
		lst13.add(new int[]{-123,-185});
		tarList.add(lst13);
		
		List<int[]> lst14 = new ArrayList<int[]>();
		lst14.add(new int[]{-132,-230});
		tarList.add(lst14);
		
		List<int[]> lst15 = new ArrayList<int[]>();
		lst15.add(new int[]{-162,-208});
		lst15.add(new int[]{-180,-223});
		tarList.add(lst15);
		
		List<int[]> lst16 = new ArrayList<int[]>();
		lst16.add(new int[]{-157,-210});
		lst16.add(new int[]{-133,-234});
		tarList.add(lst16);
		
		List<int[]> lst17 = new ArrayList<int[]>();
		lst17.add(new int[]{-157,-210});
		lst17.add(new int[]{-172,-184});
		lst17.add(new int[]{-184,-184});
		tarList.add(lst17);
		
		List<int[]> lst171 = new ArrayList<int[]>();

		lst171.add(new int[]{-183,-204});
		lst171.add(new int[]{-211,-259});
		tarList.add(lst171);		
		
		List<int[]> lst18 = new ArrayList<int[]>();
		lst18.add(new int[]{-226,-256});
		tarList.add(lst18);
		
		List<int[]> lst19 = new ArrayList<int[]>();
		lst19.add(new int[]{-256,-252});
		lst19.add(new int[]{-276,-262});
		tarList.add(lst19);
		
		List<int[]> lst20 = new ArrayList<int[]>();
		lst20.add(new int[]{-316,-197});
		tarList.add(lst20);
		/**���ʱ�ݴ��������*/
		List<int[]> backLst = new ArrayList<int[]>();
		/**��������NPC����*/
		String npcName = "�˽�";
		/**��������*/
		int xpNum = 2;
		/**ʹ����Ʒ��N�������е�����,ֱ�ӷɵ����������NPC�Աߡ�*/
		int npcToXP = 3;	
		/**ÿ��boss���������������ֻ�������̹�ʹ��*/
		int maxDeadNum = 3;
		
		helperVO.setBackLst(backLst);
		
		helperVO.setRole(role);

		helperVO.setTaskLst(taskLst);
		
		helperVO.setMonsterNameLst(monsterNameLst);

		helperVO.setQueryMonsterNum(queryMonsterNum);

		helperVO.setTarList(tarList);


		helperVO.setNpcName(npcName);

		helperVO.setXpNum(xpNum);

		helperVO.setNpcToXP(npcToXP);

		helperVO.setMaxDeadNum(maxDeadNum);
		
		try {
			KillBossUtil.zlGuajiAll(helperVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
