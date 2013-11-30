package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.http.client.ClientProtocolException;

import main.AnalysisEngine;
import main.ClientPot;

public class ClientPotGUI extends JFrame implements ActionListener{
	
	public static void main(String []args){
		new ClientPotGUI();
	}

	private JButton fetchURLList;
	private JButton virusTotalSubmit;
	private JButton virusTotalRetrieve;
	private JButton googleSafeBrowsingScan;

	
	public ClientPotGUI(){
		
		fetchURLList = new JButton("FetchURLsFromMail");
		fetchURLList.setActionCommand("fetchURLList");
		fetchURLList.setPreferredSize(new Dimension(200,70));
		fetchURLList.addActionListener(this);
		
		googleSafeBrowsingScan = new JButton("GoogleSafeBrowsingScan");
		googleSafeBrowsingScan.setActionCommand("googleSBScan");
		googleSafeBrowsingScan.setPreferredSize(new Dimension(200,70));
		googleSafeBrowsingScan.addActionListener(this);
		
		virusTotalSubmit = new JButton("virusTotalSubmit");
		virusTotalSubmit.setActionCommand("virusTotalSubmit");
		virusTotalSubmit.setPreferredSize(new Dimension(200,70));
		virusTotalSubmit.addActionListener(this);
		
		virusTotalRetrieve = new JButton("virusTotalRetrieve");
		virusTotalRetrieve.setActionCommand("virusTotalRetrieve");
		virusTotalRetrieve.setPreferredSize(new Dimension(200,70));
		virusTotalRetrieve.addActionListener(this);
		
		
		
		JPanel virusTotal = new JPanel();
		virusTotal.add(virusTotalSubmit);
		virusTotal.add(virusTotalRetrieve);
		
		
		
		GridLayout legendGL = new GridLayout(0,1);
		
		legendGL.setHgap(4);
		legendGL.setVgap(4);
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(legendGL);
		mainPanel.add(fetchURLList);
		mainPanel.add(googleSafeBrowsingScan);
		mainPanel.add(virusTotal);
		
		
		getContentPane().add(mainPanel, BorderLayout.PAGE_START);
		
		pack();
		setVisible(true);
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("fetchURLList")){
			if(ClientPot.retrieveURLsfromMail()!=null){
				JOptionPane.showMessageDialog(null, "the urls list is retrieved and saved");				
			}
			else{
				JOptionPane.showMessageDialog(null, "Error !");
			}
			
		}
		File file = null;
		if(e.getActionCommand().equals("googleSBScan")||e.getActionCommand().equals("virusTotalSubmit")||e.getActionCommand().equals("virusTotalRetrieve")){
			JFileChooser chooser = new JFileChooser();
			chooser = new JFileChooser(); 
		    chooser.setCurrentDirectory(new java.io.File("."));
		    chooser.setDialogTitle("Select file with URLs");
		    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		    chooser.setAcceptAllFileFilterUsed(true);
		   
		    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
		    	 file = chooser.getSelectedFile();
		        }
		}
		
		if(e.getActionCommand().equals("googleSBScan")){
			
		    try {
				String outputFile = AnalysisEngine.scanUsingGoogleSafeBrowsing(file);
				JOptionPane.showMessageDialog(null, "                                 Scan Succesful ! \nThe output is stored in "+outputFile);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(e.getActionCommand().equals("virusTotalSubmit")){
			if(AnalysisEngine.scanURLsUsingVirusTotal(file)==1){
				JOptionPane.showMessageDialog(null, "                                 Submission Succesful ! \nPlease wait for a couple of minutes before retrieving the report\n\t\t (Max 5760 urls per day)");

			}
		}
		if(e.getActionCommand().equals("virusTotalRetrieve")){
			try {
				String outputFile = AnalysisEngine.retrieveReportVirusTotal(file);
				JOptionPane.showMessageDialog(null, "                                 Retrieval Succesful ! \nThe output is stored in "+outputFile);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

		
	}
}
