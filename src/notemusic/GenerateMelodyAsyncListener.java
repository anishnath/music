package notemusic;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;

public class GenerateMelodyAsyncListener implements AsyncListener {

	@Override
	public void onComplete(AsyncEvent arg0) throws IOException {
		System.out.println("Completed");
		
		
		
		
	}

	@Override
	public void onError(AsyncEvent arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartAsync(AsyncEvent arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTimeout(AsyncEvent arg0) throws IOException {
	}
	

}
