package com.example.demosftp;

import com.jcraft.jsch.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;

@SpringBootApplication
public class DemoSftpApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoSftpApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Host=" + args[0]);
		System.out.println("Port=" + args[1]);
		System.out.println("User=" + args[2]);
		System.out.println("Password=" + args[3]);
		System.out.println("Path=" + args[4]);

		ChannelSftp channel = getConnection(args[0], Integer.parseInt(args[1]), args[2], args[3]);
		InputStream is = channel.get(args[4]);
		System.out.println("Get OK");
		SftpATTRS stat = channel.stat(args[4]);
		System.out.println("Stat OK OK");
		
	}



	ChannelSftp getConnection(String host, int port, String user, String password) throws JSchException {
			JSch jsch = new JSch();
			Session session = jsch.getSession(user, host, port);
			session.setConfig("PreferredAuthentications", "password");
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(password);
			session.connect(20000);
			Channel channel = session.openChannel("sftp");
			ChannelSftp sftp = (ChannelSftp) channel;
			sftp.connect(20000);
			return sftp;
	}


}
