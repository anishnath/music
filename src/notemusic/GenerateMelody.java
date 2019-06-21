package notemusic;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

/**
 * Servlet implementation class GenerateMelody
 */
@WebServlet(asyncSupported = true, description = "Generate new Melody", urlPatterns = { "/GenerateMelody" })
public class GenerateMelody extends HttpServlet {

	private List<AsyncContext> contexts = new LinkedList<AsyncContext>();
	private static final long serialVersionUID = 1L;
	private long maxFileSize = 1024 * 10 * 10 * 10;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateMelody() {
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
		
		Properties prop = (Properties)request.getServletContext().getAttribute("properties");
		
		System.out.println("Second--->" + prop.getProperty("docker_volume"));

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			HttpSession session = request.getSession(true);
			session.setAttribute("sheetspng", null);
			session.setAttribute("msg", null);
			PrintWriter out = response.getWriter();

			Map<String, Object> requestParameter = new HashMap<String, Object>();
			
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(getDiskFileFactory(request));

			String fName = "";

			boolean error = false;
			StringBuilder strbg = new StringBuilder();

			try {

				List<FileItem> items = upload.parseRequest(request);
				// Process the uploaded items
				Iterator<FileItem> iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					
					System.out.println(item);

					// Process a file upload
					if (!item.isFormField()) {
						String fieldName = item.getFieldName();
						fName = item.getName();
						String contentType = item.getContentType();
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();

						// System.out.println(sizeInBytes);
						if (sizeInBytes == 0) {
							strbg.append("EMpty file !! Please Upload a valid MIDI");
							// System.out.println("Here....");
							error = true;

						}
						if (sizeInBytes < maxFileSize) {
							requestParameter.put(fieldName, item);
						} else {
							// System.out.println("Here....22");
							strbg.append("Only File Size of 10 MB Supported ");
							error = true;

						}
						
						System.out.println("contentType-->" +contentType);
						
						if(contentType!=null && contentType.equals("audio/midi"))
						{
							
							final FileItem item2 = (FileItem) requestParameter.get("file");
							
//							AsyncContext asyncCtx = request.startAsync();
//					        asyncCtx.addListener(new GenerateMelodyAsyncListener());
					        //asyncCtx.setTimeout(9000);
//					        ThreadPoolExecutor executor = (ThreadPoolExecutor) request
//					                .getServletContext().getAttribute("executor");
//					        
					        MeldoyRequestProcessor meldoyRequestProcessor =  new MeldoyRequestProcessor(item2,session.getId(),prop);

					        
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
	                            
	                            Map map  =(TreeMap)result;
	                            
	                            if(map!=null)
	                            {
	                            	
	                            	List<String> ls =  (List)map.get("sheetspng");
	                            	
	                            	session.setAttribute("sheetspng", ls);
	                            	
	                            	response.sendRedirect("genmelodya.jsp");
	                				return;
	                            	
	                            	
	                            }
	                            
	                            
	                        }catch(Exception ex )
	                        {
	                        	ex.printStackTrace();
	                        	strbg.append("OOPS Something Goes bad ");
								error = true;
	                        	
	                        }
					        
					        System.out.println("Sixe-- " +meldoyRequestProcessor.getSheetJPG().size());
					        
					        System.out.println("Sixety-- " +meldoyRequestProcessor.getSheetJPG().size());
					        
					        
					        
					        
					        
						}
						else{
							strbg.append("Upload Only audio/midi file");
							error = true;
						}

					} else {
						String name = item.getFieldName();
						String value = item.getString();
						requestParameter.put(name, value);
						// System.out.println("name =" + name + " value =" +
						// value);
					}
				} // end while

			} catch (Exception ex) {
				session.setAttribute("msg",
						"<font size=\"2\" color=\"red\"> System Error +" + ex.getMessage() + " </font>");
				response.sendRedirect("genmelodya.jsp");
				return;

			}

			if (error) {
				response.setContentType("text/html");
				// out.println("");
				session.setAttribute("msg", "<font size=\"2\" color=\"red\"> " + strbg.toString() + " </font>");
				// out.println("<font size=\"2\" color=\"red\"> " +
				// strbg.toString()+" </font>");
				response.sendRedirect("genmelodya.jsp");
				return;
			}
			
			
			

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
}
