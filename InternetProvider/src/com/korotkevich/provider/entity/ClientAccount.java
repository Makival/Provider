package com.korotkevich.provider.entity;

import java.math.BigDecimal;

/**
 * 
 * @author Korotkevich Kirill 2018-05-10
 *
 */
public class ClientAccount {
	private int clientId;
	private BigDecimal cashBalance;
	private double trafficBalance;

	/**
	 * Default constructor
	 */
	public ClientAccount() {
		
	}
	
	/**
	 * Constructor with clientId
	 * @param clientId
	 */
	public ClientAccount(int clientId) {
		this.clientId = clientId;
	}
	
	/**
	 * Constructor with cashBalance, trafficBalance, clientId   
	 * @param cashBalance
	 * @param trafficBalance
	 * @param clientId
	 */
	public ClientAccount(double cashBalance, double trafficBalance, int clientId) {
		this.cashBalance = constructBigDecimal(cashBalance);
		this.trafficBalance = trafficBalance;
		this.clientId = clientId;
	}

	/**
	 * Get the cash balance
	 * @return cashBalance
	 */
	public double getCashBalance() {
		return cashBalance.setScale(CurrencyParameter.BYN.getScale(), CurrencyParameter.BYN.getRounding()).doubleValue();
	}

	/**
	 * Set the cash balance
	 * @param cashBalance
	 */
	public void setCashBalance(BigDecimal cashBalance) {
		this.cashBalance = cashBalance;
	}

	/**
	 * Get the user id of the client account
	 * @return clientId
	 */
	public int getClientId() {
		return clientId;
	}

	/**
	 * Set the user id in the client account
	 * @param clientId
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	/**
	 * Set the traffic balance in the client account
	 * @param trafficBalance
	 */
	public void setTrafficBalance(double trafficBalance) {
		this.trafficBalance = trafficBalance;
	}

	/**
	 * Get the traffic balance of the client account
	 * @return trafficBalance
	 */
	public double getTrafficBalance() {
		return trafficBalance;
	}
	
	@Override
	public String toString() {
		return "ClientAccount [clientId=" + clientId + ", cashBalance=" + cashBalance + ", trafficBalance="
				+ trafficBalance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cashBalance == null) ? 0 : cashBalance.hashCode());
		result = prime * result + clientId;
		long temp;
		temp = Double.doubleToLongBits(trafficBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ClientAccount other = (ClientAccount) obj;
		if (cashBalance == null) {
			if (other.cashBalance != null)
				return false;
		} else if (!cashBalance.equals(other.cashBalance))
			return false;
		if (clientId != other.clientId)
			return false;
		if (Double.doubleToLongBits(trafficBalance) != Double.doubleToLongBits(other.trafficBalance))
			return false;
		return true;
	}

	/**
	 * Method converts double value to BigDecimal object
	 * @param value
	 * @return bigDecimalValue
	 */
	private BigDecimal constructBigDecimal(double value) {
		BigDecimal bigDecimalValue = new BigDecimal(value);
		return bigDecimalValue;
	}
	
}
