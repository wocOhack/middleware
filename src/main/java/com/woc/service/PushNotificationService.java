package com.woc.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.woc.dto.MultiplePushNotificationRequestBodyDto;
import com.woc.dto.NotificationBodyDto;
import com.woc.dto.SinglePushNotificationRequestBodyDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component
public class PushNotificationService {

	private static final String PUSH_NOTIF_ENDPOINT = "https://fcm.googleapis.com/fcm/send";
	private static final String FCM_SERVER_KEY = "AAAAiQ--Yns:APA91bFmgiNOhreo6kA3bfQN5DFqeSSEN6V8uEJYPyuQR1-GOYl5BJgcWa3qI7n8K-foQieSuelaS_6_8RLzAkGBh9Bnwga6vWPh4mJqBXpNw7KI-mRffLZGbodJu10R5MvK7Mxmlnmv";

	public void send(String identifier, Object payload, List<String> androidIds) throws URISyntaxException {
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

			if (androidIds.size() == 1) {
				SinglePushNotificationRequestBodyDto singlePushNotificationRequestBodyDto = new SinglePushNotificationRequestBodyDto();
				singlePushNotificationRequestBodyDto.setTo(androidIds.get(0));

				NotificationBodyDto notification = new NotificationBodyDto(identifier, payload);
				singlePushNotificationRequestBodyDto.setData(notification);

				httpEntity = new HttpEntity<>(singlePushNotificationRequestBodyDto, headers);
			} else {
				MultiplePushNotificationRequestBodyDto multiplePushNotificationRequestBodyDto = new MultiplePushNotificationRequestBodyDto();
				multiplePushNotificationRequestBodyDto.setRegistrationIds((String[]) androidIds.toArray());

				NotificationBodyDto notification = new NotificationBodyDto(identifier, payload);
				multiplePushNotificationRequestBodyDto.setData(notification);

				httpEntity = new HttpEntity<>(multiplePushNotificationRequestBodyDto, headers);
			}

			ResponseEntity<String> response = restTemplate.postForEntity(uri, httpEntity, String.class);

		} catch (Exception e) {
			return;
		}
		return;
	}
}
