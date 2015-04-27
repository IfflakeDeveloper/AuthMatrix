package driver;


import java.nio.file.Paths;
import controller.LoginPageController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class WindowsLocker {
	Stage currentStage;
	WindowsSecurity ws;
	AltTabStopper alttabLock;
	String currDir;

	public WindowsLocker(Stage stage){
		currentStage = stage;
		currDir = Paths.get("").toAbsolutePath().toString();

		lock();
	}

	private void lock(){

		try{

			//KeyHook.blockWindowsKey();	

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoginPage.fxml"));
			Parent root = loader.load();
			LoginPageController controller = (LoginPageController) loader.getController();
			controller.setLocker(this);
			Scene scene = new Scene(root);

			Screen screen = Screen.getPrimary();
			Rectangle2D bounds = screen.getVisualBounds();

			currentStage.setX(bounds.getMinX());
			currentStage.setY(bounds.getMinY());
			currentStage.setWidth(bounds.getWidth());
			currentStage.setHeight(bounds.getHeight());
			// currentStage.initStyle(StageStyle.UNDECORATED);

			currentStage.setFullScreenExitHint(new String(""));
			currentStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
			currentStage.setTitle("Home Page");
			currentStage.setFullScreen(true);

			Platform.setImplicitExit(false);
			currentStage.setAlwaysOnTop(true);


			currentStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {

					event.consume();
				}});

			currentStage.setOnHiding(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {

					event.consume();
				}});

			currentStage.setScene(scene);
			disableKeyboard();


		}

		catch(Exception e){
			e.printStackTrace();
		}

	}



	private void disableKeyboard(){
		try{

			ws =new WindowsSecurity(currentStage);
			alttabLock = AltTabStopper.create(currentStage);

			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableTaskMgr /t REG_DWORD /d 1 /f");
			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableLockWorkstation /t REG_DWORD /d 1 /f");
			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableChangePassword  /t REG_DWORD /d 1 /f");
			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v NoLogoff  /t REG_DWORD /d 1 /f");

		}

		catch(Exception ex) { ex.printStackTrace(); }

	}

	public void unlock(){

		try{

			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableTaskMgr /t REG_DWORD /d 0 /f");
			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableLockWorkstation /t REG_DWORD /d 0 /f");
			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v DisableChangePassword  /t REG_DWORD /d 0 /f");
			Runtime.getRuntime().exec(currDir+"\\src\\extras\\elevate\\bin.x86-64\\elevate.exe -c REG add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System /v NoLogoff  /t REG_DWORD /d 0 /f");


			ws.stop();
			ws.thread.join();
			alttabLock.stop();


		}
		catch(Exception ex){ ex.printStackTrace(); }
	}


}
