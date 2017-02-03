package au.edu.unsw.soacourse.rmsclient;
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
	
	
	public ArrayList<NoticeBean> getNotice(Document doc){
		
		
		
		NodeList NoticeNodes = doc.getElementsByTagName("notice");
		ArrayList<NoticeBean> noticelist = new ArrayList<NoticeBean>();
		Node n=NoticeNodes.item(0);
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
		
		return noticelist;
		
	}
	
public ArrayList<LinksBean> getLinks(Document doc){
		
		
		
		NodeList LinksNodes = doc.getElementsByTagName("Info");
		ArrayList<LinksBean> list = new ArrayList<LinksBean>();
		
		for(int i=0;i<LinksNodes.getLength();i++){
			Node n=LinksNodes.item(i);
			NodeList noticeElements = n.getChildNodes();
			LinksBean b = new LinksBean();
			for (int j=0;j<noticeElements.getLength();j++ ){
				Node e = noticeElements.item(j);
				//System.out.println(e.getNodeName());
				//logger.info(e.getNodeName()+":"+e.getTextContent());
				if(e.getNodeName().equalsIgnoreCase("action"))
					b.setAction(e.getTextContent());
				
				if(e.getNodeName().equalsIgnoreCase("link"))
					b.setLink(e.getTextContent());
				
			}
			list.add(b);
		}
		
		return list;
		
	}
	

}
