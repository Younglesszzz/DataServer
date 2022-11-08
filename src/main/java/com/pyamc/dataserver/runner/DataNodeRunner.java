package com.pyamc.dataserver.runner;

import com.alibaba.fastjson.JSON;
import com.pyamc.dataserver.entity.DataNode;
import com.pyamc.dataserver.service.EtcdService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Component
public class DataNodeRunner implements ApplicationRunner {
    @Resource
    EtcdService etcdService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // --addr=ip:xxxx --name=DATANODE_01
        Set<String> options = args.getOptionNames();
        DataNode node = new DataNode();
        if (options.contains("addr")) {
            List<String> optionValues = args.getOptionValues("addr");
            node.setUrl(optionValues.get(0));
        }
        if (options.contains("name")) {
            List<String> optionalValues = args.getOptionValues("name");
            node.setKey(optionalValues.get(0));
        }
        // 集群lease
        etcdService.putWithLease(getNodeInfoKey(node.getKey()), JSON.toJSONString(node));
    }

    private static String getNodeInfoKey(String nodeName) {
        return "DATANODE_KEY_" + nodeName;
    }
}
