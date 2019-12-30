package br.com.vmbackup.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.vmbackup.modelo.Datastore;
import br.com.vmbackup.modelo.Hypervisor;

@Stateless
public class DatastoreDao {

	public void addDatastore(Datastore datastore) {
		if (buscarPorUrleHypervisor(datastore, datastore.getHypervisor()) == null) {
			EntityManager manager = VmbackupDao.getManager();
			manager.getTransaction().begin();
			manager.persist(datastore);
			manager.getTransaction().commit();
			VmbackupDao.closeManager();
		}
	}

	public List<Datastore> getLista() {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT d FROM Datastore d");
			@SuppressWarnings("unchecked")
			List<Datastore> datastoreList = query.getResultList();
			return datastoreList;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public List<Datastore> buscarDatastorePorHypervisor(Hypervisor hypervisor) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT d FROM Datastore d " + "where d.hypervisor = :paramHypervisor");
			query.setParameter("paramHypervisor", hypervisor);
			@SuppressWarnings("unchecked")
			List<Datastore> datastoreList = query.getResultList();
			return datastoreList;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public Datastore buscarPorName(String name) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT d FROM Datastore as d " + "where d.name LIKE :paramName");
			query.setParameter("paramName", "%" + name + "%");
			query.setMaxResults(1);
			return (Datastore) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}
		
	public Datastore buscarPorUrl(String url) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT d FROM Datastore as d " + "where d.url LIKE :paramName");
			query.setParameter("paramName", "%" + url + "%");
			query.setMaxResults(1);
			return (Datastore) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}
	
	public Datastore buscarPorUrleHypervisor(Datastore datastore, Hypervisor hypervisor) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT d FROM Datastore as d " + "where d.url = :paramUrl and d.hypervisor = :paramHypervisor");
			query.setParameter("paramUrl", datastore.getUrl());
			query.setParameter("paramHypervisor", hypervisor);
			query.setMaxResults(1);
			return (Datastore) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}
	
}