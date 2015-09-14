package com.wiley.gr.ace.search.service.impl;

import com.wiley.gr.ace.search.constant.CommonConstants;
import com.wiley.gr.ace.search.service.SearchClientService;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by KKALYAN on 9/13/2015.
 */
public class SearchClientServiceImpl implements SearchClientService {

    private static final Logger logger = LoggerFactory.getLogger(SearchClientServiceImpl.class);


    @Value("${cluster.name}")
    private String clusterName;

    @Value("${server.port}")
    private int port;

    @Value("${ip.address}")
    private String machine;

    private Client client;

    /**
     * Method to get client object.
     *
     * @return
     */
    public Client getClient() {
        if (client == null) {
            client = createClient();
        }

        return client;
    }

    /**
     * Method to create transport client.
     *
     * @return
     */
    protected Client createClient() {
        if (client == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("Creating client for Search!");
            }
            //Try starting search client at context loading
            try {

                Settings settings = ImmutableSettings.settingsBuilder()
                        .put(CommonConstants.CLUSTER_NAME, clusterName)
                        .put(CommonConstants.CLUSTER_TRANSPORT_SNIFF, true).build();

                TransportClient transportClient = new TransportClient(settings);

                transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress(machine, port));

                if (transportClient.connectedNodes().size() == 0) {
                    logger.error("There are no active nodes available for the transport, it will be automatically added once nodes are live!");
                }
                client = transportClient;
            } catch (Exception ex) {
                //ignore any exception, dont want to stop context loading
                logger.error("Error occured while creating search client!", ex);
            }
        }
        return client;
    }

}
