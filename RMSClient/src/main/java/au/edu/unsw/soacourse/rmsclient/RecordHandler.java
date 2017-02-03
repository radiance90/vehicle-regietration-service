package au.edu.unsw.soacourse.rmsclient;
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
	
	
	
	public ArrayList<RecordBean> getRecords(Document doc){
		
		
		NodeList NoticeNodes = doc.getElementsByTagName("Entry");
		ArrayList<RecordBean> noticelist = new ArrayList<RecordBean>();
		Node n=NoticeNodes.item(0);
		NodeList noticeElements = n.getChildNodes();
		RecordBean b = new RecordBean();
		for (int j=0;j<noticeElements.getLength();j++ ){
			Node e = noticeElements.item(j);
			//System.out.println(e.getNodeName());
			//logger.info(e.getNodeName()+":"+e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("_rid"))
				b.set_rid(e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("RegistrationNumber"))
				b.setRego(e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("LastName"))
				b.setLast(e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("FirstName"))
				b.setFirst(e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("DriversLicenseNo"))
				b.setLicense(e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("Email"))
				b.setEmail(e.getTextContent());
			if(e.getNodeName().equalsIgnoreCase("RegistrationValidTill"))
				b.setValid(e.getTextContent());
			
		}
		noticelist.add(b);
		
		return noticelist;
		
		
		
	}

}
