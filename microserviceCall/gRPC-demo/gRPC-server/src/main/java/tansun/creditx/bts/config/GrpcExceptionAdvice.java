package tansun.creditx.bts.config;

import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

@GrpcAdvice
public class GrpcExceptionAdvice {

    /**
     * 参数异常相关
     */
    @GrpcExceptionHandler({IllegalArgumentException.class})
    public Status handleInvalidArgument(IllegalArgumentException e) {
        return Status.INVALID_ARGUMENT.withDescription("Your description").withCause(e);
    }

/*    @GrpcExceptionHandler(MethodArgumentNotValidException.class)
    public StatusException handleResourceNotFoundException(MethodArgumentNotValidException e) {
        Status status = Status.NOT_FOUND.withDescription("Your description").withCause(e);
        Metadata metadata = ...
        return status.asException(metadata);
    }*/

}
