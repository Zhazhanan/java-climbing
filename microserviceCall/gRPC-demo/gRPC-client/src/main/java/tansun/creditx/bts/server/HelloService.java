package tansun.creditx.bts.server;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import tansun.creditx.bts.service.grpc.impl.HelloProto;
import tansun.creditx.bts.service.grpc.impl.HelloServiceGrpc;

@Service
public class HelloService {

    @GrpcClient("grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub helloServiceBlockingStub;

    public String sayHello(String msg) {
        HelloProto.HelloRequest request = HelloProto.HelloRequest
                .newBuilder()
                .setMsg(msg)
                .build();
        HelloProto.HelloResponse response = helloServiceBlockingStub.hello(request);
        return response.getResult();
    }
}
