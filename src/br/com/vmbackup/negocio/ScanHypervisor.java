package br.com.vmbackup.negocio;

import br.com.vmbackup.dao.DatastoreDao;
import br.com.vmbackup.dao.GuestDao;
import br.com.vmbackup.dao.VirtualDiskDao;
import br.com.vmbackup.modelo.Datastore;
import br.com.vmbackup.modelo.Guest;
import br.com.vmbackup.modelo.Hypervisor;
import br.com.vmbackup.modelo.VirtualDisk;

public class ScanHypervisor implements Runnable {

	private ExecuteSSH executeSSh = new ExecuteSSH();
	private DatastoreDao datastoreDao = new DatastoreDao();
	public static Boolean statusScan = false;
	public static float percentScan = 0;

	@Override
	public void run() {
		Hypervisor hypervisor = ImplementThread.staticHypervisor;

		statusScan = true;
		// datastoreName
		String comandoGetDatastoreName = "vim-cmd hostsvc/datastore/listsummary | grep name | cut -d \"=\" -f 2 | cut -d \"\\\"\" -f 2";
		String[] datastoresName = executeSSh.ssh(hypervisor, comandoGetDatastoreName).split("\n");
		// datastoreUrl
		String comandoGetDatastoreUrl = "vim-cmd hostsvc/datastore/listsummary | grep url | cut -d \"=\" -f 2 | cut -d \"\\\"\" -f 2";
		String[] datastoresUrl = executeSSh.ssh(hypervisor, comandoGetDatastoreUrl).split("\n");

		for (int d = 0; d < datastoresName.length; d++) {
			Datastore datastore = new Datastore();
			datastore.setName(datastoresName[d]);
			datastore.setUrl(datastoresUrl[d]);
			datastore.setHypervisor(hypervisor);
			datastoreDao.addDatastore(datastore);
		}

		String comandoGetVmids = "vim-cmd vmsvc/getallvms | grep -v Vmid | awk \"{print \\$1}\"";
		String vmids = executeSSh.ssh(hypervisor, comandoGetVmids);
		String[] arrayStringVmid = vmids.split("\n");

		percentScan = 0;
		int j = 1;
		for (int i = 0; i < arrayStringVmid.length; i++) {
			scanGuest(hypervisor, Integer.parseInt(arrayStringVmid[i]));
			percentScan = (float) ((j * 100) / arrayStringVmid.length);
			j++;
		}

		statusScan = false;
	}

	public void scanGuest(Hypervisor hypervisor, int vmid) {

		GuestDao guestDao = new GuestDao();

		String removerSnapshot = "vim-cmd vmsvc/snapshot.removeall " + vmid;
		String comandoGetPowerState = "vim-cmd vmsvc/power.getstate " + vmid + " | grep Powered";
		String comandoGetHostname = "vim-cmd vmsvc/get.summary " + vmid + " | grep name | cut -d \"\\\"\" -f 2";
		String comandoGetStorageName = "vim-cmd vmsvc/get.datastores " + vmid + " | grep name | awk \"{print \\$2}\"";
		String comandoGetStorageUrl = "vim-cmd vmsvc/get.datastores " + vmid + " | grep url | awk \"{print \\$2}\"";
		String comandoGetArquivoVmx = "vim-cmd vmsvc/get.config " + vmid + " | grep vmPathName | cut -d \"/\" -f 2 | cut -d \"\\\"\" -f 1";
		String comandoGetNameDir = "vim-cmd vmsvc/get.config " + vmid + " | grep value | grep vswp | cut -d \"/\" -f 5";

		String[] guestPowerState = executeSSh.ssh(hypervisor, comandoGetPowerState).split("\n");

		if (guestPowerState[0].equals("Powered on")) {
			
			Guest guest = new Guest();
			executeSSh.runComando(hypervisor, removerSnapshot);

			guest.setVmid(vmid);
			String[] guestHostname = executeSSh.ssh(hypervisor, comandoGetHostname).split("\n");
			guest.setHostname(guestHostname[0]);
			String[] getNameDir = executeSSh.ssh(hypervisor, comandoGetNameDir).split("\n");
			guest.setNameDir(getNameDir[0]);
			String[] vmx = executeSSh.ssh(hypervisor, comandoGetArquivoVmx).split("\n");
			guest.setVmx(vmx[0]);
			String[] storageName = executeSSh.ssh(hypervisor, comandoGetStorageName).split("\n");
			guest.setStorageName(storageName[0]);
			String[] storageUrl = executeSSh.ssh(hypervisor, comandoGetStorageUrl).split("\n");
			guest.setStorageUrl(storageUrl[0]);
			guest.setHypervisor(hypervisor);
			
			System.out.println(guest.toString());
			
			guestDao.adicionarGuest(guest);
			
			scanVirtualDisktheGuest(hypervisor, guest.getHostname());

		} else {
			if (guestPowerState[0].equals("Powered off")) {
				Guest guest = guestDao.buscarPorVmid(vmid);
				if (guest != null) {
					System.out.println("excluindo guest desligado!");
					guestDao.excluir(guest);
				}
			}
		}
		System.out.println("fim scan do Guest vmid: " + vmid);
		System.out.println("##############");

	}

	public void scanVirtualDisktheGuest(Hypervisor hypervisor, String guestName) {
		
		GuestDao guestDao = new GuestDao();
		VirtualDiskDao virtualDiskDao = new VirtualDiskDao();
		
		Guest guest = guestDao.buscarPorHostname(guestName);

		String comandoGetFileVmdk = "ls " + "\"" + guest.getStorageUrl() + "/" + guest.getNameDir() + "\"" + " | grep vmdk | grep -v flat";
		String[] getvmdks = executeSSh.ssh(hypervisor, comandoGetFileVmdk).split("\n");

		String comandoGetFileFlatVmdk = "ls " + "\"" + guest.getStorageUrl() + "/" + guest.getNameDir() + "\"" + " | grep flat";
		String[] getflatVmdks = executeSSh.ssh(hypervisor, comandoGetFileFlatVmdk).split("\n");
		
		for (int j = 0; j < getvmdks.length; j++) {
			VirtualDisk virtualDisk = new VirtualDisk();
			virtualDisk.setVmdk(getvmdks[j]);
			virtualDisk.setFlat(getflatVmdks[j]);
			virtualDisk.setGuest(guest);
			virtualDiskDao.adicionarVirtualDisk(virtualDisk);
			System.out.println(virtualDisk.toString());
		}
	}
}