package swingPlayer.example.RTSP_SwingPlayer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RtspSwingPlayerApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(RtspSwingPlayerApplication.class, args);
		 RtspSwingPlayer.main(args);
	}

}
