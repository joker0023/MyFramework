package com.joker.myfw.vo.console;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.joker23.orm.persistence.POJO;

@Table(name="console_user")
public class ConsoleUser extends POJO{

	private static final long serialVersionUID = -3449106707672034358L;

	public static final int NORMAL = 1;
	public static final int DISABLED = 2;
	
	@Id
	private Long id;
	
	/**
	 * 登录账号
	 */
	private String account;
	
	/**
	 * 登录密码
	 */
	private String password;
	
	/**
	 * 昵称
	 */
	private String nick;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 角色
	 */
	private Long role;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;
	
	/**
	 * 最后登录ip
	 */
	private String lastLoginIp;
	
	/**
	 * 状态
	 * 1--正常 0--禁用
	 */
	private Integer state;
	
	@Transient
	private String roleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
