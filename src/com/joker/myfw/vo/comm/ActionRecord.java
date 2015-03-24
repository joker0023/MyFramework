package com.joker.myfw.vo.comm;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;

import com.joker23.orm.persistence.POJO;

@Table(name="actionrecord")
public class ActionRecord extends POJO{

	private static final long serialVersionUID = 6661572822359189169L;

	public static final int CONSOLE = 1;
	public static final int WEB = 2;
	
	@Id
	private Long id;
	
	private Integer position;
	
	private String account;
	
	private Date time;
	
	private String type;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
