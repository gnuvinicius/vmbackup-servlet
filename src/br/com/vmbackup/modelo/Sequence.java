package br.com.vmbackup.modelo;

import java.io.Serializable;

public class Sequence implements Serializable {

	private static final long serialVersionUID = 3796189018385641885L;

	private int sequence;

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Sequence() {
	}

	public Sequence(int sequence) {
		setSequence(sequence);
	}
}