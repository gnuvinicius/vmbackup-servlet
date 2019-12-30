package br.com.vmbackup.servlet;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.vmbackup.dao.HypervisorDao;
import br.com.vmbackup.modelo.Hypervisor;
import br.com.vmbackup.negocio.ImplementThread;
import br.com.vmbackup.negocio.ScanHypervisor;
import br.com.vmbackup.negocio.StatusHypervisor;

@WebServlet("/hypervisor")
public class HypervisorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	private HypervisorDao hypervisorDao;

	public final static String HYPERVISOR = "hypervisor";

	public final static String CADASTRO_HYPERVISOR = "cadastro_hypervisor";

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		final String acao = request.getParameter("logica");

		if (acao.equals(HYPERVISOR)) {
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file2=hypervisor.jsp");
			rd.forward(request, response);
		}

		if (acao.equals(CADASTRO_HYPERVISOR)) {
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=hypervisor_cadastro.jsp&file2=hypervisor.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("createHypervisor")) {
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			Hypervisor hypervisor = new Hypervisor();
			hypervisor.setHostname(request.getParameter("hostname"));
			hypervisor.setIpaddr(request.getParameter("ipaddr"));
			hypervisor.setUser(request.getParameter("user"));
			hypervisor.setPassword(request.getParameter("password"));
			
			System.out.println(hypervisor.getHostname());
			/**
			 * consertar isso aqui!!
			 */
			if (hypervisorDao.buscarPorHostname(hypervisor.getHostname()) != null) {
				request.setAttribute("hostname", hypervisor.getHostname());
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				request.setAttribute("statusCadastroHypervisor", "VMware ESXi j� cadastrado!");
				rd.forward(request, response);
			} else {
				if (StatusHypervisor.isOnline(hypervisor) == false) {
					request.setAttribute("statusCadastroHypervisor", "Endere�o IP, usuario ou senha inv�lidos!");
					RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=hypervisor_cadastro.jsp&file2=hypervisor.jsp");
					rd.forward(request, response);
				} else {
					if ((hypervisorDao.buscarPorHostname(hypervisor.getHostname()) == null)) {
						hypervisorDao.adicionarHypervisor(hypervisor);
						RequestDispatcher rd = request.getRequestDispatcher("hypervisor?logica=hypervisor");
						rd.forward(request, response);
					}
				}
			}
		}

		if (acao.equals("editHypervisor")) {
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			Hypervisor hypervisor = hypervisorDao.buscarPorHostname(request.getParameter("hostname"));
			request.setAttribute("hypervisor_id", hypervisor.getId());
			request.setAttribute("hostname", hypervisor.getHostname());
			request.setAttribute("ipaddr", hypervisor.getIpaddr());
			request.setAttribute("user", hypervisor.getUser());
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp?file=update_hypervisor.jsp&file2=hypervisor.jsp");
			rd.forward(request, response);
		}

		if (acao.equals("updateHypervisor")) {
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			Hypervisor hypervisor = hypervisorDao.buscarPorId(Integer.parseInt(request.getParameter("hypervisor_id")));
			hypervisor.setHostname(request.getParameter("hostname"));
			hypervisor.setIpaddr(request.getParameter("ipaddr"));
			hypervisor.setUser(request.getParameter("user"));
			hypervisor.setPassword(request.getParameter("password"));
			if (!StatusHypervisor.isOnline(hypervisor)) {
				request.setAttribute("hypervisor_id", hypervisor.getId());
				request.setAttribute("hostname", hypervisor.getHostname());
				request.setAttribute("ipaddr", hypervisor.getIpaddr());
				request.setAttribute("user", hypervisor.getUser());
				request.setAttribute("status", "Endere�o IP, usuario ou senha inv�lidos!");
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=update_hypervisor.jsp");
				rd.forward(request, response);
			} else {
				hypervisorDao.updateHypervisor(hypervisor);
				RequestDispatcher rd = request.getRequestDispatcher("hypervisor?logica=hypervisor");
				rd.forward(request, response);
			}
		}

		if (acao.equals("removerHypervisor")) {
			request.setAttribute("hypervisorList", hypervisorDao.getLista());
			Hypervisor hypervisor = hypervisorDao.buscarPorHostname(request.getParameter("hostname"));
			if (hypervisor != null) {
				request.setAttribute("hypervisorHostname", hypervisor.getHostname());
				RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file=confirm_remover_hypervisor.jsp&file2=hypervisor.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("hypervisor?logica=hypervisor");
				rd.forward(request, response);
			}
		}

		if (acao.equals("deleteHypervisor")) {
			Hypervisor hypervisor = hypervisorDao.buscarPorHostname(request.getParameter("hostname"));
			if (hypervisor != null) {
				hypervisorDao.excluir(hypervisor);
				RequestDispatcher rd = request.getRequestDispatcher("hypervisor?logica=hypervisor");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("hypervisor?logica=hypervisor");
				rd.forward(request, response);
			}
		}

		if (acao.equals("scanHypervisor")) {
			if (ScanHypervisor.statusScan) {
				request.setAttribute("status", false);
				RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			} else {
				Hypervisor hypervisor = hypervisorDao.buscarPorHostname(request.getParameter("hostname"));
				if (hypervisor != null) {
					ImplementThread.scanHypervisor(hypervisor);
					request.setAttribute("status", true);
					RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
					rd.forward(request, response);
				}
			}
		}
	}
}