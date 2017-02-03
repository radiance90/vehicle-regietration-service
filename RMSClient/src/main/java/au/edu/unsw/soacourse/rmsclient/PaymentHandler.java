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


public class PaymentHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public PaymentHandler(){
	}
	
	public ArrayList<PaymentBean> getPayment(Document doc){
		
		
		NodeList PaymentNodes = doc.getElementsByTagName("Entry");
		ArrayList<PaymentBean> paymentlist = new ArrayList<PaymentBean>();
		
		for(int i=0;i<PaymentNodes.getLength();i++){
			Node n=PaymentNodes.item(i);
			NodeList paymentElements = n.getChildNodes();
			PaymentBean b = new PaymentBean();
			for (int j=0;j<paymentElements.getLength();j++ ){
				Node e = paymentElements.item(j);
				//System.out.println(e.getNodeName());
				//logger.info(e.getNodeName()+":"+e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("_pid"))
					b.set_pid(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("_nid"))
					b.set_nid(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("Amount"))
					b.setAmount(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("Card"))
					b.setCard(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("Date"))
					b.setDate(e.getTextContent());
			}
			paymentlist.add(b);
		}
		
		return paymentlist;
		
	}

}
