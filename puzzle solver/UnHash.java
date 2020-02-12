import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UnHash {

	static int[] unhashCompound1(String input, Worker thread) {
		int limit = 1;
		BigInteger ten = new BigInteger(Integer.toString(10));
		BigInteger ten_3 = ten.pow(3);
		BigInteger ten_6 = ten.pow(6);
		BigInteger ten_9 = ten.pow(9);
		while (limit < 61) {
			for (int i = 1; i <= limit; i++) {
				BigInteger O1_0 = new BigInteger(Integer.toString(i));
				for (int j = 1; j <= limit; j++) {
					BigInteger O2_0 = new BigInteger(Integer.toString(j));
					for (int k = 1; k <= limit; k++) {
						BigInteger O3_0 = new BigInteger(Integer.toString(k));
						for (int l = 1; l <= limit; l++) {
							if (i < limit && j < limit && k < limit && l < limit)
								continue;
							BigInteger O4_0 = new BigInteger(Integer.toString(l));
							BigInteger compound1 = O1_0.add(O2_0.multiply(ten_3)).add(O3_0.multiply(ten_6))
									.add(O4_0.multiply(ten_9));
							String compoundHash = hash(compound1.toString());
							if (compoundHash.compareTo(input) == 0) {
								int[] res = new int[4];
								res[0] = i;
								res[1] = j;
								res[2] = k;
								res[3] = l;
								return res;
							}
						}
					}
				}
			}
			limit++;
		}

		int[] res = new int[4];
		res[0] = -1;
		res[1] = -1;
		res[2] = -1;
		res[3] = -1;
		return res;

	}

	static Map<String, Integer[]> compoundMem = new HashMap<>();
	static int a = 1, b = 1, c = 1, d = 1;
	static int al = 0, bl = 0, cl = 0, dl = 0;

	static int[] unhashCompound(String input, Worker thread) {
		Integer[] ans = compoundMem.get(input);
		if (ans != null) {
			int[] res = { ans[0], ans[1], ans[2], ans[3] };
			// System.out.println("Saved compound " + Arrays.toString(res));
			return res;
		}

		int limit = 1;
		BigInteger ten = new BigInteger(Integer.toString(10));
		BigInteger ten_3 = ten.pow(3);
		BigInteger ten_6 = ten.pow(6);
		BigInteger ten_9 = ten.pow(9);
		main: for (; limit < 63; limit++) {
			// System.out.printf("%2d\n", a);

			for (a = 1; a <= limit; a++) {
				BigInteger O1 = new BigInteger(Integer.toString(a));
				for (b = 1; b <= limit; b++) {
					BigInteger O2 = new BigInteger(Integer.toString(b));
					for (c = 1; c <= limit; c++) {
						BigInteger O3 = new BigInteger(Integer.toString(c));
						for (d = 1; d <= limit; d++) {
							if (a < limit && b < limit && c < limit && d < limit
									|| a <= al && b <= bl && c <= cl && d <= dl)
								continue;
							Integer[] save = new Integer[] { a, b, c, d };
							BigInteger O4 = new BigInteger(Integer.toString(d));
							BigInteger compound = O1.add(O2.multiply(ten_3)).add(O3.multiply(ten_6))
									.add(O4.multiply(ten_9));

							String temp = hash(compound.toString());
							if (limit <= 40) {
								compoundMem.put(temp, save);
								al = a;
								bl = b;
								cl = c;
								dl = d;
							}
							if (temp.compareTo(input) == 0) {
								int[] res = { save[0], save[1], save[2], save[3] };
								// System.out.printf("%d %d %d %d %s\n", a, b, c, d, Arrays.toString(res));
								return res;
							}
							if (thread != null && !thread.keepRunning())
								break main;
						}
					}
				}
			}
		}
		int[] res = new int[4];
		res[0] = -1;
		res[1] = -1;
		res[2] = -1;
		res[3] = -1;
		return res;
	}

	static Map<String, Integer> simpleMem = new HashMap<>();
	static int simpleMemI = 0;

	static int unhash(String input, int upperBound, Worker thread) {
		Integer ans = simpleMem.get(input);
		if (ans != null) {
			return ans;
		}

		while (simpleMemI < upperBound) {
			simpleMemI = simpleMemI + 1;
			String temp = hash(Integer.toString(simpleMemI));
			simpleMem.put(temp, simpleMemI);

			if (temp.equals(input)) {
				return simpleMemI;
			}

			if (thread != null && !thread.keepRunning())
				break;

		}
		return -1;
	}

	public static String hash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] message = md.digest(input.getBytes());
			return bytesToHex(message);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}

	private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

}
