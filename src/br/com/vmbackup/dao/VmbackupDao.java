package br.com.vmbackup.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VmbackupDao {

	private static EntityManagerFactory factory = null;
	private static EntityManager manager = null;

	public static EntityManager getManager() {
		if (factory == null || !factory.isOpen()) {
			openManager();
		}
		return manager;
	}

	private synchronized static void openManager() {

		factory = Persistence.createEntityManagerFactory("vmbackup");
		manager = factory.createEntityManager();
	}

	public static void closeManager() {
		if (manager.isOpen() == true) {
			manager.close();
			factory.close();
		}
	}
	
	public void closeFactory() {
		factory.close();
	}
}