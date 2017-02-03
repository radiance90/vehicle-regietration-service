package au.edu.unsw.soacourse.rmsclient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NoticeBean {
	
	private String _nid;
	private String _rid;
	private String status;
	
	
	public NoticeBean(){}
	
	public NoticeBean(String _nid, String _rid, String status) {
		super();
		this._rid = _rid;
		this._nid = _nid;
		this.status = status;
		
	}
	
	public String get_rid() {
		return _rid;
	}
	public void set_rid(String _rid) {
		this._rid = _rid;
	}
	
	public String get_nid() {
		return _nid;
	}
	public void set_nid(String _nid) {
		this._nid = _nid;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
