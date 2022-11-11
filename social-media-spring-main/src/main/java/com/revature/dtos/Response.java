package com.revature.dtos;

import java.util.Objects;

public class Response {
    String message;
    public Response(){

    }
    public Response(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(getMessage(), response.getMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage());
    }

    @Override
    public String toString() {
        return this.message;
    }
}
