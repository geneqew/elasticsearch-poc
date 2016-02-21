package com.geneqew.poc.elasticsearch;

import java.net.InetAddress;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class ClientFactoryBean extends AbstractFactoryBean<Client> implements
        InitializingBean {

	@Value("${elastic.host.url}")
	private String hostUrl;
	@Value("${elastic.host.port}")
	private int hostPort;
	@Value("${elastic.clusterName}")
	private String clusterName;
	@Value("${client.pingTimeout}")
	private String pingTimeout;

	private Client client;

	@Override
	public Class<?> getObjectType() {
		return Client.class;
	}

	@Override
	protected Client createInstance() throws Exception {
		client = TransportClient
		        .builder()
		        .settings(settings())
		        .build()
		        .addTransportAddress(
		                new InetSocketTransportAddress(InetAddress
		                        .getByName(hostUrl), hostPort));
		return client;
	}

	protected Settings settings() {
		return Settings.settingsBuilder().put("cluster.name", clusterName)
		        .put("client.transport.ping_timeout", pingTimeout).build();
	}

	@Override
	public void destroy() throws Exception {
		if (client != null) {
			client.close();
		}
	}

}
