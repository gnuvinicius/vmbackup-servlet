package br.com.vmbackup.negocio;

public class ClonarOne implements Runnable {

	public void run() {
		Procedure.begin(ImplementThread.sequenceCloneOne);
	}
}