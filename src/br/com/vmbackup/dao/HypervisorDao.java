package br.com.vmbackup.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.vmbackup.modelo.Hypervisor;

@Stateless
public class HypervisorDao {

	public void adicionarHypervisor(Hypervisor hypervisor) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		manager.persist(hypervisor);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public List<Hypervisor> getLista() {
		EntityManager manager = VmbackupDao.getManager();
		Query query = manager.createQuery("SELECT e FROM Hypervisor e");
		@SuppressWarnings("unchecked")
		List<Hypervisor> hypervisor = query.getResultList();
		VmbackupDao.closeManager();
		return hypervisor;
	}

	public Hypervisor buscarPorId(int id) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT e FROM Hypervisor as e " + "where e.id = :paramId");
			query.setParameter("paramId", id);
			Hypervisor hypervisor = (Hypervisor) query.getSingleResult();
			return hypervisor;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public Hypervisor buscarPorHostname(String hostname) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager
					.createQuery("SELECT e FROM Hypervisor as e " + "where e.hostname LIKE :paramHostname");
			query.setParameter("paramHostname", "%" + hostname + "%");
			query.setMaxResults(1);
			Hypervisor hypervisor = (Hypervisor) query.getSingleResult();
			return hypervisor;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public void updateHypervisor(Hypervisor hypervisor) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		Hypervisor myHypervisor = manager.find(Hypervisor.class, hypervisor.getId());
		myHypervisor = hypervisor;
		manager.merge(myHypervisor);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public void excluir(Hypervisor hypervisor) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			manager.getTransaction().begin();
			Hypervisor myHypervisor = manager.find(Hypervisor.class, hypervisor.getId());
			manager.remove(myHypervisor);
			manager.getTransaction().commit();
		} catch (NoResultException e) {
			e.printStackTrace();
		} finally {
			VmbackupDao.closeManager();
		}

	}
}