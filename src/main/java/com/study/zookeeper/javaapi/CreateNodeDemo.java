package com.study.zookeeper.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CreateNodeDemo implements Watcher {
    private static final String CONNECTSTR = "192.168.25.128:2181,192.168.25.128:2182,192.168.25.128:2183";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static ZooKeeper zooKeeper = null;
    private static Stat stat = new Stat();
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        zooKeeper = new ZooKeeper(CONNECTSTR, 5000, new CreateNodeDemo());
        countDownLatch.await();
        String result = zooKeeper.create("/node1", "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("创建成功："+result);

       // zooKeeper.setData("/node1","1231231".getBytes(),-1);
        Thread.sleep(2000);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if(watchedEvent.getType()==Event.EventType.None && null == watchedEvent.getPath()){
                countDownLatch.countDown();
                //会话状态：NOt Connected/connecting/connected/closed
                System.out.println("----->"+watchedEvent.getType()+"--------->"+watchedEvent.getState());
                System.out.println("======="+watchedEvent.getPath());
            }else if (watchedEvent.getType()==Event.EventType.NodeDataChanged){
                try {
                    System.out.println("路径:"+watchedEvent.getPath()+"---NodeDataChanged改变后的值："+
                                zooKeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if (watchedEvent.getType()== Event.EventType.NodeChildrenChanged){//子节点数据变更会触发

            }else if (watchedEvent.getType() == Event.EventType.NodeCreated){//创建子节点触发
                try {
                    System.out.println("节点创建路径:"+watchedEvent.getPath()+"---NodeCreated改变后的值："+
                            zooKeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(watchedEvent.getType() == Event.EventType.NodeDeleted){//删除子节点触发
                try {
                    System.out.println("子节点创建路径:"+watchedEvent.getPath()+"---NodeCreated改变后的值："+
                            zooKeeper.getData(watchedEvent.getPath(),true,stat));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(watchedEvent.getType());
    }
}
