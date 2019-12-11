package org.pyf.developer.bean.one.model.auth;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** 
*Description: <系统用户实体类>. <br>
*<p>
	<使用说明>
 </p>
*Makedate:2014年3月20日 上午10:36:07 
* @author hanqunfeng  
* @version V1.0                             
*/  
@Entity
@Table(name = "TBL_CP_USER", schema = "cp2015db")
@Lazy(value=false)
public class SystemUser implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3899377388822016718L;

	
	@Id
	@Column(name="USER_ID")
	private String userId;
	
	public String getUserId() {
		return userId;
	}

	@Column(name="USER_PASSWORD")
	private String password;
	
	@Column(name="USER_EMAIL")
	private String email;
	
	@Column(name="USER_PHNUM")
	private String phoneNumber;
	
	@Column(name="USER_MOBILE")
	private String mobileNumber;
	
	
	@Column(name="USER_ADDRESS")
	private String address;
	
	@Column(name="USER_STATUS")
	private Boolean status = true;
	
	@Column(name="USER_NAME")
	private String name;
	
	




	@ManyToMany(cascade={CascadeType.DETACH},fetch=FetchType.LAZY)
	@JoinTable(name="TBL_CP_USERROLE",joinColumns={@JoinColumn(name="USRO_USERID_FK")},inverseJoinColumns={@JoinColumn(name="USRO_ROLEID_FK")})
	@OrderBy("id ASC")
	private Set<SystemRole> roles;

	public SystemUser() {
	}

	public SystemUser(String userId, String name, boolean status,
			long cooperatorId) {
		this.userId = userId;
		this.name = name;
		this.status = status;

	}
	public SystemUser(String userId, String password, String name,
			String email, String phoneNumber, String mobileNumber,
			String address, boolean status, long cooperatorId, Long departmentId) {
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.mobileNumber = mobileNumber;
		this.address = address;
		this.status = status;


	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SystemUser == false) {
            return false;
        }
		if(this == obj) {
            return true;
        }
		SystemUser other = (SystemUser)obj;
		return new EqualsBuilder()
				.append(getUserId(),other.getUserId())
			.isEquals();
	}

	public String getAddress() {
		return this.address;
	}

	public Set<String> getAllsUrl() {
		Set<String> results = null;
		if (roles != null && roles.size() > 0) {
			for (SystemRole role : roles) {
				if (role != null) {
					Set<SystemAuthority> auths = role.getAuthorities();
					if (auths != null && auths.size() > 0) {
						if (results == null) {
                            results = new HashSet<String>();
                        }
						for (SystemAuthority auth : role.getAuthorities()) {
							results.add(auth.getEntrance().trim());
						}
					}
				}
			}
		}
		return results;
	}

	public Set<SystemAuthority> getAuthorities() {
		Set<SystemAuthority> results = null;
		if (roles != null && roles.size() > 0) {
			for (SystemRole role : roles) {
				if (role != null) {
					Set<SystemAuthority> auths = role.getAuthorities();
					if (auths != null && auths.size() > 0) {
						if (results == null) {
                            results = new HashSet<SystemAuthority>();
                        }
						results.addAll(role.getAuthorities());
					}
				}
			}
		}
		return results;
	}



	




	public String getEmail() {
		return this.email;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public String getName() {
		return this.name;
	}

	public String getNameAndId(){
		return this.name+"---"+this.userId;
	}

	public String getPassword() {
		return this.password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public Set<SystemRole> getRoles() {
		return roles;
	}

	/**
	 * @return the status
	 */
	public Boolean getStatus() {
		return status;
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getUserId())
	.toHashCode();
	}

	public boolean isStatus() {
		return this.status;
	}

	public void setAddress(String address) {
		this.address = address;
	}






	public void setEmail(String email) {
		this.email = email;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRoles(Set<SystemRole> roles) {
		this.roles = roles;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
