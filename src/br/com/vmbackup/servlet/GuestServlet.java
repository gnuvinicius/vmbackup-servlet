package br.com.vmbackup.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.vmbackup.dao.GuestDao;
import br.com.vmbackup.dao.HypervisorDao;
import br.com.vmbackup.dao.VirtualDiskDao;
import br.com.vmbackup.modelo.Guest;
import br.com.vmbackup.modelo.Hypervisor;
import br.com.vmbackup.modelo.VirtualDisk;
import br.com.vmbackup.negocio.ScanHypervisor;

@SuppressWarnings("serial")
@WebServlet("/guest")
public class GuestServlet extends HttpServlet {

	private GuestDao guestDao = new GuestDao();
	private HypervisorDao hypervisorDao = new HypervisorDao();
	private VirtualDiskDao virtualDiskDao = new VirtualDiskDao();
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("logica");

		if (acao.equals("guest")) {
			List<Hypervisor> hypervisorList = hypervisorDao.getLista();
			String hypervisorName = request.getParameter("hypervisor");
			List<Guest> guestList = guestDao.buscarPorHypervisor(hypervisorDao.buscarPorHostname(hypervisorName));
			request.setAttribute("hypervisor", hypervisorName);
			request.setAttribute("guestList", guestList);
			request.setAttribute("hypervisorList", hypervisorList);
			if (ScanHypervisor.statusScan){
				request.setAttribute("statusScanThisHypervisor", true);
				request.setAttribute("value", ScanHypervisor.percentScan +"%");
			}
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?file=guest.jsp&file2=hypervisor.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("showGuest")) {
			Guest guest = guestDao.buscarPorHostname(request.getParameter("hostname"));
			request.setAttribute("guest_id", guest.getId());
			request.setAttribute("hostname", guest.getHostname());
			request.setAttribute("vmid", guest.getVmid());
			request.setAttribute("vmx", guest.getVmx());
			request.setAttribute("hypervisor", guest.getHypervisor().getHostname());
			request.setAttribute("storageName", guest.getStorageName());
			request.setAttribute("storageUrl", guest.getStorageUrl());
			List<VirtualDisk> virtualDiskList = virtualDiskDao.buscarVirtualDiskPorGuest(guest);
			request.setAttribute("virtualDiskList", virtualDiskList);
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=guest_info.jsp");
			rd.forward(request, response);
		}
		
		if (acao.equals("removerGuest")) {
			List<Hypervisor> hypervisorList = hypervisorDao.getLista();
			Guest guest = guestDao.buscarPorHostname(request.getParameter("hostname"));
			request.setAttribute("guestHostname", guest.getHostname());
			request.setAttribute("hypervisorList", hypervisorList);
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=confirm_remover_guest.jsp&file2=hypervisor.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("deleteGuest")) {
			List<Hypervisor> hypervisorList = hypervisorDao.getLista();
			request.setAttribute("hypervisorList", hypervisorList);
			Guest guest = guestDao.buscarPorHostname(request.getParameter("hostname"));
			guestDao.excluir(guest);
			RequestDispatcher rd = request.getRequestDispatcher("guest?logica=guest&hypervisor=" + guest.getHypervisor().getHostname());
			rd.forward(request, response);
		}
	}
}