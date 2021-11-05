package com.alten.cancunInn.exceptions;

public class BookingNotFoundException extends RuntimeException{
    public BookingNotFoundException(String msg){
        super(msg);
    }
}
