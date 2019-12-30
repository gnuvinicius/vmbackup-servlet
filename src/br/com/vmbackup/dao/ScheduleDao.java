package br.com.vmbackup.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.vmbackup.modelo.Schedule;

public class ScheduleDao {

	public void addSchedule(Schedule schedule) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		manager.persist(schedule);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public List<Schedule> getLista() {
		EntityManager manager = VmbackupDao.getManager();
		Query query = manager.createQuery("SELECT s FROM Schedule s ORDER BY sequence");
		@SuppressWarnings("unchecked")
		List<Schedule> schedule = query.getResultList();
		VmbackupDao.closeManager();
		return schedule;
	}

	public Schedule buscarPorId(int schedule_id) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT s FROM Schedule as s " + "where s.id = :paramId");
			query.setParameter("paramId", schedule_id);
			query.setMaxResults(1);
			return (Schedule) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public Schedule buscarPorSequence(int sequence) {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("SELECT s FROM Schedule as s " + "where s.sequence = :paramSequence");
			query.setParameter("paramSequence", sequence);
			query.setMaxResults(1);
			Schedule schedule = (Schedule) query.getSingleResult();
			return schedule;
		} catch (NoResultException e) {
			return null;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public int getLastSequence() {
		try {
			EntityManager manager = VmbackupDao.getManager();
			Query query = manager.createQuery("FROM Schedule order by sequence DESC");
			query.setMaxResults(1);
			Schedule schedule = (Schedule) query.getSingleResult();
			return schedule.getSequence();
		} catch (NoResultException e) {
			return 0;
		} finally {
			VmbackupDao.closeManager();
		}
	}

	public void updateSchedule(Schedule schedule) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		Schedule mySchedule = manager.find(Schedule.class, schedule.getId());
		mySchedule = schedule;
		manager.merge(mySchedule);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}

	public void deleteSchedule(Schedule schedule) {
		EntityManager manager = VmbackupDao.getManager();
		manager.getTransaction().begin();
		Schedule mySchedule = manager.find(Schedule.class, schedule.getId());
		manager.remove(mySchedule);
		manager.getTransaction().commit();
		VmbackupDao.closeManager();
	}
}