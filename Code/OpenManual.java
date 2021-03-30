package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OpenManual {
	public static void openMan() {
		Desktop desktop = java.awt.Desktop.getDesktop();
		try {
			URI url = new URI(
					"https://docs.google.com/document/d/1ALhmJtkmgsNi7LzfX09ucUOZ_Wp0h3iUx9A4_olblSU/edit?usp=sharing");
			try {
				desktop.browse(url);
			} catch (IOException e) {
			}
		} catch (URISyntaxException e) {
		}
	}
}
