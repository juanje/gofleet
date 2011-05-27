package org.gofleet.configuration;

import org.apache.commons.configuration.ConfigurationFactory;
import org.apache.commons.logging.LogFactory;

public class Configuration {

	private static org.apache.commons.configuration.Configuration configuration = null;
	private static org.apache.commons.logging.Log log = LogFactory
			.getLog(Configuration.class);

	// @Autowired
	// private static DataSource datasource;

	static {
		try {
			ConfigurationFactory factory = new ConfigurationFactory(
					"config.xml");
			configuration = factory.getConfiguration();
			// DatabaseConfiguration ddbbconfig = new DatabaseConfiguration(
			// datasource, "table", "key", "value");
		} catch (Throwable t) {
			log.error("Error loading configuration: " + t);

			configuration = new org.apache.commons.configuration.CombinedConfiguration();
		}
	}

	static public org.apache.commons.configuration.Configuration getConfiguration() {
		return configuration;
	}
}
