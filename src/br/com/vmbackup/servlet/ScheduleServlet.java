package br.com.vmbackup.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.vmbackup.dao.DatastoreDao;
import br.com.vmbackup.dao.GuestDao;
import br.com.vmbackup.dao.HypervisorDao;
import br.com.vmbackup.dao.ScheduleDao;
import br.com.vmbackup.modelo.Hour;
import br.com.vmbackup.modelo.Hypervisor;
import br.com.vmbackup.modelo.Schedule;
import br.com.vmbackup.negocio.ImplementThread;
import br.com.vmbackup.negocio.MakeCrontab;
import br.com.vmbackup.negocio.Procedure;
import br.com.vmbackup.negocio.ReadFiles;

@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private GuestDao guestDao;
	
	@Inject
	private DatastoreDao datastoreDao;

	@Inject
	private ScheduleDao scheduleDao;

	@Inject
	private HypervisorDao hypervisorDao;

	@Inject
	private ReadFiles readFiles;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("logica");

		if (acao.equals("addSchedule")) {
			Schedule schedule = new Schedule();

			int sequence = scheduleDao.getLastSequence();
			schedule.setSequence(++sequence);
			schedule.setGuest(guestDao.buscarPorHostname(request.getParameter("guest")));
			schedule.setDatastore(datastoreDao.buscarPorName(request.getParameter("datastore")));

			scheduleDao.addSchedule(schedule);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("editSchedule")) {
			Schedule schedule = new Schedule();
			schedule = scheduleDao.buscarPorSequence(Integer.parseInt(request.getParameter("sequence")));
			
			request.setAttribute("schedule_id", schedule.getId());
			request.setAttribute("sequence", schedule.getSequence());
			request.setAttribute("guest", schedule.getGuest().getHostname());
			request.setAttribute("storage", schedule.getDatastore().getName());
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?file=/schedule/update_schedule.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("updateSchedule")) {
			Schedule schedule = new Schedule();
			schedule = scheduleDao.buscarPorId(Integer.parseInt(request.getParameter("schedule_id")));
			
			schedule.setSequence(Integer.parseInt(request.getParameter("sequence")));
			schedule.setGuest(guestDao.buscarPorHostname(request.getParameter("guest")));
			schedule.setDatastore(datastoreDao.buscarPorName(request.getParameter("datastore")));
			scheduleDao.updateSchedule(schedule);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?file=schedule.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("upSchedule")) {
			int sequence = Integer.parseInt(request.getParameter("sequence"));
			if (--sequence != 0) {
				Schedule scheduleToUp = scheduleDao.buscarPorSequence(++sequence);
				scheduleToUp.setSequence(--sequence);
				Schedule scheduleToDown = scheduleDao.buscarPorSequence(sequence);
				scheduleToDown.setSequence(++sequence);
				scheduleDao.updateSchedule(scheduleToDown);
				scheduleDao.updateSchedule(scheduleToUp);
				request.setAttribute("hourBegin", readFiles.getHour().getHourBegin());
				request.setAttribute("hourEnd", readFiles.getHour().getHourEnd());
				request.setAttribute("statusThreadClone", ImplementThread.statusThreadClone);
				request.setAttribute("scheduleList", scheduleDao.getLista());
				request.setAttribute("hypervisorList", hypervisorDao.getLista());
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=schedule/step1.jsp&file2=schedule.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("hourBegin", readFiles.getHour().getHourBegin());
				request.setAttribute("hourEnd", readFiles.getHour().getHourEnd());
				request.setAttribute("statusThreadClone", ImplementThread.statusThreadClone);
				request.setAttribute("scheduleList", scheduleDao.getLista());
				request.setAttribute("hypervisorList", hypervisorDao.getLista());
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=schedule/step1.jsp&file2=schedule.jsp");
				rd.forward(request, response);
			}
		}

		if (acao.equals("downSchedule")) {
			int sequence = Integer.parseInt(request.getParameter("sequence"));
			if (scheduleDao.getLastSequence() > sequence) {
				Schedule scheduleToDown = scheduleDao.buscarPorSequence(sequence);
				scheduleToDown.setSequence(++sequence);
				Schedule scheduleToUp = scheduleDao.buscarPorSequence(sequence);
				scheduleToUp.setSequence(--sequence);
				scheduleDao.updateSchedule(scheduleToUp);
				scheduleDao.updateSchedule(scheduleToDown);
				request.setAttribute("hourBegin", readFiles.getHour().getHourBegin());
				request.setAttribute("hourEnd", readFiles.getHour().getHourEnd());
				request.setAttribute("statusThreadClone", ImplementThread.statusThreadClone);
				request.setAttribute("scheduleList", scheduleDao.getLista());
				request.setAttribute("hypervisorList", hypervisorDao.getLista());
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=schedule/step1.jsp&file2=schedule.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("hourBegin", readFiles.getHour().getHourBegin());
				request.setAttribute("hourEnd", readFiles.getHour().getHourEnd());
				request.setAttribute("statusThreadClone", ImplementThread.statusThreadClone);
				request.setAttribute("scheduleList", scheduleDao.getLista());
				request.setAttribute("hypervisorList", hypervisorDao.getLista());
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=schedule/step1.jsp&file2=schedule.jsp");
				rd.forward(request, response);
			}
		}

		if (acao.equals("removeSchedule")) {
			request.setAttribute("sequence", request.getParameter("sequence"));
			RequestDispatcher rd = request
					.getRequestDispatcher("/index.jsp?file=schedule/confirm_remover_schedule.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("deleteSchedule")) {
			Schedule schedule = new Schedule();
			schedule = scheduleDao.buscarPorSequence(Integer.parseInt(request.getParameter("sequence")));
			scheduleDao.deleteSchedule(schedule);
			int sequence = schedule.getSequence();
			while (scheduleDao.buscarPorSequence(++sequence) != null) {
				Schedule scheduleToUp = scheduleDao.buscarPorSequence(sequence);
				scheduleToUp.setSequence(--sequence);
				scheduleDao.updateSchedule(scheduleToUp);
				++sequence;
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?file=schedule.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("selectHypervisor")) {
			request.setAttribute("statusThreadClone", ImplementThread.statusThreadClone);
			request.setAttribute("comandCurrent", Procedure.comandCurrent);
			request.setAttribute("hourBegin", readFiles.getHour().getHourBegin());
			request.setAttribute("hourEnd", readFiles.getHour().getHourEnd());
			request.setAttribute("scheduleList", scheduleDao.getLista());
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=schedule/step1.jsp&file2=schedule.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("selectGuest")) {
			Hypervisor hypervisor = hypervisorDao.buscarPorHostname(request.getParameter("hypervisor"));
			request.setAttribute("hourBegin", readFiles.getHour().getHourBegin());
			request.setAttribute("hourEnd", readFiles.getHour().getHourEnd());
			request.setAttribute("scheduleList", scheduleDao.getLista());
			request.setAttribute("guestList", guestDao.buscarPorGuestNotInSchedule(hypervisor.getId()));
			request.setAttribute("datastoreList", datastoreDao.buscarDatastorePorHypervisor(hypervisor));
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=schedule/step2.jsp&file2=schedule.jsp");
			rd.forward(request, response);
		}
		
		if (acao.equals("updateController")) {
			Hour hour = new Hour();
			MakeCrontab makeCrontab = new MakeCrontab();
			
			hour.setHourBegin(request.getParameter("hourBegin"));
			hour.setHourEnd(request.getParameter("hourEnd"));
			
			readFiles.setHour(hour);
			System.out.println(readFiles.getSequence().getSequence());
			
			makeCrontab.schedule(true);
			request.setAttribute("scheduleList", scheduleDao.getLista());
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			RequestDispatcher rd = request.getRequestDispatcher("schedule?logica=selectHypervisor");
			rd.forward(request, response);
		}
		
		if (acao.equals("beginThisClone")) {
			int sequence = Integer.parseInt(request.getParameter("sequence"));
			ImplementThread.runClone(sequence);
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}
	}
}