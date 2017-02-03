package au.edu.unsw.soacourse.greenslip;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RecordHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public RecordHandler(){
	}
	
	public ArrayList<RecordBean> translateToRecords(Document doc){
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
				if(e.getNodeName().equalsIgnoreCase("_cid"))
					b.set_cid(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("RegistrationNumber"))
					b.setRego(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("Driver")) {
					NodeList driver = e.getChildNodes();
					b.setLast(driver.item(1).getTextContent());
					b.setFirst(driver.item(3).getTextContent());
					b.setLicense(driver.item(5).getTextContent());
					b.setEmail(driver.item(7).getTextContent());
				}
				if(e.getNodeName().equalsIgnoreCase("CTPDetails")) {
					NodeList CTP = e.getChildNodes();
					b.setPolicy(CTP.item(1).getTextContent());
					b.setValid(CTP.item(3).getTextContent());
				}
			}
			recordlist.add(b);
		}
		
		return recordlist;
		
	}

}
