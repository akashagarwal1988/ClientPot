package main;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.swing.JOptionPane;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ClientPot {

	static Set<String> listOfURLs = new HashSet<String>();
	
	public static String retrieveURLsfromMail() {
		Folder folder = null;
		Store store = null;
		
		try {

			Properties props = System.getProperties();
			props.setProperty("mail.store.protocol", "imaps");

			Session session = Session.getDefaultInstance(props, null);
			// session.setDebug(true);
			store = session.getStore("imaps");
			store.connect("imap.mail.yahoo.com", "test_honeypot@yahoo.com",
					"Honeypot1");
			if(!store.isConnected())
				JOptionPane.showMessageDialog(null, "Error in connectiong to the test account!");
			folder = store.getFolder("Bulk Mail");
			folder.open(Folder.READ_WRITE);
			Message messages[] = folder.getMessages();
			for (Message msg : messages) {
				Object obj = msg.getContent();
				if (obj instanceof Multipart) {
		            handleMultiPart((Multipart) obj);
		        } else {
		            handlePart(msg);

		        }
								
			}
		
			System.out.println("No. of links in the set :" + listOfURLs.size());
			String file = writeToFile();
			return file;
			
		} catch (Exception e) {
			e.getStackTrace();
		}
		return null;
	}

	public static void handleMultiPart(Multipart multiPart) throws IOException, MessagingException {

		for (int i = 0; i < multiPart.getCount(); i++) {
	        handlePart(multiPart.getBodyPart(i));
	       
	    }
	}

	public static void handlePart(Part part) throws IOException, MessagingException {
		
		Document doc = Jsoup.parseBodyFragment((String) part
				.getContent());
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String url = link.attr("abs:href");
			System.out.println(url);
			listOfURLs.add(url);
		}
		
	}
	
	
	public static String writeToFile(){
		try
	      {
			Date date = new Date();
			String file = "listOfURls_" +date+ ".ser";
	         FileOutputStream fileOut =
	         new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(listOfURLs);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in " + file);
	         return file;
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
		return null;
	}
}
