package br.com.vmbackup.modelo;

import java.io.Serializable;

public class Hour implements Serializable {

	private static final long serialVersionUID = -5909618859948402663L;

	private String hourBegin;
	private String hourEnd;

	public String getHourBegin() {
		return hourBegin;
	}

	public void setHourBegin(String hourBegin) {
		this.hourBegin = hourBegin;
	}

	public String getHourEnd() {
		return hourEnd;
	}

	public void setHourEnd(String hourEnd) {
		this.hourEnd = hourEnd;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}