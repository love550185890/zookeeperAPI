package com.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorCreateSessionDemo {
    private static final String CONNECTSTR = "192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183";
    public static void main(String[] args) {
        //创建会话的两种方式
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(
                                                            CONNECTSTR,
                                                            5000,
                                                            5000,
                                                            new ExponentialBackoffRetry(
                                                                    1000,
                                                                    3
                                                                    )
                                                    );
        curatorFramework.start();
        //Fluent风格
        CuratorFramework curatorFramework1 = CuratorFrameworkFactory.builder()
                .connectString(CONNECTSTR)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();

    }
}
