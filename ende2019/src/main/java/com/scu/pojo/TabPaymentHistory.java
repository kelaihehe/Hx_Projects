package com.scu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TabPaymentHistory implements Serializable {
    private Long id;

    private BigDecimal amount;

    private String description;

    private String payMethod;

    private String payAccount;

    private String payTrader;

    private Long userId;

    private Long conferencUserId;

    private Date createTime;

    private Date responseTime;

    private Byte isSuccess;

    private Byte printPoster;

    private String orderNumber;

    private String serialNumber;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod == null ? null : payMethod.trim();
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount == null ? null : payAccount.trim();
    }

    public String getPayTrader() {
        return payTrader;
    }

    public void setPayTrader(String payTrader) {
        this.payTrader = payTrader == null ? null : payTrader.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getConferencUserId() {
        return conferencUserId;
    }

    public void setConferencUserId(Long conferencUserId) {
        this.conferencUserId = conferencUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public Byte getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Byte isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Byte getPrintPoster() {
        return printPoster;
    }

    public void setPrintPoster(Byte printPoster) {
        this.printPoster = printPoster;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber == null ? null : orderNumber.trim();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }
}