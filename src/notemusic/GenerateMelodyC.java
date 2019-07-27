package notemusic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

/**
 * Servlet implementation class GenerateMelody
 */
@WebServlet(asyncSupported = true, description = "Generate new Melody", urlPatterns = { "/GenerateMelodyC" })
public class GenerateMelodyC extends HttpServlet {

	private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
	private static final long serialVersionUID = 1L;
	private long maxFileSize = 1024 * 10 * 10 * 10;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateMelodyC() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String docker_volume = (String) request.getServletContext().getAttribute("docker_volume");
		System.out.println("Second--->" + docker_volume);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Properties prop = (Properties) request.getServletContext().getAttribute("properties");

		// request.getInputStream()

		System.out.println("Second--->" + prop.getProperty("docker_volume"));
		
		PrintWriter out = response.getWriter();
		

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		System.out.println("IsMultipart" + isMultipart);

		byte[] buffer = new byte[1024 * 1024];

		FileItem fileItem = new FileItem() {

			@Override
			public void setHeaders(FileItemHeaders arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public FileItemHeaders getHeaders() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void write(File arg0) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void setFormField(boolean arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setFieldName(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isInMemory() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFormField() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String getString(String arg0) throws UnsupportedEncodingException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getString() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public long getSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public OutputStream getOutputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return "item.midi";
			}

			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return request.getInputStream();
			}

			@Override
			public String getFieldName() {
				// TODO Auto-generated method stub
				return "item.midi";
			}

			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public byte[] get() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void delete() {
				// TODO Auto-generated method stub

			}
		};

		HttpSession session = request.getSession(true);

		MeldoyRequestProcessor meldoyRequestProcessor = new MeldoyRequestProcessor(fileItem, session.getId(), prop);

		boolean error = false;

		Callable<Object> callable = new Callable<Object>() {
			public Object call() throws Exception {
				return meldoyRequestProcessor.run();
			}
		};

		ExecutorService executorService = Executors.newCachedThreadPool();
		Object result = null;
		Future<Object> task = executorService.submit(callable);
		try {
			// ok, wait for 15 seconds max
			result = task.get(20, TimeUnit.SECONDS);

			Map map = (TreeMap) result;

			if (map != null) {

				List<String> ls = (List) map.get("sheetspng");

				for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
					String string = (String) iterator.next();

					System.out.println("string  " + string);
					//<iframe width="100" height="100" src="images/midi_upload/<%=request.getSession().getId() %>/<%=base64PNG%>"></iframe>
					
					String docker_vol = prop.getProperty("docker_volume");
					
					String actualPath = docker_vol + "/midi_upload/" + request.getSession().getId() + "/" + string;
					
					System.out.println("actualPath -- > " +actualPath);
					
					
					
					
					
					byte[] b = loadFileAsBytesArray(actualPath);
					
					String base64= Base64.getEncoder().encodeToString(b);
					
					String s = "<img class=\"img-fluid\" src=\"data:image/jpeg;base64,"+base64+"\">";
					
					System.out.println(s);
					
					out.println(s);
					
					

				}

				//session.setAttribute("sheetspng", ls);

				// response.sendRedirect("genmelodya.jsp");
				return;

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// strbg.append("OOPS Something Goes bad ");
			error = true;

		}

		System.out.println("Sixe-- " + meldoyRequestProcessor.getSheetJPG().size());

		System.out.println("Sixety-- " + meldoyRequestProcessor.getSheetJPG().size());

		if (error) {
			response.setContentType("text/html");
			// out.println("");
			// session.setAttribute("msg", "<font size=\"2\" color=\"red\"> " +
			// strbg.toString() + " </font>");
			// out.println("<font size=\"2\" color=\"red\"> " +
			// strbg.toString()+" </font>");
			response.sendRedirect("genmelodya.jsp");
			return;
		}

	}

	private DiskFileItemFactory getDiskFileFactory(HttpServletRequest request) {

		FileCleaningTracker tracker = FileCleanerCleanup.getFileCleaningTracker(request.getServletContext());

		// Check that we have a file upload request

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		factory.setFileCleaningTracker(tracker);
		return factory;
	}
	
	public static byte[] loadFileAsBytesArray(String fileName) throws Exception {
		 
        File file = new File(fileName);
        int length = (int) file.length();
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
        byte[] bytes = new byte[length];
        reader.read(bytes, 0, length);
        reader.close();
        return bytes;
 
    }
}
