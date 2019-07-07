package com.shim.user.shimapplication.retrofit.response;

import com.google.gson.annotations.SerializedName;
import com.shim.user.shimapplication.room.Asmr;

import java.util.List;

public class AsmrListResponse {
    private int status;
    @SerializedName("arr")
    private List<Asmr> data;

    public AsmrListResponse(int status, List<Asmr> data) {
        this.status = status;
        this.data = data;
    }

    public List<Asmr> getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }
}
