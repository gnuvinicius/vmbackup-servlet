package br.com.vmbackup.negocio;

import java.util.Date;
import java.util.List;

import br.com.vmbackup.dao.LogDao;
import br.com.vmbackup.dao.ScheduleDao;
import br.com.vmbackup.dao.VirtualDiskDao;
import br.com.vmbackup.modelo.Hypervisor;
import br.com.vmbackup.modelo.Log;
import br.com.vmbackup.modelo.Schedule;
import br.com.vmbackup.modelo.VirtualDisk;


public class Procedure {

	private static ExecuteSSH executeSSH = new ExecuteSSH();
	private static Comandos comandos = new Comandos();
	public static String comandCurrent = null;
	public static String GuestCurrent = null;
	public static Boolean methodCloning = false;


	public static void begin(int sequence) {

		ScheduleDao scheduleDao = new ScheduleDao();
		VirtualDiskDao virtualDiskDao = new VirtualDiskDao();
		LogDao logDao = new LogDao();

		Log log = new Log();
		Date date = new Date();

		Schedule schedule = scheduleDao.buscarPorSequence(sequence);
		Hypervisor hypervisor = schedule.getGuest().getHypervisor();
		List<VirtualDisk> virtualDisk = virtualDiskDao.buscarVirtualDiskPorGuest(schedule.getGuest());

		if (StatusHypervisor.isOnline(hypervisor)) {
			log.setNameGuest(schedule.getGuest().getHostname());
			log.setDate(date);

			methodCloning = true;
			GuestCurrent = schedule.getGuest().getHostname();

			comandCurrent = comandos.removerSnapshot(schedule.getGuest());
			executeSSH.runComando(hypervisor, comandCurrent);

			comandCurrent = comandos.criarSnapshot(schedule.getGuest());
			executeSSH.runComando(hypervisor, comandCurrent);

			comandCurrent = comandos.createFolder(schedule);
			executeSSH.runComando(hypervisor, comandCurrent);

			comandCurrent = comandos.copiarArquivoVmx(schedule.getGuest(), schedule);
			int status = executeSSH.runComando(hypervisor, comandCurrent);

			if (status == 0) {
				List<String> renomearToOldVmdk = comandos.renomearToOldFileVmdk(schedule, virtualDisk);
				for (int i = 0; i < renomearToOldVmdk.size(); i++) {
					comandCurrent = renomearToOldVmdk.get(i);
					executeSSH.runComando(hypervisor, comandCurrent);
				}

				long timeClone = System.currentTimeMillis();
				List<String> clonarVmdk = comandos.clonarVmdk(schedule, virtualDisk);
				for (int j = 0; j < clonarVmdk.size(); j++) {
					comandCurrent = clonarVmdk.get(j);
					executeSSH.runComando(hypervisor, comandCurrent);
				}
				log.setTimeCloneVmdk(((System.currentTimeMillis() - timeClone) / 1000));

				List<String> removerVmdkOld = comandos.removerVmdkOld(schedule, virtualDisk);
				for (int g = 0; g < removerVmdkOld.size(); g++) {
					comandCurrent = removerVmdkOld.get(g);
					executeSSH.runComando(hypervisor, comandCurrent);
				}

			}

			comandCurrent = comandos.removerSnapshot(schedule.getGuest());
			executeSSH.runComando(hypervisor, comandCurrent);

			logDao.addLog(log);
			GuestCurrent = null;
			comandCurrent = null;
			methodCloning = false;
		} else {
			System.out.println("conex�o falhou!");
		}
	}
}