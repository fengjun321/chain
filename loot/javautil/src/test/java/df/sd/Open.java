package df.sd;

public class Open {
	public static void v1() {

		int standardBoxOpened = 0;
		while (true) {
			int probabilityIn1000 = (3 * standardBoxOpened * standardBoxOpened) / 10000
					+ (25 * standardBoxOpened) / 100;

			int r1 = (int) (Math.random() * 1000);
			if (r1 < probabilityIn1000) {
				// hit limited Oman
				// mint
				System.out.println("hit~@" + standardBoxOpened);
				break;
			}
			standardBoxOpened++;
		}

	}
	public static void main(String[] args) {
		v2();
	}
	public static void v2() {

		int standardBoxOpened = 0;
		while (true) {
			int probabilityIn1000 = (int)  (1500/(1500 - standardBoxOpened)) ;

			int r1 = (int) (Math.random() * 1000);
			if (r1 < probabilityIn1000) {
				// hit limited Oman
				// mint
				System.out.println("hit~@" + standardBoxOpened);
				break;
			}
			standardBoxOpened++;
		}

	}
	
}
