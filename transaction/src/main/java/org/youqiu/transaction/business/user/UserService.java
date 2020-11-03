package org.youqiu.transaction.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: WangKun
 * @date: 2020/11/2
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void insert(DashboardUser user) {
        userMapper.insert(user);
    }
}
