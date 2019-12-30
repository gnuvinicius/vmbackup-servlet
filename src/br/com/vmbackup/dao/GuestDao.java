package br.com.vmbackup.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.vmbackup.modelo.Guest;
import br.com.vmbackup.modelo.Hypervisor;

@Stateless
public class GuestDao {

	public void adicionarGuest(Guest guest) {
		if (buscarPorHostname(guest.getHostname()) == null) {
			EntityManager manager = VmbackupDao.getManager();
			manager.getTransaction().begin();
			manager.persist(guest);
			manager.getTransaction().commit();
			VmbackupDao.closeManager();
		}
	}

	public List<Guest> getLista() {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT g FROM Guest g");
			@SuppressWarnings("unchecked")
			List<Guest> guest = query.getResultList();
			return guest;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public List<Guest> buscarPorHypervisor(Hypervisor hypervisor) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT g FROM Guest g " + "where g.hypervisor = :paramHypervisor");
			query.setParameter("paramHypervisor", hypervisor);
			@SuppressWarnings("unchecked")
			List<Guest> guest = query.getResultList();
			return guest;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public Guest buscarPorId(int id) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT g FROM Guest as g " + "where g.id = :paramId");
			query.setParameter("paramId", id);
			Guest guest = (Guest) query.getSingleResult();
			return guest;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public Guest buscarPorHostname(String hostname) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT g FROM Guest as g " + "where g.hostname LIKE :paramHostname");
			query.setParameter("paramHostname", "%" + hostname + "%");
			query.setMaxResults(1);
			return (Guest) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public Guest buscarPorVmid(int vmid) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT g FROM Guest g " + "where g.vmid = :paramVmid");
			query.setParameter("paramVmid", vmid);
			Guest guest = (Guest) query.getSingleResult();
			return guest;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public List<Guest> buscarPorGuestNotInSchedule(int hypervisorId) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery(
					"FROM Guest g WHERE g.hypervisor.id = :paramHypervisor and g.id not in (SELECT s.guest.id FROM Schedule s)");
			query.setParameter("paramHypervisor", hypervisorId);
			@SuppressWarnings("unchecked")
			List<Guest> guestList = query.getResultList();
			return guestList;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public void upgrade(Guest guest) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		Guest myGuest = manager.find(Guest.class, guest.getId());
		myGuest = guest;
		manager.merge(myGuest);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public void excluir(Guest guest) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		Guest myGuest = manager.find(Guest.class, guest.getId());
		manager.remove(myGuest);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}
}