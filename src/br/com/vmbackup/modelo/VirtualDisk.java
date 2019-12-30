package br.com.vmbackup.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class VirtualDisk {

	@Id
	@GeneratedValue
	@Column(name = "virtualdisk_id")
	private int id;
	private String vmdk;
	private String flat;
	
	@ManyToOne
	@JoinColumn(name = "guest_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Guest guest;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVmdk() {
		return vmdk;
	}

	public void setVmdk(String vmdk) {
		this.vmdk = vmdk;
	}

	public String getFlat() {
		return flat;
	}

	public void setFlat(String flat) {
		this.flat = flat;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	@Override
	public String toString() {
		return "VirtualDisk [id=" + id + ", vmdk=" + vmdk + ", flat=" + flat + ", guest=" + guest + "]";
	}

	
	
}