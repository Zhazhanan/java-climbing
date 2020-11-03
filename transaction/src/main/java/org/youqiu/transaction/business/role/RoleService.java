package org.youqiu.transaction.business.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: WangKun
 * @date: 2020/11/2
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public void insert(DashboardRole role) {
        roleMapper.insert(role);
    }
}
