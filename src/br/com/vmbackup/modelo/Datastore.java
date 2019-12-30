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
public class Datastore {

	@Id
	@GeneratedValue
	@Column(name = "datastore_id")
	private int id;
	private String name;
	private String url;

	@ManyToOne
	@JoinColumn(name = "hypervisor_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Hypervisor hypervisor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Hypervisor getHypervisor() {
		return hypervisor;
	}

	public void setHypervisor(Hypervisor hypervisor) {
		this.hypervisor = hypervisor;
	}

	
}