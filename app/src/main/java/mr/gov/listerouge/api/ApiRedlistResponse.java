package mr.gov.listerouge.api;

import java.util.List;

import mr.gov.listerouge.models.RedListItem;

public class ApiRedlistResponse {

    private List<RedListItem> data;
    private int total;
    private String page;
    private String limit;

    // Getters and Setters

    public List<RedListItem> getData() {
        return data;
    }
    public void setData(List<RedListItem> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }

    public String getLimit() {
        return limit;
    }
    public void setLimit(String limit) {
        this.limit = limit;
    }
}