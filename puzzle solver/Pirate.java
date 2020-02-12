import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.lang.StringBuilder;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Pirate {
	private static BufferedReader br;
	private static Tree tree = new Tree(-1); // initialize a treeee

	public static void findTreasure(String str, int start_offset, Node root) {

		// if(start_offset > str.length()){
		// break;

		// }
		String toUnhash = str.substring(start_offset, start_offset + 32);
		Worker w1 = new Worker(false, toUnhash, start_offset, root, str);
		Worker w2 = new Worker(true, toUnhash, start_offset, root, str);

		w1.start();
		w2.start();

		while (!w1.found() && !w2.found()) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException in) {
				in.printStackTrace();
			}
		}

		if (!w1.isDone())
			w1.doStop();

		if (!w2.isDone())
			w2.doStop();

		if (w1.found()) {
			int res = w1.getResultSimple();
			Node one = new Node(res, "simple");

			root.setc1(one);
		}

		if (w2.found()) {
			int[] res = w2.getResultCompound();
			// System.out.println(w2.init + " " + str.length());
			// System.out.println(Arrays.toString(res));
			for (int i = 0; i < 4; i++) {
				res[i] = w2.init + 32 * res[i];
				if (i == 0) {
					Node one = new Node(res[i], "compound");
					root.setc1(one);
					findTreasure(str, res[i], root.child1);
				} else if (i == 1) {
					Node two = new Node(res[i], "compound");
					root.setc2(two);
					findTreasure(str, res[i], root.child2);
				} else if (i == 2) {
					Node three = new Node(res[i], "compound");
					root.setc3(three);
					findTreasure(str, res[i], root.child3);
				} else {
					Node four = new Node(res[i], "compound");
					root.setc4(four);
					findTreasure(str, res[i], root.child4);
				}
			}
		}
	}

	public static void main(String[] args) {
		int start_offset = Integer.parseInt(args[0]);
		File file = new File(args[1]);

		try {
			br = new BufferedReader(new FileReader(file));
			String input;

			input = br.readLine();
			Pirate.findTreasure(input, start_offset, tree.root);
			String clueFile = new String(Files.readAllBytes(Paths.get(args[2])));

			// try {
			// TimeUnit.SECONDS.sleep(100);
			// } catch (InterruptedException ex) {
			// Thread.currentThread().interrupt();
			// }
			tree.printLevelOrder();
			// System.out.println(tree.output);

			StringBuilder outputstr = new StringBuilder();

			for (int k = 0; k < tree.output.size(); k++) {
				// get each of the output from the list
				int returnednchar = tree.output.get(k);
				// appened the ouput to a character to the string builder.
				outputstr.append(clueFile.charAt(returnednchar));
			}
			System.out.println(outputstr);

		}

		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
