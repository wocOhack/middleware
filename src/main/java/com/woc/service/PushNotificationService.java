package com.woc.service;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.woc.util.PushNotificationRequestBodyUtility;
import com.woc.service.enums.PushNotificationIdentifierEnum;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class PushNotificationService {

	private static final String PUSH_NOTIF_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
	private static final String FCM_SERVER_KEY = "AAAAiQ--Yns:APA91bFmgiNOhreo6kA3bfQN5DFqeSSEN6V8uEJYPyuQR1-GOYl5BJgcWa3qI7n8K-foQieSuelaS_6_8RLzAkGBh9Bnwga6vWPh4mJqBXpNw7KI-mRffLZGbodJu10R5MvK7Mxmlnmv";

	public void send(PushNotificationIdentifierEnum identifier, Object payload, List<String> androidIds) throws URISyntaxException {
		if(androidIds == null || androidIds.size() == 0) {
			return;
		}

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.AUTHORIZATION, "key=" + FCM_SERVER_KEY);
		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Object> httpEntity = null;

		try {
			URI uri = new URI(PUSH_NOTIF_ENDPOINT);

			JSONObject pushNotificationRequestBody = PushNotificationRequestBodyUtility.buildPushNotificationRequestBody(
					identifier, payload, androidIds);

			httpEntity = new HttpEntity<>(pushNotificationRequestBody.toString(), headers);

			ResponseEntity<String> response = restTemplate.postForEntity(uri, httpEntity, String.class);
			System.out.println(response);

		} catch (Exception e) {
			return;
		}
		return;
	}
}
