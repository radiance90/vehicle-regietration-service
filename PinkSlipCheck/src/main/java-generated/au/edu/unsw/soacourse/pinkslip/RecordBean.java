package au.edu.unsw.soacourse.pinkslip;

public class RecordBean {
	
	private String _cid;
	private String rego;
	private String last;
	private String first;
	private String license;
	private String email;
	private String year;
	private String valid;
	
	public RecordBean(){}
	
	public RecordBean(String _cid, String rego, String last,
			String first, String license, String email, String year,
			String valid) {
		super();
		this._cid = _cid;
		this.rego = rego;
		this.last = last;
		this.first = first;
		this.license = license;
		this.email = email;
		this.year = year;
		this.valid = valid;
	}
	
	public String get_cid() {
		return _cid;
	}
	public void set_cid(String _cid) {
		this._cid = _cid;
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
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
}
