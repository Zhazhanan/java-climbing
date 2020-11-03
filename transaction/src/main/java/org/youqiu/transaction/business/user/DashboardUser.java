package org.youqiu.transaction.business.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
* @author wk
* @date 2020-11-02T18:05:06.087
*/
@Data
@ApiModel(value = "dashboard_user model", description = "dashboard_user model")
public class DashboardUser implements Serializable {

	@ApiModelProperty(value = "")
	private String userId;//
	@ApiModelProperty(value = "")
	private String loginName;//
	@ApiModelProperty(value = "")
	private String userName;//
	@ApiModelProperty(value = "")
	private String userPassword;//
	@ApiModelProperty(value = "")
	private String userStatus;//

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
}

