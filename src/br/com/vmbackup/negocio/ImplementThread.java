package br.com.vmbackup.negocio;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.vmbackup.modelo.Hypervisor;

public class ImplementThread implements Job {

	public static int sequenceCloneOne;
	public static Hypervisor staticHypervisor;
	public static Boolean statusThreadClone = false;

	public static void runClone() {
		Clonar clonar = new Clonar();
		Thread thread = new Thread(clonar);
		thread.start();
		statusThreadClone = thread.isAlive();
	}

	public static void runClone(int sequence) {
		sequenceCloneOne = sequence;
		ClonarOne clonarOne = new ClonarOne();
		Thread threadClonarOne = new Thread(clonarOne);
		threadClonarOne.start();
	}

	public static void scanHypervisor(Hypervisor hypervisor) {
		staticHypervisor = hypervisor;
		ScanHypervisor scanHypervisor = new ScanHypervisor();
		Thread threadScan = new Thread(scanHypervisor);
		threadScan.start();
	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		Clonar clonar = new Clonar();
		Thread thread = new Thread(clonar);
		thread.start();
	}
	
	public void interruptThread(Thread thread) {
		
	}
}