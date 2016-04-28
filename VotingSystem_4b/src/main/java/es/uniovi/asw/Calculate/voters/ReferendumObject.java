package es.uniovi.asw.Calculate.voters;

public class ReferendumObject {

	private int SI;
	private int NO;

	public ReferendumObject(int si, int no) {
		this.SI = si;
		this.NO = no;
	}

	public int getSI() {
		return SI;
	}

	public void setSI(int sI) {
		SI = sI;
	}

	public int getNO() {
		return NO;
	}

	public void setNO(int nO) {
		NO = nO;
	}

}
