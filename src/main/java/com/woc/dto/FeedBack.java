package com.woc.dto;

public class FeedBack {

	private int tripId;
	private int rating;
	private String comments;
	
	
	public int getTripId() { return tripId; }
	public void setTripId(int tripId) { this.tripId = tripId; }
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
