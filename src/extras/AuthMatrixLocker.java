package extras;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.management.ManagementFactory;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

class AuthMatrixLocker{
	static final int INSTALL_SERVICE = 198;
	
	public static void main(String args[]){
		

		try{
		String currDir = Paths.get("").toAbsolutePath().toString();
		
		
		if(Integer.parseInt(args[0])==(INSTALL_SERVICE)){
			
			int pid = getProcessId();
			ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","genConfig.bat",new Integer(pid).toString(),"exit");
			
			System.out.print(currDir);
			
			pb.directory(new File(currDir + "\\src\\extras\\yajsw-stable-11.11\\bat\\"));
			Process process = pb.start();
			
			OutputStream stdin = process.getOutputStream ();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdin));
			
			writer.write("C");
			 
			boolean exitStatus = process.waitFor(1,TimeUnit.MINUTES);
			
			if(!exitStatus)
				process.destroy();
			else System.out.print("Yes!");
			
			 Properties prop =new Properties();
			   prop.load(new FileInputStream(currDir+"\\src\\extras\\yajsw-stable-11.11\\conf\\wrapper.conf"));
			   prop.setProperty("wrapper.ntservice.name", "AuthLocker");
			   prop.setProperty("wrapper.ntservice.displayname", "AuthLocker");
			   prop.store(new FileOutputStream(currDir+"\\src\\extras\\yajsw-stable-11.11\\conf\\wrapper.conf"),null);
			
			   
			   
			   
		}
		
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	static int getProcessId() throws Exception{
		
		
		
		java.lang.management.RuntimeMXBean runtime = 
			    java.lang.management.ManagementFactory.getRuntimeMXBean();
			java.lang.reflect.Field jvm = runtime.getClass().getDeclaredField("jvm");
			jvm.setAccessible(true);
			sun.management.VMManagement mgmt =  
			    (sun.management.VMManagement) jvm.get(runtime);
			java.lang.reflect.Method pid_method =  
			    mgmt.getClass().getDeclaredMethod("getProcessId");
			pid_method.setAccessible(true);

			int pid = (Integer) pid_method.invoke(mgmt);
			return pid;
		
	}
	
	
}