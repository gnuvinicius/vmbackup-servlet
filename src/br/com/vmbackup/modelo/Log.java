package br.com.vmbackup.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Log {

	@Id
	@GeneratedValue
	@Column(name = "log_id")
	private int id;

	@Column(name = "name_guest")
	private String nameGuest;

	@Column(name = "date_clone", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(name = "time_clone_vmdk")
	private long timeCloneVmdk;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameGuest() {
		return nameGuest;
	}

	public void setNameGuest(String nameGuest) {
		this.nameGuest = nameGuest;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public long getTimeCloneVmdk() {
		return timeCloneVmdk;
	}

	public void setTimeCloneVmdk(long timeCloneVmdk) {
		this.timeCloneVmdk = timeCloneVmdk;
	}

	
}