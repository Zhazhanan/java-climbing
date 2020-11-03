package org.youqiu.transaction.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.youqiu.transaction.business.role.DashboardRole;
import org.youqiu.transaction.business.role.RoleService;
import org.youqiu.transaction.business.user.DashboardUser;
import org.youqiu.transaction.business.user.UserService;

import java.sql.SQLException;
import java.util.UUID;

/**
 * 事务传播行为测试
 *
 * @author: WangKun
 * @date: 2020/10/29
 */
@Service
public class TranscationService {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;


    /**
     * 测试一 没有事务
     */
    public void demo() throws SQLException {
        DashboardRole dashboardRole = new DashboardRole();
        dashboardRole.setRoleId(UUID.randomUUID().toString());
        dashboardRole.setRoleName("name");
        dashboardRole.setUserId("iii");
        roleService.insert(dashboardRole);

        DashboardUser dashboardUser = new DashboardUser();
//        dashboardUser.setUserId(UUID.randomUUID().toString());
        dashboardUser.setLoginName("123");
        dashboardUser.setUserName("321");
        dashboardUser.setUserPassword("234");
        dashboardUser.setUserStatus("sss");
        userService.insert(dashboardUser);

    }

    /**
     * 测试二 没有事务调用有事务
     */
    public void demo1() throws SQLException {
        DashboardRole dashboardRole = new DashboardRole();
        dashboardRole.setRoleId(UUID.randomUUID().toString());
        dashboardRole.setRoleName("name");
        dashboardRole.setUserId("iii");
        roleService.insert(dashboardRole);
        demo2();
    }

    /**
     * 有事务
     */
    @Transactional
    public void demo2() throws SQLException {
        DashboardUser dashboardUser = new DashboardUser();
//        dashboardUser.setUserId(UUID.randomUUID().toString());
        dashboardUser.setLoginName("123");
        dashboardUser.setUserName("321");
        dashboardUser.setUserPassword("234");
        dashboardUser.setUserStatus("sss");
        userService.insert(dashboardUser);
//        throw new SQLException("异常");
    }

    /**
     * 有事务，生效 DataIntegrityViolationException 是RuntimeException
     */
    @Transactional
    public void demo22() throws SQLException {
        DashboardRole dashboardRole = new DashboardRole();
        dashboardRole.setRoleId(UUID.randomUUID().toString());
        dashboardRole.setRoleName("name");
        dashboardRole.setUserId("iii");
        roleService.insert(dashboardRole);

        DashboardUser dashboardUser = new DashboardUser();
//        dashboardUser.setUserId(UUID.randomUUID().toString());
        dashboardUser.setLoginName("123");
        dashboardUser.setUserName("321");
        dashboardUser.setUserPassword("234");
        dashboardUser.setUserStatus("sss");
        userService.insert(dashboardUser);
    }

    /**
     * 有事务，失效，方法不能为 private、默认，必须为public
     */
    @Transactional
    void demo4() {
        DashboardRole dashboardRole = new DashboardRole();
        dashboardRole.setRoleId(UUID.randomUUID().toString());
        dashboardRole.setRoleName("name");
        dashboardRole.setUserId("iii");
        roleService.insert(dashboardRole);

        DashboardUser dashboardUser = new DashboardUser();
//        dashboardUser.setUserId(UUID.randomUUID().toString());
        dashboardUser.setLoginName("123");
        dashboardUser.setUserName("321");
        dashboardUser.setUserPassword("234");
        dashboardUser.setUserStatus("sss");
        userService.insert(dashboardUser);
    }

    /**
     * 有事务，失效，SQLException 不是RuntimeException
     * Transactional 默认回滚RuntimeException
     */
    @Transactional
    public void demo222() throws SQLException {
        DashboardRole dashboardRole = new DashboardRole();
        dashboardRole.setRoleId(UUID.randomUUID().toString());
        dashboardRole.setRoleName("name");
        dashboardRole.setUserId("iii");
        roleService.insert(dashboardRole);
        throw new SQLException("异常");
    }

    /**
     * 有事务，失效，try catch处理
     */
    @Transactional
    public void demo3() throws Exception {
        DashboardRole dashboardRole = new DashboardRole();
        dashboardRole.setRoleId(UUID.randomUUID().toString());
        dashboardRole.setRoleName("name");
        dashboardRole.setUserId("iii");
        roleService.insert(dashboardRole);
        try {
            DashboardUser dashboardUser = new DashboardUser();
//        dashboardUser.setUserId(UUID.randomUUID().toString());
            dashboardUser.setLoginName("123");
            dashboardUser.setUserName("321");
            dashboardUser.setUserPassword("234");
            dashboardUser.setUserStatus("sss");
            userService.insert(dashboardUser);
        } catch (Exception throwables) {
            System.out.println("异常啦！");
        }
    }


    /** 多线程，在标记事务的方法内部，另起子线程执行 db 操作，此时事务同样不会生效*/


}
