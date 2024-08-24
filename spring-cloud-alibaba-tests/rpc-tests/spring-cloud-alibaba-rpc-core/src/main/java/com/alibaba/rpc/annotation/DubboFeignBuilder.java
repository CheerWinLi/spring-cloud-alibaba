package com.alibaba.rpc.annotation;


import feign.Feign;
import feign.Target;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.reference.ReferenceCreator;
import org.apache.dubbo.config.spring.util.AnnotationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;

/**
 * @author Lictory
 */

public class DubboFeignBuilder extends Feign.Builder {
    @Autowired
    private ApplicationContext applicationContext;

    public DubboReference defaultReference;

    final class DefaultReferenceClass {
        @DubboReference(check = false)
        String field;
    }

    public DubboFeignBuilder() {
        this.defaultReference = ReflectionUtils.findField(DefaultReferenceClass.class, "field").getAnnotation(DubboReference.class);
    }

    //ReferenceBeanBuilder
//        .create(defaultReference, target.getClass().getClassLoader(), applicationContext)
//            .setInterfaceClass(target.type()).build();
    @Override
    public <T> T target(Target<T> target) {
        AnnotationAttributes attributes = AnnotationUtils.getAnnotationAttributes(defaultReference, true);
        ReferenceCreator build = ReferenceCreator.create(attributes, applicationContext).defaultInterfaceClass(target.type());
        try {
            T object = (T) build.build().get();
            return object;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
