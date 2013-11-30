package main;

import googleSafeBrowsing.GoogleSafeBrowsingJava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;

import virusTotal.VirusTotal;

public class AnalysisEngine {

	static HashMap<String, String> urlsAndReportMap = new HashMap<String, String>();
	static VirusTotal vt = new VirusTotal(
			"adecd08a507e4974c73623b60f05d7068bdf2f821e95ba8465a69f1209d61600");

	public static HashSet<String> readFromFile(File fileName) {

		HashSet<String> urls = null;
		try {
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			urls = (HashSet<String>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("HashSet class not found");
			c.printStackTrace();
		}

		return urls;

	}

	public static int scanURLsUsingVirusTotal(File fileName) {

		HashSet<String> urls = readFromFile(fileName);

		try {
			for (String url : urls) {
				vt.sendSubmitURls(url);
			
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	
	}

	public static String retrieveReportVirusTotal(File fileName)
			throws ClientProtocolException, IOException {
		HashSet<String> urls = readFromFile(fileName);
		String fileOutput = "VTReport_" + new Date() + ".txt";
		PrintWriter out = new PrintWriter(fileOutput);
		for (String url : urls) {
			String report = vt.retreiveURLscanReport(url);
			out.println(report);

			System.out.println(report);
		}
		out.close();
		return fileOutput;

	}

	public static String scanUsingGoogleSafeBrowsing(File fileName)
			throws IOException {
		HashSet<String> urls = readFromFile(fileName);
		String outputFile = "VTReport_" + new Date() + ".txt";
		PrintWriter out = new PrintWriter(outputFile);
		for (String url : urls) {
			String report = GoogleSafeBrowsingJava.scanURL(url);
			if (report == null)
				report = url + ": Not Malicious";
			else {
				report = url + ": " + report;
			}
			out.println(report);

			System.out.println(report);
			
			
		}
		out.close();
		return outputFile;

	}
}
