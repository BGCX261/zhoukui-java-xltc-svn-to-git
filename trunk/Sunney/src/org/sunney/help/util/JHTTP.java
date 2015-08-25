package org.sunney.help.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class JHTTP extends Thread {
	private File documentRootDirectory;
	private String indexFileName = "index.html";
	private ServerSocket server;
	private int numThreads = 50;

	public JHTTP(File documentRootDirectory, int port, String indexFileName) throws IOException {
		if (!documentRootDirectory.isDirectory()) {
			throw new IOException(documentRootDirectory + " does not exist as a directory!");
		}
		this.documentRootDirectory = documentRootDirectory;
		this.indexFileName = indexFileName;
		this.server = new ServerSocket(port);
	}

	public JHTTP(File documentRootDirectory, int port) throws IOException {
		this(documentRootDirectory, port, "index.html");
	}

	public JHTTP(File documentRootDirectory) throws IOException {
		this(documentRootDirectory, 80);
	}

	public void run() {
		for (int i = 0; i < numThreads; i++) {
			Thread t = new Thread(new RequestProcessor(this.documentRootDirectory, indexFileName));
			t.start();
		}
		System.out.println("Accepting connections on port " + server.getLocalPort());
		System.out.println("Document Root:" + this.documentRootDirectory);
		while (true) {
			try {
				Socket request = server.accept();
				RequestProcessor.processRequest(request);
			} catch (IOException ex) {
			}
		}
	}

	public static void main(String[] args) {
		File docroot = new File("D:\\AppServ\\www\\zphp");
		int port = 8080;
		JHTTP webserver;
		try {
			webserver = new JHTTP(docroot, port);
			webserver.start();
		} catch (IOException e) {
			System.out.println("Server could not start because of an " + e.getClass());
			System.out.println(e);
		}
	}
}

class RequestProcessor implements Runnable {
	private static List<Socket> pool = new LinkedList<Socket>();
	private File documentRootDirectory;
	private String indexFileName = "index.html";

	public RequestProcessor(File documentRootDirectory, String indexFileName) {
		this.documentRootDirectory = documentRootDirectory;
		try {
			this.documentRootDirectory = documentRootDirectory.getCanonicalFile();
		} catch (IOException e) {
		}
		this.indexFileName = indexFileName;
	}

	public static void processRequest(Socket request) {
		synchronized (pool) {
			pool.add(pool.size(), request);
			pool.notifyAll();
		}
	}

	@Override
	public void run() {
		String root = this.documentRootDirectory.getPath();
		while (true) {
			Socket connection;
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException ie) {
					}
				}
				connection = pool.remove(0);
			}
			try {
				String filename = "";
				String contentType = "";
				OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
				Writer out = new OutputStreamWriter(raw);
				Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()), "UTF-8");

				StringBuffer requestLine = new StringBuffer();
				int c = -1;
				while (true) {
					c = in.read();
					if (c == '\r' || c == '\n') {
						break;
					}
					requestLine.append((char) c);
				}

				String get = requestLine.toString();
				// 记录请求头信息
				System.out.println("Request:\n" + get);

				StringTokenizer st = new StringTokenizer(get);
				String method = st.nextToken();
				String version = "";
				if (method.equals("GET")) {
					filename = st.nextToken();
					if (filename.endsWith("/")) {
						filename += indexFileName;
					}
					System.out.println("filename = " + filename);
					contentType = guessContentTypePromName(filename);
					if (st.hasMoreElements()) {
						version = st.nextToken();
					}
					File theFile = new File(documentRootDirectory, filename.substring(1, filename.length()));
					System.out.println(theFile.getAbsolutePath() + "--" + theFile.getCanonicalPath());
					if (theFile.canRead() && theFile.getCanonicalPath().startsWith(root)) {
						DataInputStream fis = new DataInputStream(new BufferedInputStream(new FileInputStream(theFile)));
						byte[] theData = new byte[(int) theFile.length()];
						fis.readFully(theData);
						fis.close();
						if (version.startsWith("HTTP")) {
							out.write("HTTP/1.0 200 OK\r\n");
							out.write("Date: " + new Date() + "\r\n");
							out.write("Server: JHTTP/1.0\r\n");
							out.write("Content-length: " + theData.length + "\r\n");
							out.write("Content-type: " + contentType + "\r\n\r\n");
							out.flush();
						}
						raw.write(theData);
						raw.flush();
					} else {
						if (version.startsWith("HTTP")) {
							out.write("HTTP/1.0 404 File Not Found\r\n");
							out.write("Date: " + new Date() + "\r\n");
							out.write("Server: JHTTP/1.0\r\n");
							out.write("Content-type: text/html\r\n\r\n");
							out.flush();
						}
						out.write("<HTML><HEAD><TITLE>DOCUMENT moved</TITLE></HEAD>\r\n");
						out.write("<BODY><H1>HTTP Error 404: File Not Found</H1>\r\n");
						out.write("</BODY></HTML>\r\n");
						out.flush();
					}
				} else {
					if (version.startsWith("HTTP")) {
						out.write("HTTP/1.0 501 Not Implemented\r\n");
						out.write("Date: " + new Date() + "\r\n");
						out.write("Server: JHTTP/1.0\r\n");
						out.write("Content-type: " + contentType + "\r\n\r\n");
						out.flush();
					}
					out.write("<HTML><HEAD><TITLE>DOCUMENT moved</TITLE></HEAD>\r\n");
					out.write("<BODY><H1>HTTP Error 501: Not Implemented</H1>\r\n");
					out.write("</BODY></HTML>\r\n");
					out.flush();
				}
			} catch (Exception e) {
			} finally {
				try {
					connection.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private String guessContentTypePromName(String filename) {
		if (filename.endsWith(".html") || filename.endsWith(".htm")) {
			return "text/html";
		} else if (filename.endsWith(".txt") || filename.endsWith(".txt")) {
			return "text/plain";
		} else if (filename.endsWith(".gif") || filename.endsWith(".gif")) {
			return "image/gif";
		} else if (filename.endsWith(".class") || filename.endsWith(".class")) {
			return "application/octet-stream";
		} else if (filename.endsWith(".jpg") || filename.endsWith(".jpg")) {
			return "image/jpeg";
		} else {
			return "text/plain";
		}
	}

}
