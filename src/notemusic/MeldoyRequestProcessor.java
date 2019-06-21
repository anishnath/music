package notemusic;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.servlet.AsyncContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;

public class MeldoyRequestProcessor {

	private AsyncContext asyncContext;
	private FileItem item;
	private String dirName;

	private List<String> sheetJPG;
	private List<String> sheetMidi;
	private Properties prop;

	public MeldoyRequestProcessor() {
	}

	public List<String> getSheetJPG() {
		return sheetJPG;
	}

	public void setSheetJPG(List<String> sheetJPG) {
		this.sheetJPG = sheetJPG;
	}

	public List<String> getSheetMidi() {
		return sheetMidi;
	}

	public void setSheetMidi(List<String> sheetMidi) {
		this.sheetMidi = sheetMidi;
	}

	public MeldoyRequestProcessor(AsyncContext asyncCtx, FileItem fileItem, String dirName) {
		this.asyncContext = asyncCtx;
		this.item = fileItem;
		this.dirName = dirName;
	}

	public MeldoyRequestProcessor(FileItem fileItem, String dirName, Properties prop) {
		this.item = fileItem;
		this.dirName = dirName;
		this.prop = prop;
		this.sheetJPG =  new ArrayList<>();
		this.sheetMidi =  new ArrayList<>();
	}

	public Map<String, List> run() {

		Map<String, List> map = new TreeMap<>();

		// TODO Auto-generated method stub
		System.out.println("Inside MeldoyRequestProcessor RUN  ");

		System.out.println(item.toString());
		System.out.println("dirName -> " + dirName);

		String dockerVol = prop.getProperty("docker_volume", "/opt");
		String docker_volume_container = prop.getProperty("docker_volume_container", "/opt");
		String web_inf_user_images = prop.getProperty("web_inf_user_images", "/opt");
		String web_inf_user_mid = prop.getProperty("web_inf_user_mid", "/opt");

		String sheetCMD = (String) prop.get("sheet");
		String magentaCMD = (String) prop.get("magenta");

		String path = System.getProperty("java.io.tmpdir");

		System.out.println("Path -- >>" + path);

		System.out.println("dockerVol-- >>" + dockerVol);
		System.out.println("docker_volume_container-- >>" + docker_volume_container);
		System.out.println("web_inf_user_images-- >>" + web_inf_user_images);
		System.out.println("web_inf_user_mid-- >>" + web_inf_user_mid);
		System.out.println("sheetCMD-- >>" + sheetCMD);
		System.out.println("magentaCMD-- >>" + magentaCMD);

		String sheetName = item.getName().substring(0, item.getName().lastIndexOf("."));

		String userDirectory = dockerVol + "/midi_upload/" + dirName;
		String containerDirectoty = docker_volume_container + "/midi_upload/" + dirName;

		System.out.println("userDirectory -- " + userDirectory);

		File file = new File(userDirectory);
		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		try {
			FileUtils.cleanDirectory(new File(userDirectory));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String absolutefileName = userDirectory + "/" + item.getName();
		String containerfileName = containerDirectoty + "/" + item.getName();
		String sheetPart = containerDirectoty + "/" + sheetName;

		System.out.println("absolutefileName -- " + absolutefileName);
		System.out.println("containerfileName -- " + containerfileName);
		System.out.println("sheetPart -- " + sheetPart);

		File f = new File(absolutefileName);
		try {
			FileUtils.copyInputStreamToFile(item.getInputStream(), f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// docker exec -d notemusiclib mono /usr/bin/sheet.exe /opt/first1.mid
		// /opt/first

		String firstCommand = sheetCMD + " " + containerfileName + " " + sheetPart;

		String[] arr = firstCommand.split(" ");

		List<String> command = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
			command.add(arr[i]);
		}

		doCommand(command);

		System.out.println("userDirectory -- > " + userDirectory);
		File[] files = new File(userDirectory).listFiles();

		for (File t : files) {
			System.out.println(t.getName());
			if (t.isFile()) {

				if (t.getName().endsWith("png")) {

					System.out.println(t.getAbsolutePath());

					this.sheetJPG.add(t.getName());

				}

				System.out.println(t.getName());
			}

		}

		map.put("sheetspng", sheetJPG);
		
		// STAGE-2 Generate new Melody 
		
		

		
		String melody_rnn = "/usr/local/bin/docker exec notemusiclib melody_rnn_generate --config=basic_rnn --bundle_file=/opt/melody_rnn/basic_rnn.mag --output_dir="+containerDirectoty+"/newmidi --num_outputs=2 --num_steps=400 --primer_midi="+containerfileName;
		
		
		System.out.println(melody_rnn);
		
		 arr = melody_rnn.split(" ");

		command = new ArrayList<>();

		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
			command.add(arr[i]);
		}

		doCommand(command);
		
		String genuserDirectory = userDirectory+"/newmidi";
		
		System.out.println("genuserDirectory -- > " + genuserDirectory);
		files = new File(genuserDirectory).listFiles();

		for (File t : files) {
			System.out.println(t.getName());
			if (t.isFile()) {

				
				
			}

		}

		
		

		return map;

	}

	private static String encodeFileToBase64Binary(String fileName) throws IOException {
		File file = new File(fileName);
		byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
		return new String(encoded, StandardCharsets.US_ASCII);
	}

	public static void main(String[] args) {

		List<String> command = new ArrayList<>();
		StringBuilder bld = new StringBuilder();
		command.add("/usr/local/bin/docker");
		command.add("exec");
		command.add("notemusiclib");
		command.add("mono");
		command.add("/usr/bin/sheet.exe");
		command.add("/opt//midi_upload/25109B1256A7B437855BC0EE4E2A5EE4/first1.mid");
		command.add("/opt//midi_upload/25109B1256A7B437855BC0EE4E2A5EE4/first1");

		System.out.println(doCommand(command));

	}

	public static String doCommand(List<String> command)

	{
		StringBuilder builder = new StringBuilder();
		System.out.println("Command  " + command.toString());
		try {
			String s = null;

			ProcessBuilder pb = new ProcessBuilder(command);
			Process process = pb.start();

			BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			while ((s = stdInput.readLine()) != null) {
				builder.append(s + "<br>");
				builder.append(System.getProperty("line.separator"));
			}

			// read any errors from the attempted command
			// System.out
			// .println("Here is the standard error of the command (if
			// any):\n");
			while ((s = stdError.readLine()) != null) {
				builder.append(s + "<br>");
				builder.append(System.getProperty("line.separator"));
			}

		} catch (Exception e) {
			System.out.println("Error " + e);
		}
		return builder.toString();
	}

}
