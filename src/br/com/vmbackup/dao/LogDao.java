package br.com.vmbackup.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vmbackup.modelo.Log;

public class LogDao {

	public void addLog(Log log) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		manager.persist(log);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public List<Log> getLista() {
		EntityManager manager = VmbackupDao.getManager();
		Query query = manager.createQuery("SELECT l FROM Log l");
		@SuppressWarnings("unchecked")
		List<Log> logs = query.getResultList();
		Collections.reverse(logs);
		VmbackupDao.closeManager();
		return logs;
	}
}