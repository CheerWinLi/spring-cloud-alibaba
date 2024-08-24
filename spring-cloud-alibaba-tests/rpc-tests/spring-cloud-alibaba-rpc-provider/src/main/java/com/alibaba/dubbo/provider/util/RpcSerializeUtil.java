package com.alibaba.dubbo.provider.util;

import org.apache.dubbo.common.serialize.ObjectOutput;
import org.apache.dubbo.common.serialize.hessian2.Hessian2Serialization;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author :Lictory
 * @date : 2024/08/13
 */


public class RpcSerializeUtil {
    public static byte[] serializeRequestByHessian2(Object result) {
        Hessian2Serialization serialization = new Hessian2Serialization();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutput objectOutput = serialization.serialize(null, byteArrayOutputStream);

            objectOutput.writeObject(result);
            objectOutput.flushBuffer();
        } catch (Exception e) {

        }
        return byteArrayOutputStream.toByteArray();
    }
    //TODO : add the rest of the serialization methods
}
