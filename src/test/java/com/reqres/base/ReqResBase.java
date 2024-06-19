package com.reqres.base;

public abstract class ReqResBase {
    private final int page, per_page, total, total_pages;

    public ReqResBase (int page, int per_page, int total, int total_pages){
        this.page = page;
        this.per_page = per_page;
        this.total = total;
        this.total_pages = total_pages;
    }

    public int getPage() {
        return page;
    }

    public int getPer_page() {
        return per_page;
    }

    public int getTotal() {
        return total;
    }

    public int getTotal_pages() {
        return total_pages;
    }
}
