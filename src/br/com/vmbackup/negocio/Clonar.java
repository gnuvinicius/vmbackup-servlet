package br.com.vmbackup.negocio;

import br.com.vmbackup.dao.ScheduleDao;
import br.com.vmbackup.modelo.Sequence;

public class Clonar implements Runnable {

	private ReadFiles readFiles = new ReadFiles();
	private ScheduleDao scheduleDao = new ScheduleDao();

	public void run() {
		while (!StopCloneTime.getStopCloneTime()) {
			int sequence = readFiles.getSequence().getSequence();
			Procedure.begin(sequence);
			++sequence;
			if (scheduleDao.getLastSequence() < sequence) {
				sequence = 1;
			}
			readFiles.setSequence(new Sequence(sequence));

			if (StopCloneTime.getStopCloneTime()) {
				break;
			}
		}
	}
}