package au.edu.unsw.soacourse.pinkslip;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.jws.WebService;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;




import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import au.edu.unsw.soacourse.pinkslipdefinitions.ObjectFactory;
import au.edu.unsw.soacourse.pinkslipdefinitions.ApprovalType;
import au.edu.unsw.soacourse.pinkslipdefinitions.RenewalInputType;
import au.edu.unsw.soacourse.pinkslipdefinitions.AgeType;
@WebService(endpointInterface = "au.edu.unsw.soacourse.pinkslip.PSCheckPT")
public class PSCheckPTImpl implements PSCheckPT {

	@Override
	public ApprovalType psCheck(RenewalInputType renewalreq) {
		// TODO Auto-generated method stub
		ObjectFactory factory = new ObjectFactory();
		ApprovalType res = factory.createApprovalType();
		
		ArrayList<RecordBean> records = null;
		
	    try {
	    	InputSource xmlFile =  new InputSource(new FileInputStream(new File("/Users/Henry/Downloads/PinkSlip_Database.xml")));
	    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	        Document doc = builder.parse(xmlFile);
	        RecordHandler handler = new RecordHandler();
	        records = handler.translateToRecords(doc);
	    }
	    catch (Exception e) {
	    	res.setPassed("Pink Slip Provider Called (Payment Check) - Error");
			return res;
	    }
		for(int i = 0; i < records.size(); i++) {
			if (renewalreq.getFirstName().equals(records.get(i).getFirst()) &&
				renewalreq.getLastName().equals(records.get(i).getLast()) &&
				renewalreq.getRego().equals(records.get(i).getRego()) &&
				records.get(i).getValid().equals("Yes")) {
				res.setPassed("Pink Slip Provider Called (Payment Check) - Payment Check Passed");
				return res;
			}
			else if (renewalreq.getFirstName().equals(records.get(i).getFirst()) &&
					renewalreq.getLastName().equals(records.get(i).getLast()) &&
					renewalreq.getRego().equals(records.get(i).getRego()) &&
					records.get(i).getValid().equals("No")) {
					res.setPassed("Pink Slip Provider Called (Payment Check) - Payment Check Failed");
					return res;
			}
		}
		res.setPassed("Pink Slip Provider Called (Payment Check) - Not Found");
		return res;
	}
	
	@Override
	public AgeType vehicleAge(RenewalInputType renewalreq) {
		ObjectFactory factory = new ObjectFactory();
		AgeType res = factory.createAgeType();
		
		ArrayList<RecordBean> records = null;
		
	    try {
	    	InputSource xmlFile =  new InputSource(new FileInputStream(new File("/Users/Henry/Downloads/PinkSlip_Database.xml")));
	    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	        Document doc = builder.parse(xmlFile);
	        RecordHandler handler = new RecordHandler();
	        records = handler.translateToRecords(doc);
	    }
	    catch (Exception e) {
	    	res.setAge("Pink Slip Provider Called (Age Check) - Technical Fault, Please Try Again Later");
			return res;
	    }
	    try{
		    for(int i = 0; i < records.size(); i++) {
				if (renewalreq.getFirstName().equals(records.get(i).getFirst()) &&
					renewalreq.getLastName().equals(records.get(i).getLast()) &&
					renewalreq.getRego().equals(records.get(i).getRego())) {
					int curr_year = Calendar.getInstance().get(Calendar.YEAR);
					if(curr_year >= Integer.parseInt(records.get(i).getYear())+5) {
						res.setAge("Pink Slip Provider Called (Age Check) - Vehicle is more than 5 years old");
						return res;
					}
					else if((curr_year >= Integer.parseInt(records.get(i).getYear())) && (curr_year < Integer.parseInt(records.get(i).getYear())+5)) {
						res.setAge("Pink Slip Provider Called (Age Check) - Vehicle is less than 5 years old");
						return res;
					}
					else {
						res.setAge("Pink Slip Provider Called (Age Check) - Technical Fault, Please Try Again Later");
						return res;
					}
				}
				
			}
	    }catch (Exception e) {
	    	res.setAge("Pink Slip Provider Called (Age Check) - Technical Fault, Please Try Again Later");
			return res;
	    }
		res.setAge("Pink Slip Provider Called (Age Check) - Not Found");
		return res;
	}

}