package au.edu.unsw.soacourse.rms;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class RecordHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public RecordHandler(){
	}
	
	public void writeRecord(ArrayList<RecordBean> records) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("/Users/Henry/Downloads/Database.xml");
			writer.println("<Registrations>");
			for(int i = 0; i < records.size(); i++) {
				writer.println("\t<Entry>");
				writer.println("\t\t<_rid>"+records.get(i).get_rid()+"</_rid>");
				writer.println("\t\t<RegistrationNumber>"+records.get(i).getRego()+"</RegistrationNumber>");
				writer.println("\t\t<Driver>");
				writer.println("\t\t\t<LastName>"+records.get(i).getLast()+"</LastName>");
				writer.println("\t\t\t<FirstName>"+records.get(i).getFirst()+"</FirstName>");
				writer.println("\t\t\t<DriversLicenseNo>"+records.get(i).getLicense()+"</DriversLicenseNo>");
				writer.println("\t\t\t<Email>"+records.get(i).getEmail()+"</Email>");
				writer.println("\t\t</Driver>");
				writer.println("\t\t<RegistrationValidTill>"+records.get(i).getValid()+"</RegistrationValidTill>");
				writer.println("\t</Entry>");
			}
			writer.println("</Registrations>");
			writer.close();
		}catch(Exception e) {
			
		}
	}
	
	public ArrayList<RecordBean> getRecords(){
		Document doc = null;
		try {
	    	InputSource xmlFile =  new InputSource(new FileInputStream(new File("/Users/Henry/Downloads/Database.xml")));
	    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	        doc = builder.parse(xmlFile);
	        
	        
	    }
	    catch (Exception e) {
	    	
	    }
		
		NodeList RecordNodes = doc.getElementsByTagName("Entry");
		ArrayList<RecordBean> recordlist = new ArrayList<RecordBean>();
		
		for(int i=0;i<RecordNodes.getLength();i++){
			Node n=RecordNodes.item(i);
			NodeList recordElements = n.getChildNodes();
			RecordBean b = new RecordBean();
			for (int j=0;j<recordElements.getLength();j++ ){
				Node e = recordElements.item(j);
				//System.out.println(e.getNodeName());
				//logger.info(e.getNodeName()+":"+e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("_rid"))
					b.set_rid(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("RegistrationNumber"))
					b.setRego(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("Driver")) {
					NodeList driver = e.getChildNodes();
					b.setLast(driver.item(1).getTextContent());
					b.setFirst(driver.item(3).getTextContent());
					b.setLicense(driver.item(5).getTextContent());
					b.setEmail(driver.item(7).getTextContent());
				}
				if(e.getNodeName().equalsIgnoreCase("RegistrationValidTill"))
					b.setValid(e.getTextContent());
			}
			recordlist.add(b);
		}
		
		return recordlist;
		
	}

}
