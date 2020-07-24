package com.woc.util;

import com.woc.service.enums.PushNotificationIdentifierEnum;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class PushNotificationRequestBodyUtility {

    private static final String IDENTIFIER = "identifier";
    private static final String DATA = "data";
    private static final String TITLE = "title";
    private static final String BODY = "body";

    public static JSONObject buildPushNotificationRequestBody(PushNotificationIdentifierEnum identifier, Map<String, Object> payload, List<String> fcmTokens) {
        JSONObject notificationRequestBody = new JSONObject();
        String fcmTokenKey = fcmTokens.size() < 2 ? "to" : "registration_ids";
        Object fcmTokenValue = fcmTokens.size() < 2 ? fcmTokens.get(0) : fcmTokens;

        notificationRequestBody.put(fcmTokenKey, fcmTokenValue);
        JSONObject data = new JSONObject();

        switch (identifier) {
            case DRIVER_VERIFICATION:
                data.put(TITLE, "Verification");
                data.put(BODY, "Your verification is complete. You can now accept rides.");
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.DRIVER_VERIFICATION.toString());
                break;
            case RIDE_REQUEST_FOUND:
                data.put(TITLE, "Ride Request");
                data.put(BODY, "You have a ride request. Kindly click to accept/reject ride.");
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.RIDE_REQUEST_FOUND.toString());
                data.put("rideRequestId", payload.get("rideRequestId"));
                break;
            case RIDE_CANCELLED_BY_DRIVER:
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.RIDE_CANCELLED_BY_DRIVER.toString());
                data.put(TITLE, "Driver Cancelled Ride");
                data.put(BODY, "Your ride request got cancelled. Kindly search for rides again.");
                break;
            case DRIVER_ENROUTE:
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.DRIVER_ENROUTE.toString());
                data.put("rideRequestId", payload.get("rideRequestId"));
                data.put("driverName", payload.get("driverName"));
                data.put("rating", payload.get("rating"));
                data.put("vehicle", payload.get("vehicle"));
                data.put("phone", payload.get("phone"));
                data.put("liveLocation", payload.get("liveLocation"));
                break;
            case RIDE_CANCELLED_BY_RIDER:
                data.put(TITLE, "Ride cancelled");
                data.put(BODY, "Rider has cancelled. Click ok to go back to home screen.");
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.RIDE_CANCELLED_BY_RIDER.toString());
                break;
            case TRIP_START:
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.TRIP_START.toString());
                break;
            case TRIP_END:
                data.put(IDENTIFIER, PushNotificationIdentifierEnum.TRIP_END.toString());
                data.put("duration", payload.get("duration"));
                data.put("distance", payload.get("distance"));
                data.put("fare", payload.get("fare"));
                break;
        }

        notificationRequestBody.put(DATA, data);
        return notificationRequestBody;
    }
}
