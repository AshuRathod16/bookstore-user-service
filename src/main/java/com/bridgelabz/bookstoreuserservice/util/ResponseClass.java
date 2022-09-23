package com.bridgelabz.bookstoreuserservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseClass {
    private int statusCode;
    private String statusMessage;
    private Object data;

    public ResponseClass() {
    }
}

