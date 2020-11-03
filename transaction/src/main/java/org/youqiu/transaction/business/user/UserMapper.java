package org.youqiu.transaction.business.user;

import org.apache.ibatis.annotations.Insert;

/**
 * @author: WangKun
 * @date: 2020/11/2
 */
public interface UserMapper {
    @Insert("INSERT INTO dashboard_user (user_id, login_name, user_name, user_password, user_status)  "
            + "values (#{userId}, #{loginName}, #{userName}, #{userPassword}, #{userStatus}) ")
    void insert(DashboardUser user);
}
