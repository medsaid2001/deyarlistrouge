package mr.gov.listerouge.api;

import android.content.ClipData;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import mr.gov.listerouge.models.RelistResponse;

public class ApiResponse {
    @SerializedName("data")
    private List<RelistResponse> data;

    @SerializedName("total")
    private int total;

    public List<RelistResponse> getData() {
        return data;
    }

    public int getTotal() {
        return total;
    }
}
