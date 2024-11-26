package tansun.creditx.bts.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "ControllerAPI", description = "控制器接口", externalDocs = @ExternalDocumentation(description = "这是一个接口文档介绍", url = "https://www.cnblogs.com/antLaddie/"))
@RestController
public class CustomerController {

    @NacosInjected
    private NamingService namingService;

    @GetMapping(value = "/test")
    public String test() {
        return "hello world";
    }

    @Operation(summary = "根据Id查询学生信息", description = "根据ID查询学生信息，并返回响应结果信息", parameters = {@Parameter(name = "id", description = "学生ID", required = true, example = "1")}, responses = {@ApiResponse(responseCode = "200", description = "响应成功", content = @Content(mediaType = "application/json", schema = @Schema(title = "AjaxResul和StudentVO组合模型", description = "返回实体，AjaxResult内data为StudentVO模型"))), @ApiResponse(responseCode = "500", description = "响应失败", content = @Content(mediaType = "application/json", schema = @Schema(title = "AjaxResul模型", description = "返回实体，AjaxResult内data为空")))})
    @GetMapping(value = "/get")
    public List<Instance> get(@RequestParam(defaultValue = "test") String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    @PostMapping(value = "/post")
    public List<Instance> post(@RequestParam(defaultValue = "test") String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }
}