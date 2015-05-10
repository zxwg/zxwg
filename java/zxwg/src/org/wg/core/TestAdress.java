package org.wg.core;

public class TestAdress {
	/**
	 * 121 2 134 413
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Role r = new Role(7404);
		/**
		 * 121 2 134 413
		 */
		// 121 2
		// 134 413

		// 276038136
		// System.out.println(r.getMonsters());
		int adress = r.$(r.$(r.$(r.$(0xCF7E64) + 0x1c) + 4) + 8);
		for (int i1 = 0; i1 < 1000; i1++) {
			int iAdress = r.$(adress + i1 * 4);
			if (iAdress == 0) {
				// System.out.println(i1);
				continue;
			}
			for (int i2 = 0; i2 < 1000; i2++) {
				int jValue = r.$(iAdress + i2 * 4);
				if (jValue == 276038136) {
					System.out.println(jValue);
					System.out.println(i1 + " " + i2);
				}
				int num = i1 * 1000 + i2;
				if (num % 100000 == 0) {
					System.out.println(System.currentTimeMillis() + " "
							+ (num / 100000) + "0WÌõ");

				}
			}
		}

	}

}
