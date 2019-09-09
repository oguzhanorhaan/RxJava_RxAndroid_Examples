package com.example.rxjava_retrofit_example.Models;

import com.example.rxjava_retrofit_example.Clients.BaseResponse;

public class Note extends BaseResponse {
    int id;
    String note;
    String timestamp;

    public int getId() {
        return id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
