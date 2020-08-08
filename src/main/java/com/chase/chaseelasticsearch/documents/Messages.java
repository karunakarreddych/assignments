package com.chase.chaseelasticsearch.documents;

import javax.persistence.Transient;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "#{index.getMessageIndex()}", type = "_doc")
public class Messages {

	@Id
	private String messageid;
	private String body;
	private String tojid;
	private String fromjid;
	private String sentdate;
	private String targetJid;
	private String stanza;
	
	@Transient
	private String name;
	
	
	public String getMessageid() {
		return messageid;
	}
	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getTojid() {
		return tojid;
	}
	public void setTojid(String tojid) {
		this.tojid = tojid;
	}
	public String getFromjid() {
		return fromjid;
	}
	public void setFromjid(String fromjid) {
		this.fromjid = fromjid;
	}
	public String getSentdate() {
		return sentdate;
	}
	public void setSentdate(String sentdate) {
		this.sentdate = sentdate;
	}
	public String getTargetJid() {
		return targetJid;
	}
	public void setTargetJid(String targetJid) {
		this.targetJid = targetJid;
	}
	public String getStanza() {
		return stanza;
	}
	public void setStanza(String stanza) {
		this.stanza = stanza;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}
