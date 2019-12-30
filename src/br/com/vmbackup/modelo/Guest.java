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
public class Guest {

	@Id
	@GeneratedValue
	@Column(name = "guest_id")
	private int id;
	private int vmid;
	private String hostname;
	private String nameDir;
	private String vmx;
	private String storageName;
	private String storageUrl;
	
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

	public int getVmid() {
		return vmid;
	}

	public void setVmid(int vmid) {
		this.vmid = vmid;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getVmx() {
		return vmx;
	}

	public void setVmx(String vmx) {
		this.vmx = vmx;
	}

	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}

	public String getStorageUrl() {
		return storageUrl;
	}

	public void setStorageUrl(String storageUrl) {
		this.storageUrl = storageUrl;
	}

	public Hypervisor getHypervisor() {
		return hypervisor;
	}

	public void setHypervisor(Hypervisor hypervisor) {
		this.hypervisor = hypervisor;
	}

	public String getNameDir() {
		return nameDir;
	}

	public void setNameDir(String nameDir) {
		this.nameDir = nameDir;
	}

	@Override
	public String toString() {
		return "Guest [id=" + id + ", vmid=" + vmid + ", hostname=" + hostname + ", nameDir=" + nameDir + ", vmx=" + vmx
				+ ", storageName=" + storageName + ", storageUrl=" + storageUrl + ", hypervisor=" + hypervisor + "]";
	}
	
	
}
