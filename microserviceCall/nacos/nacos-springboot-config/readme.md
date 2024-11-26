## nacos 示例

1. 启动 NacosConfigApplication，调用接口，返回内容是 false

```shell
curl http://localhost:8080/config/get
```

2. 通过调用 Nacos Open API 向 Nacos server 发布配置：dataId 为example，内容为useLocalCache=true

```shell
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=example&group=DEFAULT_GROUP&content=useLocalCache=true"
```

3. 再次访问接口，此时返回内容为true，说明程序中的useLocalCache值已经被动态更新了

```shell
http://localhost:8080/config/get
```