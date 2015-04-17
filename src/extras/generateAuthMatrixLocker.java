package extras;

import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

class generateAuthMatrixLocker{
	
	static final int INSTALL_SERVICE = 198;
	
	public static void main(String[] args){
		
		try{
		String currDir = Paths.get("").toAbsolutePath().toString();
		
		//Process process = Runtime.getRuntime().exec("java -jar \""+currDir+"src/extras/AuthMatrixLocker.jar\" "+INSTALL_SERVICE);
		Process process = Runtime.getRuntime().exec("javac \""+currDir+"src/extras/AuthMatrixLocker.jar\"");
	 process = Runtime.getRuntime().exec("java  \""+currDir+"src/extras/AuthMatrixLocker\" "+INSTALL_SERVICE);
		
		process.waitFor();
		
		
		 ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","runConsole.bat","exit");
		 pb.start();
			boolean exitStatus = process.waitFor(1,TimeUnit.MINUTES);
			 
		 
		 if(!exitStatus)
				process.destroy();
			else System.out.print("Yes!");
		 
		 
		 pb = new ProcessBuilder("cmd.exe","/c","installService.bat","exit");
		 pb.start();
			exitStatus = process.waitFor(1,TimeUnit.MINUTES);
			 
		 
		 if(!exitStatus)
				process.destroy();
			else System.out.print("Yes!");
		 
		 
		 
		  pb = new ProcessBuilder("cmd.exe","/c","runService.bat","exit");
		 pb.start();
			exitStatus = process.waitFor(1,TimeUnit.MINUTES);
			 
		 
		 if(!exitStatus)
				process.destroy();
			else System.out.print("Yes!");
			
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		
		}
	
	
}