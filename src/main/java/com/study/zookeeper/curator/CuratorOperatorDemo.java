package com.study.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

            public class CuratorOperatorDemo {
                public static void main(String[] args) throws Exception {
                    CuratorFramework curatorFramework = CuratorUtils.getInstance();
                    //创建节点
        /*
        String path = curatorFramework.create()
                                      .creatingParentsIfNeeded()
                                      .withMode(CreateMode.PERSISTENT)
                                      .forPath("/curator/node/node1-1");
        System.out.println(path);*/

        /*//删除节点
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/node111");*/

                    //查询
        /*
        Stat stat = new Stat();
        byte[] bytes = curatorFramework.getData().storingStatIn(stat).forPath("/curator/node/node1-1");
        System.out.println(new String(bytes));*/

                    //更新
        /*
        curatorFramework.setData().forPath("/curator/node/node1-1","123".getBytes());*/

                    //异步操作
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    ExecutorService executor = Executors.newFixedThreadPool(1);
                    curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).inBackground(new BackgroundCallback() {
                        @Override
            public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                countDownLatch.countDown();
                System.out.println(Thread.currentThread().getName()+"->resultCode:"+curatorEvent.getResultCode()+"->操作类型："+curatorEvent.getType());
            }
        },executor).forPath("/nodeTmp","123".getBytes());
        countDownLatch.await();
        executor.shutdown();
    }
}
