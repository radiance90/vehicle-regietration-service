package au.edu.unsw.soacourse.rmsclient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PaymentBean {
	
	private String _pid;
	private String _nid;
	private String amount;
	private String card;
	private String date;
	
	
	public PaymentBean(){}
	
	public PaymentBean(String _pid, String _nid, String amount,
			String card, String date) {
		super();
		this._pid = _pid;
		this._nid = _nid;
		this.amount = amount;
		this.card = card;
		this.date = date;
		
	}
	
	public String get_pid() {
		return _pid;
	}
	public void set_pid(String _pid) {
		this._pid = _pid;
	}
	
	public String get_nid() {
		return _nid;
	}
	public void set_nid(String _nid) {
		this._nid = _nid;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
