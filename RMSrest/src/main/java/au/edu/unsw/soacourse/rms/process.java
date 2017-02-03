package au.edu.unsw.soacourse.rms;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.web.bind.annotation.RequestParam;

import au.edu.unsw.soacourse.rms.RecordBean;
import au.edu.unsw.soacourse.rms.RecordHandler;

@Path("/")
public class process {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	HttpHeaders headers;
	@POST
	@Path("officer/generate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response generate(@Context HttpHeaders headers) throws IOException {
		try {
			//System.out.println(headers.getRequestHeader("key").get(0));
		if(!headers.getRequestHeader("key").get(0).equals("officer")) {
			ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
			builder.type("text/html");
			builder.entity("<h3>Unauthorised</h3>");
			throw new WebApplicationException(builder.build());
			}
		}
		catch (Exception e) {
			ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
			builder.type("text/html");
			builder.entity("<h3>Unauthorised</h3>");
			throw new WebApplicationException(builder.build());
		}
		String locations = "<Entry>\n";
		int count = 0;
		ArrayList<RecordBean> records = null;
		RecordHandler handler = new RecordHandler();
        records = handler.getRecords();
        ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        
        for(int i = 0; i < records.size(); i++) {
        	String dateString = records.get(i).getValid();
        	//System.out.println(dateString );
        	try {
        		//System.out.println("1" );
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateString);
				//System.out.println("2" );
				int cur_year = Calendar.getInstance().get(Calendar.YEAR);
				int cur_month = Calendar.getInstance().get(Calendar.MONTH);
				int cur_day = Calendar.getInstance().get(Calendar.DATE);
				Calendar cal = Calendar.getInstance();
			    cal.setTime(date);
				int valid_year = cal.get(Calendar.YEAR);
				int valid_month = cal.get(Calendar.MONTH);
				int valid_day = cal.get(Calendar.DATE);
				boolean less = false;
				//System.out.println(String.valueOf(cur_year) + String.valueOf(cur_month) +String.valueOf(cur_day) );
				//System.out.println(String.valueOf(valid_year) + String.valueOf(valid_month) +String.valueOf(valid_day) );
				if(((valid_year*12+valid_month-1)<(cur_year*12+cur_month)) || (((valid_year*12+valid_month-1) == (cur_year*12+cur_month)) && (valid_day<=cur_day)) ) {
					less = true;
					//System.out.println("1");
				}
				if((less == true) && !date.before(Calendar.getInstance().getTime())) {
					//System.out.println("1");
					String driver_rid = records.get(i).get_rid();
					boolean found = false;
					for(int j = 0; j < notices.size(); j++) {
						if(notices.get(j).get_rid().equals(driver_rid) ) {
							found = true;
							if(notices.get(j).getStatus().equals("cancelled") || notices.get(j).getStatus().equals("archived") || notices.get(j).getStatus().equals("completed")) {
								notices.get(j).setStatus("created");
								//locations += records.get(i).getEmail() + ";";
								locations += "<nid>/notices/"+ notices.get(j).get_nid() +"</nid>\n";
								count++;
							}
							break;
						}
					}
					if(!found) {
						locations += "<nid>/notices/"+String.valueOf(notices.size()) +"</nid>\n";
						handler2.addNotice(notices, String.valueOf(notices.size()), records.get(i).get_rid(), "created");
						//locations += records.get(i).getEmail() + ";";
						//System.out.println("1111");
						count++;
					}
				}
			} catch (ParseException e) {
				continue;
			}
        	
        }
        //System.out.print(locations);
        locations += "</Entry>\n";
        if(count == 0) {
        	locations = "";
        }
        Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(locations).build();
        handler2.writeNotice(notices);
        return response;
	}
	
	@GET
	@Path("list/")
	@Produces(MediaType.APPLICATION_XML)
	public Response list(@Context HttpHeaders headers) {
		
		try {
			//System.out.println(headers.getRequestHeader("key").get(0));
		if(!headers.getRequestHeader("key").get(0).equals("officer")) {
			ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
			builder.type("text/html");
			builder.entity("<h3>Unauthorised</h3>");
			throw new WebApplicationException(builder.build());
			}
		}
		catch (Exception e) {
			ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
			builder.type("text/html");
			builder.entity("<h3>Unauthorised</h3>");
			throw new WebApplicationException(builder.build());
		}
		//String msg = "{notices:[";
		String msg = "<Entry>\n";
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        for(int i = 0; i < notices.size(); i++) {
        	msg += "<nid>" + notices.get(i).get_nid() + "</nid>\n";
//        	if(i == 0) {
//        		msg += "{\"nid\":\"" + notices.get(i).get_nid() +"\"}";
//        	}
//        	else {
//        		msg += ",{\"nid\":\"" + notices.get(i).get_nid() +"\"}";
//        	}
        }
        msg += "</Entry>\n";
        //msg += "]}";
        Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	
	//TODO return possible actions for different status
	@GET
	@Path("notices/{_nid}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getNotice(@Context HttpHeaders headers, @PathParam("_nid") String _nid) {
		String role = "unknown";
		try {
			//System.out.println(headers.getRequestHeader("key").get(0));
			if((!headers.getRequestHeader("key").get(0).equals("officer")) || (!headers.getRequestHeader("key").get(0).equals("driver"))) {
				role = headers.getRequestHeader("key").get(0);
			}
		}
		catch (Exception e) {
		}
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        String msg = "<Error>Not Found</Error>";
        for(int i = 0; i < notices.size(); i++) {
        	if(notices.get(i).get_nid().equals(_nid)) {
        		msg = "<Entry>\n<notice>\n";
	        	msg += "<_nid>" + notices.get(i).get_nid() + "</_nid>\n<_rid>" + notices.get(i).get_rid() +
	        				"</_rid>\n<Status>" + notices.get(i).getStatus() + "</Status>\n</notice>\n<links>\n";
	        	String status = notices.get(i).getStatus();
	        	if(status.equals("created") && role.equals("driver")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=requested&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>request</action>\n</Info>\n";
	        	}
	        	else if(status.equals("requested") && role.equals("driver")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=cancelled&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>cancel</action>\n</Info>\n";
	        	}
	        	else if(status.equals("cancelled") && role.equals("driver")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=requested&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>request</action>\n</Info>\n";
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=archived&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>delete</action>\n</Info>\n";
	        	}
	        	else if(status.equals("awaiting") && role.equals("driver")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=pay&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>pay</action>\n</Info>\n";
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=checkpay&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>check payment</action>\n</Info>\n";
	        	}
	        	else if(status.equals("rejected") && role.equals("driver")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=archived&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>delete</action>\n</Info>\n";
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=driver&amp;result=requested&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>request</action>\n</Info>\n";
	        	}
	        	else if(status.equals("requested") && role.equals("officer")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=under-review&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>review</action>\n</Info>\n";
	        	}
	        	else if(status.equals("under-review") && role.equals("officer")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=check&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>auto-check</action>\n</Info>\n";
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=accepted&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>accept</action>\n</Info>\n";
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=rejected&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>reject</action>\n</Info>\n";
	        	}
	        	else if(status.equals("accepted") && role.equals("officer")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=awaiting&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>create payment</action>\n</Info>\n";
	        	}
	        	else if(status.equals("awaiting") && role.equals("officer")) {
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=checkpay&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>check payment</action>\n</Info>\n";
	        		msg += "<Info>\n";
	        		msg += "<link>http://localhost:8080/RMSClient/update?role=officer&amp;result=completed&amp;nid=" + _nid + "</link>\n";
	        		msg += "<action>complete</action>\n</Info>\n";
	        	}
	        	msg += "</links>\n</Entry>";
        	}
        	
        }
        
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	
	@PUT
	@Path("notices/{_nid}/{status}")
	//@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response update(@Context HttpHeaders headers,@PathParam("_nid") String _nid,@PathParam("status") String status) throws IOException {
		if(status.equals("under-review") || status.equals("accepted") || status.equals("rejected") || status.equals("completed")) {
			try {
			//System.out.println(headers.getRequestHeader("key").get(0));
				if(!headers.getRequestHeader("key").get(0).equals("officer")) {
					ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
					builder.type("text/html");
					builder.entity("<h3>Unauthorised</h3>");
					throw new WebApplicationException(builder.build());
				}
			}
			catch (Exception e) {
				ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
				builder.type("text/html");
				builder.entity("<h3>Unauthorised</h3>");
				throw new WebApplicationException(builder.build());
			}
		}
		if(status.equals("cancelled") || status.equals("requested")) {
			try {
			//System.out.println(headers.getRequestHeader("key").get(0));
				if(!headers.getRequestHeader("key").get(0).equals("driver")) {
					ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
					builder.type("text/html");
					builder.entity("<h3>Unauthorised</h3>");
					throw new WebApplicationException(builder.build());
				}
			}
			catch (Exception e) {
				ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
				builder.type("text/html");
				builder.entity("<h3>Unauthorised</h3>");
				throw new WebApplicationException(builder.build());
			}
		}
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        String msg = "";
        
		if(status.equals("requested")) {
			for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("created") || notices.get(i).getStatus().equals("cancelled") || notices.get(i).getStatus().equals("rejected")) {
						notices.get(i).setStatus(status);
						handler2.writeNotice(notices);
						msg = "<URI>/notices/" + _nid + "</URI>";
						break;
					}
				}
			}
		}
		
		if(status.equals("cancelled")) {
			for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("requested")) {
						notices.get(i).setStatus(status);
						handler2.writeNotice(notices);
						msg = "<URI>/notices/" + _nid + "</URI>";
						break;
					}
				}
			}
		}
		if(status.equals("under-review")) {
			for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("requested")) {
						notices.get(i).setStatus(status);
						handler2.writeNotice(notices);
						msg = "<URI>/notices/" + _nid + "</URI>";
						break;
					}
				}
			}
		}
		
		if(status.equals("accepted")) {
			for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("under-review")) {
						notices.get(i).setStatus(status);
						handler2.writeNotice(notices);
						msg = "<URI>/notices/" + _nid + "</URI>";
						break;
					}
				}
			}
		}
		
		if(status.equals("rejected")) {
			for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("under-review")) {
						notices.get(i).setStatus(status);
						handler2.writeNotice(notices);
						msg = "<URI>/notices/" + _nid + "</URI>";
						break;
					}
				}
			}
		}
		
		if(status.equals("completed")) {
			for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("awaiting")) {
						notices.get(i).setStatus(status);
						handler2.writeNotice(notices);
						msg = "<URI>/notices/" + _nid + "</URI>";
						
						WebClient bookClient = WebClient.create("http://localhost:8080/RMSrest/");
						Form form = new Form();
						
						bookClient.path("/car/"+_nid).type(MediaType.APPLICATION_FORM_URLENCODED);
						bookClient.put(form);
						
						break;
					}
				}
			}
		}
		
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
		
	}
		
	@DELETE
	@Path("notices/{_nid}/archived")
	@Produces(MediaType.APPLICATION_XML)
	public Response archiveNotice(@Context HttpHeaders headers,@PathParam("_nid") String _nid) {
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        String msg = "";
		try {
			//System.out.println(headers.getRequestHeader("key").get(0));
				if(!headers.getRequestHeader("key").get(0).equals("driver")) {
					ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
					builder.type("text/html");
					builder.entity("<h3>Unauthorised</h3>");
					throw new WebApplicationException(builder.build());
				}
			}
			catch (Exception e) {
				ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
				builder.type("text/html");
				builder.entity("<h3>Unauthorised</h3>");
				throw new WebApplicationException(builder.build());
			}
		for(int i = 0; i < notices.size(); i++) {
			if(notices.get(i).get_nid().equals(_nid)) {
				if(notices.get(i).getStatus().equals("cancelled") || notices.get(i).getStatus().equals("rejected")) {
					notices.get(i).setStatus("archived");
					handler2.writeNotice(notices);
					msg = "<URI>/notices/" + _nid + "</URI>";
					break;
				}
			}
		}
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	
	
	@POST
	@Path("notices/{_nid}/generate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response create(@Context HttpHeaders headers,@PathParam("_nid") String _nid, @FormParam("amount") String amount) throws IOException {
		try {
			//System.out.println(_nid + " " + amount + "\n");
			//System.out.println(headers.getRequestHeader("key").get(0));
		if(!headers.getRequestHeader("key").get(0).equals("officer")) {
			ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
			builder.type("text/html");
			builder.entity("<h3>Unauthorised</h3>");
			throw new WebApplicationException(builder.build());
			}
		}
		catch (Exception e) {
			ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
			builder.type("text/html");
			builder.entity("<h3>Unauthorised</h3>");
			throw new WebApplicationException(builder.build());
		}
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        ArrayList<PaymentBean> payments = null;
        PaymentHandler handler3 = new PaymentHandler();
        payments = handler3.getPayment();
        String msg = "";
		try {
			//System.out.println(notices.size() + " " + notices.get(Integer.parseInt(_nid)).getStatus() + "\n");
			if(notices.size() > Integer.parseInt(_nid) && notices.get(Integer.parseInt(_nid)).getStatus().equals("accepted")) {
	        	notices.get(Integer.parseInt(_nid)).setStatus("awaiting");
	        	handler3.addPayment(payments, String.valueOf(payments.size()), _nid, amount, "N/A", "N/A");
	        	handler3.writePayment(payments);
	        	handler2.writeNotice(notices);
	        	msg = "<URI>/payments/" + String.valueOf(payments.size()) + "</URI>";
	        }
	        else {
	        	msg = "<Error>Not Available</Error>";
	        }
		} catch (Exception e){
			msg = "<Error>Wrong Input</Error>";
		}
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	@PUT
	@Path("payments/{_nid}/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response pay(@Context HttpHeaders headers,@PathParam("_nid") String _nid, @FormParam("card") String card, @FormParam("date") String date) throws IOException {
		try {
			//System.out.println(headers.getRequestHeader("key").get(0));
				if(!headers.getRequestHeader("key").get(0).equals("driver")) {
					ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
					builder.type("text/html");
					builder.entity("<h3>Unauthorised</h3>");
					throw new WebApplicationException(builder.build());
				}
			}
			catch (Exception e) {
				ResponseBuilder builder = Response.status(Status.UNAUTHORIZED); 
				builder.type("text/html");
				builder.entity("<h3>Unauthorised</h3>");
				throw new WebApplicationException(builder.build());
			}
		
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        ArrayList<PaymentBean> payments = null;
	    PaymentHandler handler3 = new PaymentHandler();
	    payments = handler3.getPayment();
	    String msg = "<Error>Not Available</Error>";
	    if(!_nid.matches("[0-9]+") || !card.matches("[0-9]+")) {
	    	msg = "<Error>Wrong Input Type</Error>";
	    }
	    else {
			
		    for(int i = 0; i < notices.size(); i++) {
				if(notices.get(i).get_nid().equals(_nid)) {
					if(notices.get(i).getStatus().equals("awaiting")) {
						for(int j = 0; j < payments.size(); j++) {
							if(payments.get(j).get_nid().equals(_nid)) {
								//System.out.println("11111111111");
								payments.get(j).setCard(card);
								payments.get(j).setDate(date);
								//handler3.addPayment(payments, _pid, _nid, payments.get(j).getAmount(), card, date);
					        	handler3.writePayment(payments);
					        	msg = "<URI>/payments/" + _nid + "</URI>";
					        	break;
							}
						}
					}
				}
			}
	    }
	    
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	@GET
	@Path("payments/{_nid}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getPayment(@PathParam("_nid") String _nid) {
		ArrayList<PaymentBean> payments = null;
        PaymentHandler handler3 = new PaymentHandler();
        payments = handler3.getPayment();
        String msg = "<Error>Not Found</Error>";
        for(int i = 0; i < payments.size(); i++) {
        	if(payments.get(i).get_nid().equals(_nid)) {
        		msg = "<Entry>\n";
	        	msg += "<_pid>" + payments.get(i).get_pid() + "</_pid>\n<_nid>" + payments.get(i).get_nid() +
	        				"</_nid>\n<Amount>" + payments.get(i).getAmount() + "</Amount>\n<Card>" + 
	        				payments.get(i).getCard() + "</Card>\n<Date>" + payments.get(i).getDate() + "</Date>\n";
	        	msg += "<URI>/notices/" + payments.get(i).get_nid() + "</URI>/n";
	        	msg +="</Entry>\n";
        	}
        }
        
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	
	@GET
	@Path("car/{_rid}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getCar(@PathParam("_rid") String _rid) {
		ArrayList<RecordBean> records = null;
        RecordHandler handler = new RecordHandler();
        records = handler.getRecords();
        String msg = "<Error>Not Found</Error>";
        for(int i = 0; i < records.size(); i++) {
        	if(records.get(i).get_rid().equals(_rid)) {
        		msg = "<Entry>\n";
	        	msg += "<_rid>" + _rid + "</_rid>\n<RegistrationNumber>" + records.get(i).getRego() +
	        				"</RegistrationNumber>\n<LastName>" + records.get(i).getLast() + "</LastName>\n<FirstName>" + 
	        				records.get(i).getFirst() + "</FirstName>\n<DriversLicenseNo>" + records.get(i).getLicense() + 
	        				"</DriversLicenseNo>\n<Email>" + records.get(i).getEmail() + "</Email>\n<RegistrationValidTill>" +
	        				records.get(i).getValid() + "</RegistrationValidTill>\n";
	        	msg += "</Entry>";
        	}
        }
        
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	
	@PUT
	@Path("car/{_nid}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public Response date(@PathParam("_nid") String _nid) throws IOException {
		ArrayList<NoticeBean> notices = null;
        NoticeHandler handler2 = new NoticeHandler();
        notices = handler2.getNotice();
        ArrayList<RecordBean> records = null;
        RecordHandler handler = new RecordHandler();
        records = handler.getRecords();
	    String msg = "<Error>Not Found</Error>";
	    String _rid = "";
	    for(int i = 0; i < notices.size(); i++) {
        	if(notices.get(i).get_nid().equals(_nid)) {
        		for(int j = 0; j < records.size(); j++) {
        			if(records.get(j).get_rid().equals(notices.get(i).get_rid())) {
        				_rid = records.get(j).get_rid();
        				String valid = records.get(i).getValid();
        				String valid_year = valid.substring(6);
        				valid_year = String.valueOf(Integer.parseInt(valid_year) + 1);
        				valid = valid.substring(0, 6) + valid_year;
        				records.get(j).setValid(valid);
        				handler.writeRecord(records);
        				msg = "<URI>/car/" + _rid + "</URI>";
        			}
        		}
        	}
	    }
		Response response = Response.status(200).type(MediaType.APPLICATION_XML).entity(msg).build();
		return response;
	}
	
	
}
