package com.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CuratorUtils {

    private static final String CONNECTSTR = "192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183";

    public static CuratorFramework getInstance(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory
                .newClient(CONNECTSTR,
                        5000,
                        5000,
                        new ExponentialBackoffRetry(1000,3));
        curatorFramework.start();
        return curatorFramework;
    }
}
