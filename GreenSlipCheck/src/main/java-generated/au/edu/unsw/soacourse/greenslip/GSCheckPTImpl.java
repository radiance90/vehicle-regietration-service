package au.edu.unsw.soacourse.greenslip;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.jws.WebService;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import au.edu.unsw.soacourse.greenslipdefinitions.ObjectFactory;
import au.edu.unsw.soacourse.greenslipdefinitions.ApprovalType;
import au.edu.unsw.soacourse.greenslipdefinitions.RenewalInputType;

@WebService(endpointInterface = "au.edu.unsw.soacourse.greenslip.GSCheckPT")
public class GSCheckPTImpl implements GSCheckPT {

	@Override
	public ApprovalType gsCheck(RenewalInputType renewalreq) {
		// TODO Auto-generated method stub
		ObjectFactory factory = new ObjectFactory();
		ApprovalType res = factory.createApprovalType();
		
		ArrayList<RecordBean> records = null;
		//ServiceContext context = g
		//InputSource xmlFile = new InputSource(Context.getResourceAsStream("/WEB-INF/ands_registry_dc.xml"));
		 
	    try {
	    	InputSource xmlFile =  new InputSource(new FileInputStream(new File("/Users/Henry/Downloads/GreenSlip_Database.xml")));
	    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	        Document doc = builder.parse(xmlFile);
	        RecordHandler handler = new RecordHandler();
	        records = handler.translateToRecords(doc);
	    }
	    catch (Exception e) {
	    	res.setPassed("Green Slip Provider Called - Error");
			return res;
	    }
		for(int i = 0; i < records.size(); i++) {
			if (renewalreq.getFirstName().equals(records.get(i).getFirst()) &&
				renewalreq.getLastName().equals(records.get(i).getLast()) &&
				renewalreq.getRego().equals(records.get(i).getRego()) &&
				records.get(i).getValid().equals("Yes")) {
				res.setPassed("Green Slip Provider Called - Payment Check Passed");
				return res;
			}
			else if (renewalreq.getFirstName().equals(records.get(i).getFirst()) &&
					renewalreq.getLastName().equals(records.get(i).getLast()) &&
					renewalreq.getRego().equals(records.get(i).getRego()) &&
					records.get(i).getValid().equals("No")) {
					res.setPassed("Green Slip Provider Called - Payment Check Failed");
					return res;
			}
		}
		res.setPassed("Green Slip Provider Called - Not Found");
		return res;
	}

}