package au.edu.unsw.soacourse.rmsclient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RecordBean {
	
	private String _rid;
	private String rego;
	private String last;
	private String first;
	private String license;
	private String email;
	private String valid;
	
	public RecordBean(){}
	
	public RecordBean(String _rid, String rego, String last,
			String first, String license, String email, 
			String valid) {
		super();
		this._rid = _rid;
		this.rego = rego;
		this.last = last;
		this.first = first;
		this.license = license;
		this.email = email;
		this.valid = valid;
	}
	
	public String get_rid() {
		return _rid;
	}
	public void set_rid(String _rid) {
		this._rid = _rid;
	}
	
	public String getRego() {
		return rego;
	}
	public void setRego(String rego) {
		this.rego = rego;
	}
	
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
}
