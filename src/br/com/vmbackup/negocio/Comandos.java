package br.com.vmbackup.negocio;

import java.util.ArrayList;
import java.util.List;

import br.com.vmbackup.modelo.Guest;
import br.com.vmbackup.modelo.Schedule;
import br.com.vmbackup.modelo.VirtualDisk;

public class Comandos {
	
	public String createFolder(Schedule schedule) {
		return "mkdir " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "\"";
	}	

	public String criarSnapshot(Guest guest) {
		return "vim-cmd vmsvc/snapshot.create " + guest.getVmid() + " snapshot1 snapshot 0 0";
	}

	public String removerSnapshot(Guest guest) {
		return "vim-cmd vmsvc/snapshot.removeall " + guest.getVmid();
	}

	public String copiarArquivoVmx(Guest guest, Schedule schedule) {
		return "cp " + "\"" + guest.getStorageUrl() + "/" + guest.getNameDir() + "/" + guest.getVmx() + "\"" + " " + "\"" + schedule.getDatastore().getUrl() + "/" + guest.getNameDir() + "/" + guest.getVmx() + "\"";
	}

	public List<String> clonarVmdk(Schedule schedule, List<VirtualDisk> virtualDisk) {
		List<String> comandosVmkfstools = new ArrayList<>();

		for (int i = 0; i < virtualDisk.size(); i++) {
			String clonarVmdk = "vmkfstools -i " + "\"" + schedule.getGuest().getStorageUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk()+ "\"" + " " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk() + "\" -d thin";
			comandosVmkfstools.add(clonarVmdk);
		}
		return comandosVmkfstools;
	}

	public List<String> renomearToOldFileVmdk(Schedule schedule, List<VirtualDisk> virtualDisk) {
		List<String> comandoRenomearVmdk = new ArrayList<>();

		for (int i = 0; i < virtualDisk.size(); i++) {
			String renomearFileVmdk = "mv " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk() + "\"" + " " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk() + "_old" + "\"";
			String renomearFileVmdkFlat = "mv " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getFlat() + "\"" + " " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getFlat() + "_old" + "\"";
			comandoRenomearVmdk.add(renomearFileVmdk);
			comandoRenomearVmdk.add(renomearFileVmdkFlat);
		}
		return comandoRenomearVmdk;
	}

	public List<String> renomearFromOldFileVmdk(Schedule schedule, List<VirtualDisk> virtualDisk) {
		List<String> comandoRenomearVmdk = new ArrayList<>();

		for (int i = 0; i < virtualDisk.size(); i++) {
			String renomearFileVmdk = "mv " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk() + "_old" + "\"" + " " + "\"" + schedule.getGuest().getStorageUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk() + "\"";
			String renomearFileVmdkFlat = "mv " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getFlat() + "_old" + "\"" + " " + schedule.getGuest().getStorageUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getFlat() + "\"";
			comandoRenomearVmdk.add(renomearFileVmdk);
			comandoRenomearVmdk.add(renomearFileVmdkFlat);
		}
		return comandoRenomearVmdk;
	}

	public List<String> removerVmdkOld(Schedule schedule, List<VirtualDisk> virtualDisk) {
		List<String> comandoRemoverVmdkOld = new ArrayList<>();

		for (int i = 0; i < virtualDisk.size(); i++) {
			String removerFileVmdkOld = "rm " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getVmdk() + "_old" + "\"";
			String removerFileVmdkFlatOld = "rm " + "\"" + schedule.getDatastore().getUrl() + "/" + schedule.getGuest().getNameDir() + "/" + virtualDisk.get(i).getFlat() + "_old" + "\"";
			comandoRemoverVmdkOld.add(removerFileVmdkOld);
			comandoRemoverVmdkOld.add(removerFileVmdkFlatOld);
		}
		return comandoRemoverVmdkOld;
	}
}