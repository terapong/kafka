package toto.kafka.springboot.jsf;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

@Named(value = "menuBean")
@SessionScoped
public class MenuBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IndexBean indexBean;
	
	public String  adminConsole() {
		indexBean.setContentCenter("extensions/blank.xhtml");
		return "index.xhtml";
	}
	
	public String summaryAdmin() {
		indexBean.setContentCenter("pages/admin/summaryAdmin.xhtml");
		return "index.xhtml";
	}	
	
	public String topicAdmin() {
		indexBean.setContentCenter("pages/admin/topicAdmin.xhtml");
		return "index.xhtml";
	}
	
	public String producerPage() {
		indexBean.setContentCenter("pages/producerPage.xhtml");
		return "index.xhtml";
	}
	
	public String consumerPage() {
		indexBean.setContentCenter("pages/consumerPage.xhtml");
		return "index.xhtml";
	}
}
