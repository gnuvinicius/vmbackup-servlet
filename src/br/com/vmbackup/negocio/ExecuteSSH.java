package br.com.vmbackup.negocio;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import br.com.vmbackup.modelo.Hypervisor;

public class ExecuteSSH {

	int status;

	public int runComando(Hypervisor hypervisor, String comando) {
		System.out.println(comando);
		JSch jsch = new JSch();
		Session session = null;
		ChannelExec channel = null;
		try {
			session = jsch.getSession(hypervisor.getUser(), hypervisor.getIpaddr(), 22);
			session.setPassword(hypervisor.getPassword());
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = (ChannelExec) session.openChannel("exec");
			channel.setOutputStream(System.out);
			channel.setCommand(comando);
			channel.connect();

			InputStream in;
			try {
				in = channel.getInputStream();

				byte[] tmp = new byte[1024];
				while (true) {
					while (in.available() > 0) {
						int i = in.read(tmp, 0, 1024);
						if (i < 0)
							break;
					}
					if (channel.isClosed()) {
						if (in.available() > 0)
							continue;
						status = channel.getExitStatus();
						System.out.println("exit-status: " + channel.getExitStatus());
						break;
					}
					try {
						Thread.sleep(1000);
					} catch (Exception ee) {
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			channel.disconnect();
			session.disconnect();
			return status;
		} catch (JSchException e) {
			return -1;
		}
	}

	public String ssh(Hypervisor hypervisor, String command) {
		System.out.println(command);

		String output = "";

		try {
			JSch jsch = new JSch();

			Session session = jsch.getSession(hypervisor.getUser(), hypervisor.getIpaddr(), 22);

			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);

			session.setPassword(hypervisor.getPassword());
			session.connect();

			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);

			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					
					if (i < 0) {
						break;
					}
					output = (new String(tmp, 0, i, "UTF-8"));
				}
				if (channel.isClosed()) {
					System.out.println("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(1000);
				} catch (Exception ee) {
				}
			}
			channel.disconnect();
			session.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		Procedure.comandCurrent = output;
		return output;
	}

}