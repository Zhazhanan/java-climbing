## nacos 示例

1. 启动 NacosDiscoveryApplication，调用接口，此时返回为空 JSON 数组[]

```shell
curl http://localhost:8080/discovery/get?serviceName=example
```

2. 通过调用 Nacos Open API 向 Nacos server 注册一个名称为 example 服务

```shell
curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=example&ip=127.0.0.1&port=8080'
```

3. 再次访问 

```shell
curl http://localhost:8080/discovery/get?serviceName=example
```