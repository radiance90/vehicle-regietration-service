package au.edu.unsw.soacourse.rmsclient;

import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.crypto.dsig.XMLObject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import au.edu.unsw.soacourse.greenslip.GSCheckPT;
import au.edu.unsw.soacourse.greenslipdefinitions.ApprovalType;
import au.edu.unsw.soacourse.greenslipdefinitions.RenewalInputType;
import au.edu.unsw.soacourse.rms.*;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import com.apple.eawt.Application;

@Controller
public class control {

   
	@Autowired
   private GSCheckPT simple; //this is instantiated by jaxws:client id=simple in dispatcher-servlet.xml
	
	//TODO auto check
//   @RequestMapping(value="/check",method=RequestMethod.GET)
//   public String check(ModelMap model) throws Exception {
//	  RenewalInputType request = new au.edu.unsw.soacourse.greenslipdefinitions.ObjectFactory().createRenewalInputType();
//	  request.setFirstName("Nishad");
//	  request.setLastName("Nima");  
//	  request.setRego("YYZ908");	 
//	  ApprovalType response = simple.gsCheck(request);
//      model.addAttribute("returnData", response.getPassed());
//      return "index";
//   }

    
   @RequestMapping(value="/officer/generate",method=RequestMethod.GET)
   public String generate(ModelMap model) throws Exception {
	   WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
		String s = "";
		Form form = new Form();
		restClient.path("/officer/generate").type(MediaType.APPLICATION_FORM_URLENCODED);
		restClient.header("key", "officer");
		Response response;
		response = restClient.post(form);
		s = response.readEntity(String.class);
		int found = 0;
		if(s == null || s.isEmpty()) {
			found = 0;
			model.addAttribute("found", found);
			//System.out.println("yes");
		}
		else {
			found = 1;
			model.addAttribute("found", found);
			s = response.readEntity(String.class);
			Document document = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;  
		    try  
		    {  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( s ) ) );  
		    } catch (Exception e) {  
		        //e.printStackTrace();  
		    } 
		    ArrayList<ListBean> list = null;
	        ListHandler handler = new ListHandler();
	        list = handler.getList(document);
			//JSONObject returnData = new JSONObject(s);
			model.addAttribute("returnData", list);
		}
		
		
		
      return "generate";
   }
   
   
   @RequestMapping(value="/officer_home",method=RequestMethod.GET)
   public String home(ModelMap model) throws Exception {
	   
      return "officer_home";
   }

   
   
  
   @RequestMapping(value="/list",method=RequestMethod.GET)
   public String list(ModelMap model) throws Exception {
	   WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
		String s = "";
		
		restClient.path("/list");
		restClient.header("key", "officer");
		Response response;
		response = restClient.get();
		s = response.readEntity(String.class);
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( s ) ) );  
	    } catch (Exception e) {  
	        //e.printStackTrace();  
	    } 
	    ArrayList<ListBean> list = null;
        ListHandler handler = new ListHandler();
        list = handler.getList(document);
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("returnData", list);
		
		
		
		
      return "list";
   }
   
 //TODO notice get
   @RequestMapping(value="/notice",method=RequestMethod.GET)
   public String notice(ModelMap model,@RequestParam("role") String role,@RequestParam("id") String id) throws Exception {
	   
	   	WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
		String s = "";
		
		restClient.path("/notices/"+id);
		restClient.header("key", role);
		Response response;
		response = restClient.get();
		s = response.readEntity(String.class);
		//System.out.println(s);
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( s ) ) );  
	    } catch (Exception e) {  
	        //e.printStackTrace();  
	    } 
	    ArrayList<NoticeBean> list = null;
        NoticeHandler handler = new NoticeHandler();
        list = handler.getNotice(document);
        //System.out.println(list.get(0).getStatus());
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("list", list);
		ArrayList<LinksBean> list2 = null;
        
        list2 = handler.getLinks(document);
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("list2", list2);
		
		model.addAttribute("role", role);
		
		return "notice";
   }
   
   @RequestMapping(value="/car",method=RequestMethod.GET)
   public String car(ModelMap model,@RequestParam("rid") String rid) throws Exception {
	   
	   	WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
		String s = "";
		
		restClient.path("/car/"+rid);
		//restClient.header("key", role);
		Response response;
		response = restClient.get();
		s = response.readEntity(String.class);
		//System.out.println(s);
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( s ) ) );  
	    } catch (Exception e) {  
	        //e.printStackTrace();  
	    } 
	    ArrayList<RecordBean> list = null;
        RecordHandler handler = new RecordHandler();
        list = handler.getRecords(document);
        //System.out.println(list.get(0).getStatus());
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("list", list);
		
		
      return "registration";
   }
   
   @RequestMapping(value="/update",method=RequestMethod.GET)
   public String update(ModelMap model,@RequestParam("role") String role,@RequestParam("result") String result,@RequestParam("nid") String nid) throws Exception {
	   	if(result.equals("check")) {
	   		RenewalInputType request = new au.edu.unsw.soacourse.greenslipdefinitions.ObjectFactory().createRenewalInputType();
	   		WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
			String s = "";
			
			restClient.path("/notices/"+nid);
			restClient.header("key", role);
			Response response;
			response = restClient.get();
			s = response.readEntity(String.class);
			//System.out.println(s);
			Document document = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;  
		    try  
		    {  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( s ) ) );  
		    } catch (Exception e) {  
		        //e.printStackTrace();  
		    } 
		    ArrayList<NoticeBean> list = null;
	        NoticeHandler handler = new NoticeHandler();
	        list = handler.getNotice(document);
	        String rid = list.get(0).get_rid();
	        
	        
	        restClient = WebClient.create("http://localhost:8080/RMSrest/");
			s = "";
			
			restClient.path("/car/"+rid);
			//restClient.header("key", role);
			response = restClient.get();
			s = response.readEntity(String.class);
			//System.out.println(s);
			document = null;
			factory = DocumentBuilderFactory.newInstance();  
		     
		    try  
		    {  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( s ) ) );  
		    } catch (Exception e) {  
		        //e.printStackTrace();  
		    } 
		    ArrayList<RecordBean> list2 = null;
	        RecordHandler handler2 = new RecordHandler();
	        list2 = handler2.getRecords(document);
	        
	        
	   		request.setFirstName(list2.get(0).getFirst());
	   		request.setLastName(list2.get(0).getLast());  
	   		request.setRego(list2.get(0).getRego());	 
	   		ApprovalType myres = simple.gsCheck(request);
	        model.addAttribute("returnData", myres.getPassed());
	        return "index";
	   	}
	   	else if(result.equals("archived")) {
	   		WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
			String s = "";
			Form form = new Form();
			restClient.path("/notices/"+nid+"/"+result);
			restClient.header("key", role);
			Response response;
			response = restClient.delete();

			restClient = WebClient.create("http://localhost:8080/RMSrest/");
			s = "";
			
			restClient.path("/notices/"+nid);
			restClient.header("key", role);
			
			response = restClient.get();
			s = response.readEntity(String.class);
			//System.out.println(s);
			Document document = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;  
		    try  
		    {  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( s ) ) );  
		    } catch (Exception e) {  
		        //e.printStackTrace();  
		    } 
		    ArrayList<NoticeBean> list = null;
	        NoticeHandler handler = new NoticeHandler();
	        list = handler.getNotice(document);
	        //System.out.println(list.get(0).getStatus());
			//JSONObject returnData = new JSONObject(s);
			model.addAttribute("list", list);
			ArrayList<LinksBean> list2 = null;
	        
	        list2 = handler.getLinks(document);
			//JSONObject returnData = new JSONObject(s);
			model.addAttribute("list2", list2);
			model.addAttribute("role", role);
			return "notice";
	   	}
	   	else if(result.equals("checkpay")) {
	   		WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
			String s = "";
			
			restClient.path("/payments/"+nid).type(MediaType.APPLICATION_FORM_URLENCODED);
			restClient.header("key", role);
			Response response;
			response = restClient.get();
			s = response.readEntity(String.class);
			Document document = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;  
		    try  
		    {  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( s ) ) );  
		    } catch (Exception e) {  
		        //e.printStackTrace();  
		    } 
		    ArrayList<PaymentBean> list = null;
	       PaymentHandler handler = new PaymentHandler();
	        list = handler.getPayment(document);
	        //System.out.println(list.get(0).getStatus());
			//JSONObject returnData = new JSONObject(s);
			model.addAttribute("list", list);
			model.addAttribute("role", role);
	   		return "payment";
	   	}
	   	else if(result.equals("awaiting")) {
	   		model.addAttribute("nid", nid);
	   		return "createpayment";
	   	}
	   	else if(result.equals("pay")) {
	   		model.addAttribute("nid", nid);
	   		return "pay";
	   	}
	   	
	   	else {
		   	WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
			String s = "";
			Form form = new Form();
			restClient.path("/notices/"+nid+"/"+result);
			restClient.header("key", role);
			Response response;
			response = restClient.put(form);

			restClient = WebClient.create("http://localhost:8080/RMSrest/");
			s = "";
			
			restClient.path("/notices/"+nid);
			restClient.header("key", role);
			
			response = restClient.get();
			s = response.readEntity(String.class);
			//System.out.println(s);
			Document document = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
		    DocumentBuilder builder;  
		    try  
		    {  
		        builder = factory.newDocumentBuilder();  
		        document = builder.parse( new InputSource( new StringReader( s ) ) );  
		    } catch (Exception e) {  
		        //e.printStackTrace();  
		    } 
		    ArrayList<NoticeBean> list = null;
	        NoticeHandler handler = new NoticeHandler();
	        list = handler.getNotice(document);
	        //System.out.println(list.get(0).getStatus());
			//JSONObject returnData = new JSONObject(s);
			model.addAttribute("list", list);
			ArrayList<LinksBean> list2 = null;
	        
	        list2 = handler.getLinks(document);
			//JSONObject returnData = new JSONObject(s);
			model.addAttribute("list2", list2);
			model.addAttribute("role", role);
			return "notice";	
	   	}
   }
   
   @RequestMapping(value="/createpayment",method=RequestMethod.POST)
   public String createamount(ModelMap model,@FormParam("nid") String nid, @FormParam("amount") String amount) throws Exception {
	   	WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
		String s = "";
		Form form = new Form();
		form.param("amount", amount);
		restClient.path("/notices/"+nid+"/generate").type(MediaType.APPLICATION_FORM_URLENCODED);
		restClient.header("key", "officer");
		Response response;
		response = restClient.post(form);
		s = response.readEntity(String.class);
		restClient = WebClient.create("http://localhost:8080/RMSrest/");
		s = "";
		
		restClient.path("/notices/"+nid);
		restClient.header("key", "officer");
		
		response = restClient.get();
		s = response.readEntity(String.class);
		//System.out.println(s);
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( s ) ) );  
	    } catch (Exception e) {  
	        //e.printStackTrace();  
	    } 
	    ArrayList<NoticeBean> list = null;
        NoticeHandler handler = new NoticeHandler();
        list = handler.getNotice(document);
        //System.out.println(list.get(0).getStatus());
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("list", list);
		ArrayList<LinksBean> list2 = null;
        
        list2 = handler.getLinks(document);
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("list2", list2);
		model.addAttribute("role", "officer");
		return "notice";
   }
   
   
   @RequestMapping(value="/pay",method=RequestMethod.POST)
   public String pay(ModelMap model,@FormParam("nid") String nid, @FormParam("card") String card, @FormParam("date") String date) throws Exception {
	   	WebClient restClient = WebClient.create("http://localhost:8080/RMSrest/");
		String s = "";
		Form form = new Form();
		form.param("card", card);
		form.param("date", date);
		restClient.path("/payments/"+nid).type(MediaType.APPLICATION_FORM_URLENCODED);
		restClient.header("key", "driver");
		Response response;
		response = restClient.put(form);
		s = response.readEntity(String.class);
		
		restClient = WebClient.create("http://localhost:8080/RMSrest/");
		s = "";
		
		restClient.path("/notices/"+nid);
		restClient.header("key", "driver");
		
		response = restClient.get();
		s = response.readEntity(String.class);
		//System.out.println(s);
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        document = builder.parse( new InputSource( new StringReader( s ) ) );  
	    } catch (Exception e) {  
	        //e.printStackTrace();  
	    } 
	    ArrayList<NoticeBean> list = null;
        NoticeHandler handler = new NoticeHandler();
        list = handler.getNotice(document);
        //System.out.println(list.get(0).getStatus());
		//JSONObject returnData = new JSONObject(s);
		model.addAttribute("list", list);
		ArrayList<LinksBean> list2 = null;
        
        list2 = handler.getLinks(document);
		//JSONObject returnData = new JSONObject(s);
        model.addAttribute("role", "driver");
		model.addAttribute("list2", list2);
		return "notice";
   }	
   
   
   
   
   
   
   
   
   
   
   

}