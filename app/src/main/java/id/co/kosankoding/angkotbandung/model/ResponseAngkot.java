package id.co.kosankoding.angkotbandung.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAngkot {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Angkot> data;

    public String getStatus() {
        return status;
    }

    public List<Angkot> getData() {
        return data;
    }
}
