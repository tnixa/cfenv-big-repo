package org.terrence.cfenvprocessors.db2;

import java.util.Map;
import java.util.logging.Logger;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;

public class DB2CfEnvProcessor implements CfEnvProcessor {

    private static final Logger LOG = Logger.getLogger(DB2CfEnvProcessor.class.getName());

    public DB2CfEnvProcessor() {
        LOG.info("DB2CfEnvProcessor built");
    }

    @Override
    public boolean accept(CfService service) {
        boolean match = service.existsByLabelStartsWith("dashDB For Transactions");
        LOG.info("Match [" + match + "] to service " + service.toString());
        return match;
    }

    @Override
    public CfEnvProcessorProperties getProperties() {
        return CfEnvProcessorProperties.builder().propertyPrefixes("spring.datasource").serviceName("DB2").build();
    }

    @Override
    public void process(CfCredentials cfCredentials, Map<String, Object> properties) {
        properties.put("spring.datasource.url", cfCredentials.getUri("http"));
        properties.put("spring.datasource.username", cfCredentials.getUsername());
        properties.put("spring.datasource.password", cfCredentials.getPassword());
    }
}