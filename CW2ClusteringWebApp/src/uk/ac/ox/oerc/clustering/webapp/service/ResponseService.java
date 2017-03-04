package uk.ac.ox.oerc.clustering.webapp.service;

import java.util.List;

import uk.ac.ox.oerc.clustering.model.Response;

/**
 * This interface abstracts away data access for the Response entity.
 * 
 * @author Michel Drescher
 */
public interface ResponseService {
	
	public List<Response> getAllResponses();

	public List<Response> getAllValidatedResponses();
	
	public Response createNewResponse();
	
	public void saveResponse(Response response);

	public List<Response> getUnvalidatedResponses();

	public void deleteResponse(long id);
	
	public void validateResponse(long id);

}
