package au.edu.unsw.soacourse.rms;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class NoticeHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public NoticeHandler(){
	}
	public void addNotice(ArrayList<NoticeBean> noticelist, String _nid,String _rid, String status) {
		NoticeBean newbean = new NoticeBean(_nid, _rid, status);
		noticelist.add(newbean);
	}
	public void writeNotice(ArrayList<NoticeBean> notices) {
		PrintWriter writer;
		try {
			writer = new PrintWriter("/Users/Henry/Downloads/Notice.xml");
			writer.println("<Registrations>");
			for(int i = 0; i < notices.size(); i++) {
				writer.println("<Entry>");
				writer.println("<_nid>"+notices.get(i).get_nid()+"</_nid>");
				writer.println("<_rid>"+notices.get(i).get_rid()+"</_rid>");
				writer.println("<Status>"+notices.get(i).getStatus()+"</Status>");
				writer.println("</Entry>");
			}
			writer.println("</Registrations>");
			writer.close();
		}catch(Exception e) {
			
		}
	}
	public ArrayList<NoticeBean> getNotice(){
		Document doc = null;
		try {
	    	InputSource xmlFile =  new InputSource(new FileInputStream(new File("/Users/Henry/Downloads/Notice.xml")));
	    	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = builderFactory.newDocumentBuilder();
	        doc = builder.parse(xmlFile);
	        
	        
	    }
	    catch (Exception e) {
	    	
	    }
		
		NodeList NoticeNodes = doc.getElementsByTagName("Entry");
		ArrayList<NoticeBean> noticelist = new ArrayList<NoticeBean>();
		
		for(int i=0;i<NoticeNodes.getLength();i++){
			Node n=NoticeNodes.item(i);
			NodeList noticeElements = n.getChildNodes();
			NoticeBean b = new NoticeBean();
			for (int j=0;j<noticeElements.getLength();j++ ){
				Node e = noticeElements.item(j);
				//System.out.println(e.getNodeName());
				//logger.info(e.getNodeName()+":"+e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("_rid"))
					b.set_rid(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("_nid"))
					b.set_nid(e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("Status"))
					b.setStatus(e.getTextContent());
			}
			noticelist.add(b);
		}
		
		return noticelist;
		
	}

}
