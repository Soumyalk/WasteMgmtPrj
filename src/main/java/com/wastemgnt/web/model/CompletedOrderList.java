package com.wastemgnt.web.model;

import java.io.Serializable;
import java.util.Date;

public class CompletedOrderList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String deliveredStatus;
	private Date   updatedDate;
	private Long  orderId;
	
	
	
	
	public CompletedOrderList() {
		super();
		// TODO Auto-generated constructor stub
	}

	


	public CompletedOrderList(String deliveredStatus, Date updatedDate, Long orderId) {
		super();
		this.deliveredStatus = deliveredStatus;
		this.updatedDate = updatedDate;
		this.orderId = orderId;
	}




	public String getDeliveredStatus() {
		return deliveredStatus;
	}




	public void setDeliveredStatus(String deliveredStatus) {
		this.deliveredStatus = deliveredStatus;
	}




	public Date getUpdatedDate() {
		return updatedDate;
	}




	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}




	public Long getOrderId() {
		return orderId;
	}




	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	@Override
	public String toString() {
		return "CompletedOrderList [deliveredStatus=" + deliveredStatus + ", updatedDate=" + updatedDate + ", orderId="
				+ orderId + "]";
	}
	
	
}
