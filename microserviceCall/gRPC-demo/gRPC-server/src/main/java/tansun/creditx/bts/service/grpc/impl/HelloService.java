package tansun.creditx.bts.service.grpc.impl;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(HelloProto.HelloRequest request, StreamObserver<HelloProto.HelloResponse> responseObserver) {
        String msg = request.getMsg();
        System.out.println("收到客户端请求: " + msg);
        HelloProto.HelloResponse response = HelloProto.HelloResponse
                .newBuilder()
                .setResult("ok!")
                .build();
        // 测试异常拦截包装 GrpcExceptionAdvice
        /*if (true) {
            throw new IllegalArgumentException();
        }*/
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
