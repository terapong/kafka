package toto.kafka.springboot.jsf;

import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;

@Named(value = "indexBean")
@SessionScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String contentCenter = "extensions/blank.xhtml";
	
	public String handleClose(CloseEvent event) {
		this.contentCenter = "extensions/blank.xhtml";
		return "main?facesRedirect=true";
	}

	public String getContentCenter() {
		return contentCenter;
	}

	public void setContentCenter(String contentCenter) {
		this.contentCenter = contentCenter;
	}
}
