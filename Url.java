import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors; 
class Url 
{ 
  private static final int MYTHREADS = 30;
  public static void main(String[] args)throws Exception 
  { 
  ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);
  File file_keys = new File("key_values.txt"); 
  File file_search = new File("search.txt");
  BufferedReader br_keys = new BufferedReader(new FileReader(file_keys)); 
  BufferedReader br_search = new BufferedReader(new FileReader(file_search));
  String search; 
  String key;
  while ((search = br_search.readLine()) != null){
    //System.out.println(search); 
	while ((key = br_keys.readLine()) != null){
	  String[] arr= key.split(" ");
	  if(search.equals(arr[0])){
	  //System.out.println("file found"+arr[0]+"link"+arr[1]);
	  String url = arr[1];
	  Runnable worker = new MyRunnable(url);
	  executor.execute(worker);
	  break;
	  }  
  }
  }
  executor.shutdown();
		while (!executor.isTerminated()) {
 
		}
		//System.out.println("\nFinished all threads");
  } 
  public static class MyRunnable implements Runnable {
		private final String url;
 
		MyRunnable(String url) {
			this.url = url;
		}
 
		@Override
		public void run() {
 
			String result = "";
			int code = 200;
			try {
				URL siteURL = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
				connection.setRequestMethod("HEAD");
				connection.setConnectTimeout(3000);
				connection.connect();
				code = connection.getResponseCode();
				if (code == 200) {
					result = "FILE FOUND\t"+url+ "\tResponse Code: " + code;
				} else {
					result = "FILE FOUND\t"+url+ "\tResponse Code: " + code;
				}
			} catch (Exception e) {
				result = "FILE NOT FOUND\t" + "Wrong domain - Exception: " + e.getMessage();
 
			}
			System.out.println(url + "\t\tStatus:" + result);
		}
	}
} 