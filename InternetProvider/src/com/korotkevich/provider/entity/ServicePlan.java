package com.korotkevich.provider.entity;

import java.math.BigDecimal;

/**
 * 
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class ServicePlan {
	private int id;
	private String name;
	private String signingDate;
	private String cancelationDate;
	private String description;
	private int trafficLimit;
	private BigDecimal monthlyFee;
	private BigDecimal accessCost;
	private boolean relevant;
	private Promo promo;
	
	/**
	 * Default constructor
	 */
	public ServicePlan(){
	}
	
	/**
	 * Constructor with id
	 * @param id
	 */
	public ServicePlan(int id){
		this.id = id;
	}
	
	/**
	 * Constructor with name, trafficLimit, monthlyFee
	 * @param name
	 * @param trafficLimit
	 * @param monthlyFee
	 */
	public ServicePlan(String name, int trafficLimit, double monthlyFee){
		this.name = name;
		this.trafficLimit = trafficLimit;
		this.monthlyFee = constructBigDecimal(monthlyFee);
	}
	
	/**
	 * Constructor with id, name, trafficLimit, monthlyFee, accessCost, promo  
	 * @param id
	 * @param name
	 * @param trafficLimit
	 * @param monthlyFee
	 * @param accessCost
	 * @param promo
	 */
	public ServicePlan(int id, String name, int trafficLimit, double monthlyFee, double accessCost, Promo promo){
		this.id = id;
		this.name = name;
		this.trafficLimit = trafficLimit;
		this.monthlyFee = constructBigDecimal(monthlyFee);
		this.accessCost = constructBigDecimal(accessCost);
		this.promo = promo;
	}
	
	/**
	 * Constructor with name, trafficLimit, monthlyFee, signingDate   
	 * @param name
	 * @param trafficLimit
	 * @param monthlyFee
	 * @param signingDate
	 */
	public ServicePlan(String name, int trafficLimit, double monthlyFee, String signingDate){
		this.name = name;
		this.trafficLimit = trafficLimit;
		this.monthlyFee = constructBigDecimal(monthlyFee);
		this.signingDate = signingDate;
	}

	/**
	 * Constructor with name, trafficLimit, monthlyFee, description, accessCost
	 * @param name
	 * @param trafficLimit
	 * @param monthlyFee
	 * @param description
	 * @param accessCost
	 */
	public ServicePlan(String name, int trafficLimit, double monthlyFee, String description, double accessCost){
		this.name = name;
		this.trafficLimit = trafficLimit;
		this.monthlyFee = constructBigDecimal(monthlyFee);
		this.description = description;
		this.accessCost = constructBigDecimal(accessCost);
	}
	
	/**
	 * Constructor with id, name, trafficLimit, monthlyFee, description, accessCost
	 * @param id
	 * @param name
	 * @param trafficLimit
	 * @param monthlyFee
	 * @param description
	 * @param accessCost
	 */
	public ServicePlan(int id, String name, int trafficLimit, double monthlyFee, String description, double accessCost){
		this.id = id;
		this.name = name;
		this.trafficLimit = trafficLimit;
		this.monthlyFee = constructBigDecimal(monthlyFee);
		this.description = description;
		this.accessCost = constructBigDecimal(accessCost);
	}
	
	/**
	 * Constructor with id, name, trafficLimit, monthlyFee, description, accessCost, relevant, promo
	 * @param id
	 * @param name
	 * @param trafficLimit
	 * @param monthlyFee
	 * @param description
	 * @param accessCost
	 * @param relevant
	 * @param promo
	 */
	public ServicePlan(int id, String name, int trafficLimit, double monthlyFee, String description, double accessCost, boolean relevant, Promo promo){
		this.id = id;
		this.name = name;
		this.trafficLimit = trafficLimit;
		this.monthlyFee = constructBigDecimal(monthlyFee);
		this.description = description;
		this.accessCost = constructBigDecimal(accessCost);
		this.relevant = relevant;
		this.promo = promo;
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
	 * Get the signingDate
	 * @return signingDate
	 */
	public String getSigningDate() {
		return signingDate;
	}

	/**
	 * Set the signingDate
	 * @param signingDate
	 */
	public void setSigningDate(String signingDate) {
		this.signingDate = signingDate;
	}

	/**
	 * Get the cancelationDate
	 * @return cancelationDate
	 */
	public String getCancelationDate() {
		return cancelationDate;
	}

	/**
	 * Set the cancelationDate
	 * @param cancelationDate
	 */
	public void setCancelationDate(String cancelationDate) {
		this.cancelationDate = cancelationDate;
	}

	/**
	 * Get the trafficLimit
	 * @return trafficLimit
	 */
	public int getTrafficLimit() {
		return trafficLimit;
	}

	/**
	 * Get the description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the relevant
	 * @return relevant
	 */
	public boolean isRelevant() {
		return relevant;
	}

	/**
	 * Set the isRelevant
	 * @param isRelevant
	 */
	public void setRelevant(boolean isRelevant) {
		this.relevant = isRelevant;
	}
	
	/**
	 * Get the promo id
	 * @return id
	 */
	public int getPromoId() {
		int promoId = 0;
		if (promo != null) {
			promoId = promo.getId();
		}
		return promoId;
	}
	
	/**
	 * Get the promo name
	 * @return name
	 */
	public String getPromoName() {
		String promoName = "";
		if (promo != null) {
			promoName = promo.getName();
		}
		return promoName;
	}
	
	/**
	 * Get the promo discount
	 * @return accessDiscount
	 */
	public int getPromoDiscount() {
		int promoDiscount = 0;
		if (promo != null) {
			promoDiscount = promo.getAccessDiscount();
		}
		return promoDiscount;
	}
	
	/**
	 * Get the trafficBonus discount
	 * @return trafficBonus
	 */
	public int getPromoTrafficBonus() {
		int promoTrafficBonus = 0;
		if (promo != null) {
			promoTrafficBonus = promo.getTrafficBonus();
		}
		return promoTrafficBonus;
	}

	/**
	 * Get the monthlyFee
	 * @return monthlyFee
	 */
	public double getMonthlyFee() {
		return monthlyFee.setScale(CurrencyParameter.BYN.getScale(), CurrencyParameter.BYN.getRounding()).doubleValue();
	}

	/**
	 * Set the monthlyFee
	 * @param monthlyFee
	 */
	public void setMonthlyFee(BigDecimal monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	/**
	 * Get the accessCost
	 * @return accessCost
	 */
	public double getAccessCost() {
		return accessCost.setScale(CurrencyParameter.BYN.getScale(), CurrencyParameter.BYN.getRounding()).doubleValue();
	}

	/**
	 * Set the accessCost
	 * @param accessCost
	 */
	public void setAccessCost(BigDecimal accessCost) {
		this.accessCost = accessCost;
	}

	/**
	 * Get the promo
	 * @return promo
	 */
	public Promo getPromo() {
		return promo;
	}

	/**
	 * Set the promo
	 * @param promo
	 */
	public void setPromo(Promo promo) {
		this.promo = promo;
	}

	/**
	 * Set the trafficLimit
	 * @param trafficLimit
	 */
	public void setTrafficLimit(int trafficLimit) {
		this.trafficLimit = trafficLimit;
	}
	
	/**
	 * Method converts double value to BigDecimal object
	 * @param value - type double
	 * @return bigDecimalValue
	 */
	private BigDecimal constructBigDecimal(double value) {
		BigDecimal bigDecimalValue = new BigDecimal(value);
		return bigDecimalValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessCost == null) ? 0 : accessCost.hashCode());
		result = prime * result + ((cancelationDate == null) ? 0 : cancelationDate.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((monthlyFee == null) ? 0 : monthlyFee.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((promo == null) ? 0 : promo.hashCode());
		result = prime * result + (relevant ? 1231 : 1237);
		result = prime * result + ((signingDate == null) ? 0 : signingDate.hashCode());
		result = prime * result + trafficLimit;
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
		ServicePlan other = (ServicePlan) obj;
		if (accessCost == null) {
			if (other.accessCost != null)
				return false;
		} else if (!accessCost.equals(other.accessCost))
			return false;
		if (cancelationDate == null) {
			if (other.cancelationDate != null)
				return false;
		} else if (!cancelationDate.equals(other.cancelationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (monthlyFee == null) {
			if (other.monthlyFee != null)
				return false;
		} else if (!monthlyFee.equals(other.monthlyFee))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (promo == null) {
			if (other.promo != null)
				return false;
		} else if (!promo.equals(other.promo))
			return false;
		if (relevant != other.relevant)
			return false;
		if (signingDate == null) {
			if (other.signingDate != null)
				return false;
		} else if (!signingDate.equals(other.signingDate))
			return false;
		if (trafficLimit != other.trafficLimit)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ServicePlan [id=" + id + ", name=" + name + ", signingDate=" + signingDate + ", cancelationDate="
				+ cancelationDate + ", description=" + description + ", trafficLimit=" + trafficLimit + ", monthlyFee="
				+ monthlyFee + ", accessCost=" + accessCost + ", relevant=" + relevant + ", promo=" + promo + "]";
	}
	
	

}
