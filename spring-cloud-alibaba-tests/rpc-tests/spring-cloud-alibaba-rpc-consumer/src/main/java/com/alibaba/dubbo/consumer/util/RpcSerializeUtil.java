package com.alibaba.dubbo.consumer.util;

import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.hessian2.Hessian2Serialization;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author :Lictory
 * @date : 2024/08/13
 */
public class RpcSerializeUtil {
    public byte[] serializeRequestByHessian2(Object request) throws IOException {
        Hessian2Serialization serialization = new Hessian2Serialization();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutput objectOutput = serialization.serialize(null, byteArrayOutputStream);

        objectOutput.writeObject(request);
        objectOutput.flushBuffer();

        return byteArrayOutputStream.toByteArray();
    }
    //TODO : add the rest of the serialization methods
}
