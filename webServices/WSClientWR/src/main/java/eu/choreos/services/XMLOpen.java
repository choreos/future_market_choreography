package eu.choreos.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class XMLOpen {
	public static String openUrl(String adress) {
		String xml = null;
			URL url = null;
			try {
				url = new URL(adress);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			try {
				url.openConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader buffer = null;
			try {
				buffer = new BufferedReader(new InputStreamReader(
						url.openStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			xml = "";
			String buffline = null;
			try {
				buffline = buffer.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			while (buffline != null) {
				xml += buffline + "\n";
				try {
					buffline = buffer.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return xml;
	}
}
