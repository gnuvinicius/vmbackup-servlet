package br.com.vmbackup.negocio;

import br.com.vmbackup.dao.HypervisorDao;
import br.com.vmbackup.modelo.Hypervisor;

public class Main {

	public static void main(String[] args) {
		
		int vmid = 197;

		HypervisorDao hypervisorDao = new HypervisorDao();
		
		Hypervisor hypervisor = hypervisorDao.buscarPorHostname("esxi-blade2");
		
		ScanHypervisor scanHypervisor = new ScanHypervisor();
		
		scanHypervisor.scanGuest(hypervisor, vmid);
		
		
	}
}