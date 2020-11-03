package org.youqiu.transaction.business.role;

import org.apache.ibatis.annotations.Insert;

/**
 * @author: WangKun
 * @date: 2020/11/2
 */
public interface RoleMapper {
    @Insert("INSERT INTO dashboard_role (role_id, role_name, user_id)  "
            + "values (#{roleId}, #{roleName}, #{userId} )")
    void insert(DashboardRole role);
}
