package com.example.contact;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;


public class Data {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("topics")
    @Expose
    private ArrayList<Topic> topics = null;
    @SerializedName("msgCode")
    @Expose
    private String msgCode;
    @SerializedName("sttCode")
    @Expose
    private String sttCode;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Topic> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        this.topics = topics;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getSttCode() {
        return sttCode;
    }

    public void setSttCode(String sttCode) {
        this.sttCode = sttCode;
    }
}
