package br.com.vmbackup.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Schedule {

	@Id
	@GeneratedValue
	@Column(name = "schedule_id")
	private int id;
	private int sequence;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Guest guest;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Datastore datastore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	
}