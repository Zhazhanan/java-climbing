package tansun.creditx.bts.controller;


import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tansun.creditx.bts.service.NacosSwaggerConfigService;

@RestController
@RequestMapping("/swagger/flush")
public class NacosSwaggerConfigController {

    @Autowired
    private NacosSwaggerConfigService nacosSwaggerConfigService;

    @GetMapping
    public void swaggerConfig() throws NacosException {
        nacosSwaggerConfigService.swaggerConfigFlush();
    }
}
