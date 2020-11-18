package toto.kafka.springboot.jsf;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.SessionScope;

import toto.kafka.springboot.controller.TopicController;
import toto.kafka.springboot.entity.Topic;

@Named(value = "topicAdminBean")
@SessionScope
public class TopicAdminBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Calendar cal;
	private List<Topic> topics;
	private Topic selectedRow;
	
	@Autowired
	private TopicController control;
	
	@PostConstruct
	public void init() {
		topics = control.getAllTopics();
		
//		try {
//			Properties properties = new Properties();
//			properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
//
//			AdminClient adminClient = AdminClient.create(properties);
//
//			ListTopicsOptions listTopicsOptions = new ListTopicsOptions();
//			listTopicsOptions.listInternal(true);
//			int i = 0;
//			Iterator<String> tns = adminClient.listTopics(listTopicsOptions).names().get().iterator();
//			
//			while(tns.hasNext()) {
//				Topic topic = new Topic();
//				topic.setId(i);
//				topic.setName(tns.next());
//				topics.add(topic);
//				i++;
//			}
//			System.out.println("topics: " + topics);
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
	}
	
	@PreDestroy
	public void destroy() {
		
	}
	
	public void btnNewClick() {
		cal = Calendar.getInstance();
		selectedRow = new Topic();
		selectedRow.setReplicas(1);
		selectedRow.setPartition(1);
		selectedRow.setCreateDate(cal.getTime());
		selectedRow.setUpdateDate(cal.getTime());
	}
	
	public void confirmDeleteClick() {
		try {
			control.deleteTopic(selectedRow.getId());
			init();
		} catch(Exception ex) {
			FacesMessage msg = new FacesMessage();
			msg.setSummary("ไม่สามารถ ลบ ได้");
			msg.setDetail("ไม่สามารถ ลบ ได้ เพราะมี ข้อมูลที่เกี่ยวข้อง");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public void btnSaveClick() {
		selectedRow = control.createTopic(selectedRow);
		init();
	}
	
	public void btnDeleteClick(Topic r) {
		selectedRow = r;
	}
	
	public void onRowSelect(SelectEvent<Topic> event) {
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Topic");
        msg.setDetail("Selected topic: " + selectedRow.getName());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	public Topic getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Topic selectedRow) {
		this.selectedRow = selectedRow;
	}
}
