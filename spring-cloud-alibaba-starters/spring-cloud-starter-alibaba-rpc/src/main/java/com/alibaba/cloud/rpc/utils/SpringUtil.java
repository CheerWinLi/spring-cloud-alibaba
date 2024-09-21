package com.alibaba.cloud.rpc.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author :Lictory
 * @date : 2024/08/31
 */


@Component
public final class SpringUtil implements BeanFactoryPostProcessor, ApplicationContextAware {
    /**
     * Spring应用上下文环境
     */
    public static ConfigurableListableBeanFactory beanFactory;

    public static ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }
}
