package com.pojo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单实体类
 * 记录用户的收支记录
 *
 * @author Auto Generated
 */
public class Bill {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID（关联sys_user表）
     */
    private Integer userId;

    /**
     * 交易日期
     */
    private LocalDateTime transactionDate;

    /**
     * 金额（正数为收入，负数为支出）
     */
    private BigDecimal amount;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 交易类型（如：银联快捷支付、网联协议支付等）
     */
    private String transactionType;

    /**
     * 交易备注/对方账户
     */
    private String remark;

    /**
     * 来源类型（CMB-招商银行, WECHAT-微信, ALIPAY-支付宝, MANUAL-手动记账）
     */
    private String source;

    /**
     * 分类（餐饮、交通、购物等，系统自动匹配或用户手动选择）
     */
    private String category;

    /**
     * 账单类型（INCOME-收入, EXPENSE-支出）
     */
    private String billType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", userId=" + userId +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", balance=" + balance +
                ", transactionType='" + transactionType + '\'' +
                ", remark='" + remark + '\'' +
                ", source='" + source + '\'' +
                ", category='" + category + '\'' +
                ", billType='" + billType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
