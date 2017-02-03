package au.edu.unsw.soacourse.rmsclient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LinksBean {
	
	private String link;
	private String action;
	
	
	public LinksBean(){}
	
	public LinksBean(String link, String action) {
		super();
		this.link = link;
		this.action = action;
	}
	
	
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
	
}
