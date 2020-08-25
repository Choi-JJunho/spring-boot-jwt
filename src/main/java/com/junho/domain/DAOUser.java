package com.junho.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

// User에 대한 DAO
// Entity : RDBMS에서 TABLE을 객체화시킨 것으로 보면 된다.
// Column : 지정한 변수명과 DB 컬럼명을 다르게 주고싶을 경우 @Column(name="") 형태로 작성할 수 있다.
// 기본적으로는 변수명과 DB컬럼명을 매핑한다.
@Entity
@Table(name = "test")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}