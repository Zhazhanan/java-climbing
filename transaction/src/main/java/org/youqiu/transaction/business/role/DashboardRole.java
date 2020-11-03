package org.youqiu.transaction.business.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wk
 * @date 2020-10-27T20:02:44.589
 */
@ApiModel(value = "dashboard_role model", description = "dashboard_role model")
public class DashboardRole implements Serializable {

    @ApiModelProperty(value = "")
    private String roleId;
    @ApiModelProperty(value = "")
    private String roleName;
    @ApiModelProperty(value = "")
    private String userId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

