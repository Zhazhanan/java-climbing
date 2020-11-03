package org.youqiu.transaction.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * 测试
 *
 * @author: WangKun
 * @date: 2020/10/29
 */
@RestController
public class TranscationRest {
    @Autowired
    private TranscationService transcationService;

    @GetMapping("/demo")
    public void demo() throws SQLException {
        transcationService.demo();
    }

    @GetMapping("/demo1")
    public void demo1() throws SQLException {
        transcationService.demo1();
    }

    @GetMapping("/demo2")
    public void demo2() throws SQLException {
        transcationService.demo2();
    }

    @GetMapping("/demo4")
    public void demo4() throws SQLException {
        transcationService.demo4();
    }

}
