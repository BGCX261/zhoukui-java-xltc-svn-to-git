package org.sunney.help.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleFileBlockServer {
	private static int port = 5001;

	public static void main(String[] args) {
		try {
			service(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void service(int port) throws IOException {
		ServerSocket server = new ServerSocket(port);
		String filepath = "D:/temp/sites.txt";
		while (true) {
			Socket socket = server.accept();
			new DownloadFile(socket, filepath).start();
		}
	}

	static class DownloadFile extends Thread {
		private Socket socket;
		private String filepath;
		private byte[] header;

		public DownloadFile(Socket socket, String filepath) {
			this.socket = socket;
			this.filepath = filepath;
			File file = new File(filepath);
			String head = "HTTP/1.0 200 OK \r\n" + 
				"Content-length: " + file.length() + "\r\n" + 
				"Content-type: application/octet-stream;charset=utf-8; \r\n" +
				"Content-disposition: attachment; filename="+file.getName()+" \r\n"+
				"Date: " + new java.util.Date() + "\r\n\r\n";
			this.header = head.getBytes();
		}

		@Override
		public void run() {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(filepath));
				out = new BufferedOutputStream(socket.getOutputStream());
				out.write(header);
				byte[] buffer = new byte[1024];
				int length = -1;
				while ((length = in.read(buffer)) != -1) {
					out.write(buffer, 0, length);
				}

				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
			} finally {
				try {
					if (null != in) {
						in.close();
					}
					if (null != out) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}