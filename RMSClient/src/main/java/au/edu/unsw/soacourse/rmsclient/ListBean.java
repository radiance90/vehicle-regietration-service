package au.edu.unsw.soacourse.rmsclient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListBean {
	
	private String _nid;
	
	
	
	public ListBean(){}
	
	public ListBean(String _nid) {
		super();
		this._nid = _nid;
		
	}
	
	
	
	public String get_nid() {
		return _nid;
	}
	public void set_nid(String _nid) {
		this._nid = _nid;
	}
	
	
	
}
