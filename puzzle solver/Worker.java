import java.util.Arrays;

public class Worker extends Thread {

	private boolean isDone = false;
	private boolean doCompound = false;
	public String input;
	private boolean found = false;
	public String file;
	public int init;
	private int[] resCompound = new int[4];
	private int resSimple = -1;

	public Worker(boolean doComp, String input, int init, Node root, String file) {
		super();
		this.doCompound = doComp;
		this.input = input;
		this.init = init;
		this.file = file;

	}

	public synchronized void doStop() {
		this.isDone = true;
	}

	public synchronized boolean keepRunning() {
		return this.isDone == false;
	}

	public boolean isDone() {
		return isDone;
	}

	private void setFound() {
		this.found = true;
	}

	public boolean found() {
		return this.found;
	}

	public int[] getResultCompound() {
		return resCompound;
	}

	public int getResultSimple() {
		return resSimple;
	}

	public void run() {
		if (this.doCompound == false) {
			// System.out.println("Worker simple start " + input);
			int result = UnHash.unhash(input, this.file.length(), this);
			// System.out.println("Worker simple done: " + result);
			if (result != -1) {
				this.resSimple = result;
				setFound();
			}
		} else {
			// System.out.println("Worker compound start " + input);
			int[] result = UnHash.unhashCompound(input, this);
			// System.out.println("Worker compound done: " + Arrays.toString(result));
			if (result[0] != -1) {
				this.resCompound = result;
				setFound();
			}
		}

		doStop();
	}
}
