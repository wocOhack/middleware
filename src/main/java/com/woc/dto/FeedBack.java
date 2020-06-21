package com.woc.dto;

public class FeedBack {

	private String feedBackOwnerID;
	private String feedBackProviderID;
	private int rating;
	private String comments;
	
	
	public String getFeedBackOwnerID() {
		return feedBackOwnerID;
	}
	public void setFeedBackOwnerID(String feedBackOwnerID) {
		this.feedBackOwnerID = feedBackOwnerID;
	}
	public String getFeedBackProviderID() {
		return feedBackProviderID;
	}
	public void setFeedBackProviderID(String feedBackProviderID) {
		this.feedBackProviderID = feedBackProviderID;
	}
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
