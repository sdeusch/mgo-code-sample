package sun.healthcheck;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;

@Configuration
@Component
public class MongoDBHealthCheck implements HealthCheckPoint {

	private static final Logger LOG = Logger
			.getLogger(MongoDBHealthCheck.class);

	private static final String DESRCRIPTION = "MongoDB";

	private boolean status;
	private String message = "Unknown";

	@Override
	public boolean checkStatus() {
		synchronized (this) {
				try {
					MongoClient mongo = new MongoClient("localhost", 27017);
					this.message = mongo.getAddress().toString();
					mongo.getConnector().getDBPortPool(mongo.getAddress()).get().ensureOpen();
					this.status = true;
				} catch (Exception ex) {
					LOG.error("Connection to mongoDB is closed!", ex);
					this.message = ex.getMessage().substring(0,75).concat("...");
					this.status = false;
				}
				return this.status;
		}
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String description() {
		return DESRCRIPTION;
	}

}
