package com.barbeque.response.util;
import javax.ws.rs.core.Response;

public class ResponseGenerator {
    public static Response generateResponse(Object responseObject) {
        return Response
                .ok()
                // 200
                .entity(responseObject)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE, PUT").build();
    }

    public static Response generateSuccessResponse(GenericResponse responseObject, String message) {
        responseObject.setMessageType("SUCCESS");
        responseObject.setMessage(message);
        return Response
                .ok()
                // 200
                .entity(responseObject)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE, PUT").build();
    }

    public static Response generateFailureResponse(GenericResponse responseObject, String message) {
        responseObject.setMessageType("FAILURE");
        responseObject.setMessage(message);
        return Response
                .ok()
                // 200
                .entity(responseObject)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE, PUT").build();
    }
}
