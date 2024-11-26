package tansun.creditx.bts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tansun.creditx.bts.server.HelloService;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hello")
    public String hello(@RequestParam(defaultValue = "Hello World") String msg) {
        return helloService.sayHello(msg);
    }

    @RequestMapping("/test")
    public void test(@RequestParam(defaultValue = "Hello World") String msg) {
        if (true) {
            throw new RuntimeException("error 异常测试, " + msg);
        }
    }

}
