package com.study.zookeeper.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CreateSessionDemo {
    private static final String CONNECTSTR = "192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTR, 5000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    if (watchedEvent.getState()==Event.KeeperState.SyncConnected){
                        countDownLatch.countDown();
                        System.out.println("-------------"+watchedEvent.getState());
                    }
                }
            });
            countDownLatch.await();
            System.out.println(zooKeeper.getState());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
