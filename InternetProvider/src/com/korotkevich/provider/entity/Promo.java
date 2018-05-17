package com.korotkevich.provider.entity;

/**
 * 
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class Promo {
	private int id;
	private String name;
	private int accessDiscount;
	private int trafficBonus;
	private boolean active;
	private int servicePlanId;	
	
	/**
	 * Default constructor
	 */
	public Promo() {

	}
	
	/**
	 * Constructor with id
	 * @param id
	 */
	public Promo(int id){
		this.id = id;
	}
	
	/**
	 * Constructor with id, name
	 * @param promoId
	 * @param name
	 */
	public Promo(int id, String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Constructor with name, accessDiscount, trafficBonus, servicePlanId 
	 * @param name
	 * @param accessDiscount
	 * @param trafficBonus
	 * @param servicePlanId
	 */
	public Promo(String name, int accessDiscount, int trafficBonus, int servicePlanId){
		this.name = name;
		this.accessDiscount = accessDiscount;
		this.trafficBonus = trafficBonus;
		this.active = true;
		this.servicePlanId = servicePlanId;
	}
	
	/**
	 * Constructor with id, name, accessDiscount, trafficBonus, servicePlanId
	 * @param id
	 * @param name
	 * @param accessDiscount
	 * @param trafficBonus
	 * @param servicePlanId
	 */
	public Promo(int id, String name, int accessDiscount, int trafficBonus, int servicePlanId){
		this.id = id;
		this.name = name;
		this.accessDiscount = accessDiscount;
		this.trafficBonus = trafficBonus;
		this.active = true;
		this.servicePlanId = servicePlanId;
	}
	
	/**
	 * Constructor with id, name, accessDiscount, trafficBonus, active, servicePlanId 
	 * @param id
	 * @param name
	 * @param accessDiscount
	 * @param trafficBonus
	 * @param active
	 * @param servicePlanId
	 */
	public Promo(int id, String name, int accessDiscount, int trafficBonus, boolean active, int servicePlanId){
		this.id = id;
		this.name = name;
		this.accessDiscount = accessDiscount;
		this.trafficBonus = trafficBonus;
		this.active = active;
		this.servicePlanId = servicePlanId;
	}

	/**
	 * Get the id
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set the id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Get the name
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the accessDiscount
	 * @return accessDiscount
	 */
	public int getAccessDiscount() {
		return accessDiscount;
	}

	/**
	 * Set the accessDiscount
	 * @param accessDiscount
	 */
	public void setAccessDiscount(int accessDiscount) {
		this.accessDiscount = accessDiscount;
	}

	/**
	 * Get the active
	 * @return active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Set the active
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Get the trafficBonus
	 * @return trafficBonus
	 */
	public int getTrafficBonus() {
		return trafficBonus;
	}

	/**
	 * Set the trafficBonus
	 * @param trafficBonus
	 */
	public void setTrafficBonus(int trafficBonus) {
		this.trafficBonus = trafficBonus;
	}

	/**
	 * Get the servicePlanId
	 * @return servicePlanId
	 */
	public int getServicePlanId() {
		return servicePlanId;
	}

	/**
	 * Set the servicePlanId
	 * @param servicePlanId
	 */
	public void setServicePlanId(int servicePlanId) {
		this.servicePlanId = servicePlanId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accessDiscount;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + servicePlanId;
		result = prime * result + trafficBonus;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Promo other = (Promo) obj;
		if (accessDiscount != other.accessDiscount)
			return false;
		if (active != other.active)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (servicePlanId != other.servicePlanId)
			return false;
		if (trafficBonus != other.trafficBonus)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Promo [id=" + id + ", name=" + name + ", accessDiscount=" + accessDiscount + ", trafficBonus="
				+ trafficBonus + ", active=" + active + ", servicePlanId=" + servicePlanId + "]";
	}

	
	
}
