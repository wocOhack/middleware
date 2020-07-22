package com.woc.util;

import com.woc.service.enums.PushNotificationIdentifierEnum;
import org.json.JSONObject;

import java.util.List;

public class PushNotificationRequestBodyUtility {

    private static final String IDENTIFIER = "identifier";
    private static final String DATA = "data";

    public static JSONObject buildPushNotificationRequestBody(PushNotificationIdentifierEnum identifier, Object payload, List<String> fcmTokens) {
        JSONObject notificationRequestBody = new JSONObject();
        String fcmTokenKey = fcmTokens.size() < 2 ? "to" : "registration_ids";
        Object fcmTokenValue = fcmTokens.size() < 2 ? fcmTokens.get(0) : fcmTokens;

        notificationRequestBody.put(fcmTokenKey, fcmTokenValue);
        JSONObject data = new JSONObject();

        switch (identifier) {
            case TRIP_START:
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.TRIP_START.toString());
                break;
        }

        notificationRequestBody.put(DATA, data);
        return notificationRequestBody;
    }
}
