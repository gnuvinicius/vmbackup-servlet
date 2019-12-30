package br.com.vmbackup.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import br.com.vmbackup.dao.LogDao;
import br.com.vmbackup.modelo.Log;

@SuppressWarnings("serial")
@WebServlet("/log")
public class LogServlet extends HttpServlet {

	private LogDao logDao = new LogDao();
	
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("logica");

		if (acao.equals("log")) {
			List<Log> logList = logDao.getLista();
			request.setAttribute("logList", logList);
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp?file2=log.jsp");
			rd.forward(request, response);
		}
	}
}