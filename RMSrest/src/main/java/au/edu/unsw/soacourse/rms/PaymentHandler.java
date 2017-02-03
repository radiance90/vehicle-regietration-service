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


public class PaymentHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public PaymentHandler(){
	}
	public void addPayment(ArrayList<PaymentBean> paymentlist, String _pid,String _nid, String amount, String card, String date) {
		PaymentBean newbean = new PaymentBean(_pid, _nid, amount, card, date);
		paymentlist.add(newbean);
	}
	public void writePayment(ArrayList<PaymentBean> payments) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("/Users/Henry/Downloads/Payment.xml");
			writer.println("<Registrations>");
			for(int i = 0; i < payments.size(); i++) {
				writer.println("<Entry>");
				writer.println("<_pid>"+payments.get(i).get_pid()+"</_pid>");
				writer.println("<_nid>"+payments.get(i).get_nid()+"</_nid>");
				writer.println("<Amount>"+payments.get(i).getAmount()+"</Amount>");
				writer.println("<Card>"+payments.get(i).getCard()+"</Card>");
				writer.println("<Date>"+payments.get(i).getDate()+"</Date>");
				writer.println("</Entry>");
			}
			writer.println("</Registrations>");
			writer.close();
		}catch(Exception e) {
			
		}
	}
	public ArrayList<PaymentBean> getPayment(){
		Document doc = null;
		try {
	    	InputSource xmlFile =  new InputSource(new FileInputStream(new File("/Users/Henry/Downloads/Payment.xml")));
	    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	        doc = builder.parse(xmlFile);
	        
	        
	    }
	    catch (Exception e) {
	    	
	    }
		
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
