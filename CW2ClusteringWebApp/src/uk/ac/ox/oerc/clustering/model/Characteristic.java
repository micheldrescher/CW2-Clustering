/**
 * 
 */
package uk.ac.ox.oerc.clustering.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author The NIST cloud computing characteristics
 *
 */
public enum Characteristic {
	
	ONDEMANDSELFSERVICE("On-demand self-service", Status.FINAL, 
			"A consumer can unilaterally provision computing capabilities, such as server time " +
			"and network storage, as needed automatically without requiring human interaction with each service provider."),
	
	BROADNETWORKACCESS("Broad network access", Status.FINAL, 
			"Capabilities are available over the network and accessed through standard mechanisms that promote use by "
			+ "heterogeneous thin or thick client platforms (e.g., mobile phones, tablets, laptops, and workstations)."),

	RESOURCEPOOLING("Resource pooling", Status.FINAL, 
			"The provider’s computing resources are pooled to serve multiple consumers using a multi-tenant model, with "
			+ "different physical and virtual resources dynamically assigned and reassigned according to consumer demand. "
			+ "There is a sense of location independence in that the customer generally has no control or knowledge over "
			+ "the exact location of the provided resources but may be able to specify location at a higher level of "
			+ "abstraction (e.g., country, state, or datacenter). Examples of resources include storage, processing, "
			+ "memory, and network bandwidth."),

	RAPIDELASTICIYY("Rapid elasticity", Status.FINAL, 
			"Capabilities can be elastically provisioned and released, in some cases automatically, to scale rapidly "
			+ "outward and inward commensurate with demand. To the consumer, the capabilities available for provisioning "
			+ "often appear to be unlimited and can be appropriated in any quantity at any time."),
	
	MEASUREDSERVICE("Measured service", Status.FINAL, 
			"Cloud systems automatically control and optimize resource use by leveraging a metering capability1 at some "
			+ "level of abstraction appropriate to the type of service (e.g., storage, processing, bandwidth, and active "
			+ "user accounts). Resource usage can be monitored, controlled, and reported, providing transparency for both "
			+ "the provider and consumer of the utilized service."),
	
	MASSIVESCALE("Massive scale", Status.DRAFT, 
			"A cloud platform may, depending on the resources offered, provide individual users with access to"
			+ "large-scale or even massive-scale computing."),
	
	HOMOGENEITY("Homogeneity", Status.DRAFT, 
			"In many situations it is advantageous to both customers and providers to have essentially homogeneous"
			+ "systems at their disposal. Where requirements are particularly difficult or unusual, a cloud platform may"
			+ "be built out of non-homogeneous systems and components."),
	
	VIRTUALISATION("Virtualisation", Status.DRAFT, 
			"Virtualization of machines as software systems massively increases the scale of cloud resources that can"
			+ "be made available. Virtualization is not an essential characteristic but it is becoming the only way that"
			+ "scale demands can be met by providers; customers generally don’t care either way as the virtualization"
			+ "is entirely transparent."),
	
	LOWCOSTSOFTWARE("Low cost software", Status.DRAFT, 
			"If increased scale reduces per-unit, or per-use cost, then cloud computing offers a drive towards lowercost"
			+ "software. It is important to note that this may not be the case across all sectors and activities."),
	
	RESILIENTCOMPUTING("Resilient computing", Status.DRAFT, 
			"In some sectors, continuous availability of computing with zero-downtime is crucial to the sectors"
			+ "requirements, for example, emergency and financial systems. In these sectors requirements for"
			+ "resilient, rather than just fail-safe computing will be the norm."),
	
	GEOGRAPHICDISTRIBUTION("Geographic distribution", Status.DRAFT, 
			"Some sectors have legal requirements that physical data stores are in particular geographical"
			+ "jurisdictions. This places certain restrictions on providers favouring a cloud-anywhere model. "
			+ "More commonly the user is not concerned about location per se."),
	
	SERVICEORIENTATION("Service orientation", Status.DRAFT, 
			"The design of the services that run and operate on the cloud frameworks are normally operated as"
			+ "services such they can take advantage of other factors that give resilience. This includes the "
			+ "ability to scale different components within the system depending on their load and capability."),
	
	ADVANCEDSECURITY("Advanced security", Status.DRAFT, 
			"There may be the capability to perform both system and network level security within the cloud system.");
	
	private static List<Characteristic> finalNIST;
	private static List<Characteristic> draftNIST;
	static {
		List<Characteristic> nist5 = new ArrayList<Characteristic>();
		List<Characteristic> nist8 = new ArrayList<Characteristic>();
		for (Characteristic c : Characteristic.values()) {
			if (c.getStatus().equals(Status.FINAL)) {
				nist5.add(c);
			} else {
				nist8.add(c);
			}
		}
		finalNIST = Collections.unmodifiableList(nist5);
		draftNIST = Collections.unmodifiableList(nist8);
	}

	
	private String fullName;
	private Status status;
	private String description;
	
	private Characteristic(String fullName, Status status, String description) {
		this.setFullName(fullName);
		this.setStatus(status);
		this.setDescription(description);
	}
	
	public static Characteristic getByName(String name) {
		for (Characteristic c : Characteristic.values()) {
			if (c.name().equals(name)) return c;
		}
		return null;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	private void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	private void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	private void setDescription(String description) {
		this.description = description;
	}

	public enum Status {
		FINAL, DRAFT;
	}

	public static String[] getFullNames() {
		String[] result = new String[values().length];
		for (int i = 0; i< values().length; i++) {
			result[i] = values()[i].getFullName();
		}
		return result;
	}
	
	public int getOrdinal() {
		return ordinal();
	}
	
	public String getName() {
		return name();
	}
	
	public static List<Characteristic> getFinalNIST() {
		return finalNIST;
	}
	
	public static List<Characteristic> getDraftNIST() {
		return draftNIST;
	}

	public static List<Characteristic> getAll() {
		return Arrays.asList(values());
	}

}
