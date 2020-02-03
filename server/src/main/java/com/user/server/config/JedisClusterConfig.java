package com.user.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@ConditionalOnClass({JedisCluster.class})
public class JedisClusterConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String clusterIpPort;


    @Bean
    public JedisCluster getJedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        String[] clusterIpPortList = clusterIpPort.split(",");

        for (String ipPort : clusterIpPortList) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        return new JedisCluster(nodes);
    }
}
