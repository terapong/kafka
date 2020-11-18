package toto.kafka.springboot.jsf;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

@Named(value = "summaryAdminBean")
@SessionScoped
public class SummaryAdminBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@PostConstruct
	public void init() {

	}
	
	@PreDestroy
	public void destroy() {
		
	}

}
