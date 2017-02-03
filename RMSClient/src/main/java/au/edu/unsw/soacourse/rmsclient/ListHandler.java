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


public class ListHandler {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	
	public ListHandler(){
	}
	public void addList(ArrayList<ListBean> list, String _nid) {
		ListBean newbean = new ListBean(_nid);
		list.add(newbean);
	}
	
	public ArrayList<ListBean> getList(Document doc){
		
		
		
		NodeList ListNodes = doc.getElementsByTagName("nid");
		ArrayList<ListBean> list = new ArrayList<ListBean>();
		
		for(int i=0;i<ListNodes.getLength();i++){
			Node n=ListNodes.item(i);
			
			ListBean b = new ListBean();
			b.set_nid(n.getTextContent());
			
			list.add(b);
		}
		
		return list;
		
	}

}
