package br.com.vmbackup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.vmbackup.modelo.Guest;
import br.com.vmbackup.modelo.VirtualDisk;

public class VirtualDiskDao {

	public void adicionarVirtualDisk(VirtualDisk virtualDisk) {
		if (buscarPorVmdk(virtualDisk.getVmdk()) == null) {
			EntityManager manager = VmbackupDao.getManager();
			manager.getTransaction().begin();
			manager.persist(virtualDisk);
			manager.getTransaction().commit();
			VmbackupDao.closeManager();
		}
	}

	public List<VirtualDisk> getLista() {
		EntityManager manager = VmbackupDao.getManager();
		Query query = manager.createQuery("SELECT v FROM VirtualDisk v");
		@SuppressWarnings("unchecked")
		List<VirtualDisk> virtualDisks = query.getResultList();
		VmbackupDao.closeManager();
		return virtualDisks;
	}

	public VirtualDisk buscarPorId(int id) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT v FROM VirtualDisk as v " + "where v.id = :paramId");
			query.setParameter("paramId", id);
			return (VirtualDisk) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public VirtualDisk buscarPorVmdk(String vmdk) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT v FROM VirtualDisk as v " + "where v.vmdk LIKE :paramVmdk");
			query.setParameter("paramVmdk", vmdk);
			query.setMaxResults(1);
			return (VirtualDisk) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public List<VirtualDisk> buscarVirtualDiskPorGuest(Guest guest) {
		EntityManager manager = VmbackupDao.getManager();
		Query query = manager.createQuery("SELECT v FROM VirtualDisk as v " + "where v.guest = :paramGuest");
		query.setParameter("paramGuest", guest);
		@SuppressWarnings("unchecked")
		List<VirtualDisk> virtualDisk = query.getResultList();
		VmbackupDao.closeManager();
		return virtualDisk;
	}

	public void updateVirtualDisk(VirtualDisk virtualDisk) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		VirtualDisk myVirtualDisk = manager.find(VirtualDisk.class, virtualDisk.getId());
		myVirtualDisk = virtualDisk;
		manager.merge(myVirtualDisk);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public void excluirVirtualDisk(VirtualDisk virtualDisk) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		VirtualDisk myVirtualDisk = manager.find(VirtualDisk.class, virtualDisk.getId());
		manager.remove(myVirtualDisk);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}
}