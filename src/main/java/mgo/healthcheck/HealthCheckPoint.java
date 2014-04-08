package mgo.healthcheck;

/**
 * Contract for health checked components 
 */
public interface HealthCheckPoint {
	
	public String description();
	
	public String getMessage();
	
	boolean checkStatus();
}
