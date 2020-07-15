package com.adtrack.buildResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.adtrack.dto.CustomerDataResponse;
import com.adtrack.entity.CustomerTrackInfo;

import lombok.Getter;

/*This class use to Build response. */
@Getter
public class Response {
   
	private static final String SUCCESS = "SUCCESS";
    private String status = SUCCESS;
    private String message = null;
    private Object data = null;

    public static ResponseEntity<CustomerTrackInfo> success(CustomerTrackInfo data ,HttpStatus status) {
        return new ResponseEntity<CustomerTrackInfo>(data,status);
    }
    public static ResponseEntity<CustomerDataResponse> success(CustomerDataResponse custInfo, HttpStatus status) {
        return new ResponseEntity<CustomerDataResponse>(custInfo, status);
    }
    public static Response success(String message, Object data) {
        return new Response(SUCCESS, message, data);
    }
    private Response()
    {
    }
    
    private Response(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    private Response(String status, String message) {
        this(status, message, null);
    }


}
