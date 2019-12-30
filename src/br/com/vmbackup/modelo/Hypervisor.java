package br.com.vmbackup.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hypervisor {

	@Id
	@GeneratedValue
	@Column(name = "hypervisor_id")
	private int id;
	private String hostname;
	private String ipaddr;
	private String user;
	private String password;

	public int getId() {
		return id;
	}

	public void setId(int i) {
		this.id = i;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}