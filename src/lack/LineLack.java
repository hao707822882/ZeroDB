package lack;

public class LineLack {

	private boolean islack = false;

	public void lack() {
		this.islack = true;
	}

	public void unlack() {
		this.islack = false;
	}

	public boolean isLack() {
		return this.islack;
	}
}
