package org.youqiu.transaction;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.youqiu.transaction.business.TranscationService;

import java.sql.SQLException;


/**
 * 事务传播行为测试
 *
 * @author: WangKun
 * @date: 2020/10/29
 */
//@Profile("dev")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionApplicationTests {
    @Autowired
    private TranscationService transcationService;


    @Test
    void contextLoads() {
    }

    /**
     * 没有事务
     */
    @Test
    public void demo() throws SQLException {
        transcationService.demo();
    }

    /**
     * 没有事务,调用有事务
     */
    @Test
    public void demo1() throws SQLException {
        transcationService.demo1();
//        DataIntegrityViolationException
    }

    /**
     * 有事务
     */
    @Test
    public void demo22() throws SQLException {
        transcationService.demo22();
    }

    /**
     * 有事务，失效
     */
    @Test
    public void demo222() throws SQLException {
        transcationService.demo222();
    }

    /**
     * 有事务，失效，try catch处理
     */
    @Test
    public void demo3() throws Exception {
        transcationService.demo3();
    }

}
