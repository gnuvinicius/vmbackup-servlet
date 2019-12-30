package br.com.vmbackup.negocio;

import br.com.vmbackup.modelo.Hypervisor;

public class StatusHypervisor {

	public static Boolean isOnline(Hypervisor hypervisor) {

		ExecuteSSH comandssh = new ExecuteSSH();
		String comando = "ping -c 1 " + hypervisor.getIpaddr();

		if (comandssh.runComando(hypervisor, comando) != 0) {
			return false;
		} else {
			return true;
		}
	}
}