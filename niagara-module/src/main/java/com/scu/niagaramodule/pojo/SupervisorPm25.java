package com.scu.niagaramodule.pojo;

public class SupervisorPm25 {
    private Integer id;

    private Long timestamp;

    private Integer trendflags;

    private Integer status;

    private Double value;

    private String trendflagsTag;

    private String statusTag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getTrendflags() {
        return trendflags;
    }

    public void setTrendflags(Integer trendflags) {
        this.trendflags = trendflags;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getTrendflagsTag() {
        return trendflagsTag;
    }

    public void setTrendflagsTag(String trendflagsTag) {
        this.trendflagsTag = trendflagsTag == null ? null : trendflagsTag.trim();
    }

    public String getStatusTag() {
        return statusTag;
    }

    public void setStatusTag(String statusTag) {
        this.statusTag = statusTag == null ? null : statusTag.trim();
    }
}