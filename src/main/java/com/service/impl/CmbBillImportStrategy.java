package com.service.impl;

import com.pojo.Bill;
import com.service.BillImportStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 招商银行CSV导入策略
 * 支持招商银行交易记录CSV格式解析
 */
@Component
public class CmbBillImportStrategy implements BillImportStrategy {

    private static final String SOURCE_TYPE = "CMB";
    private static final String DISPLAY_NAME = "招商银行";

    @Override
    public String getSourceType() {
        return SOURCE_TYPE;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Override
    public List<Bill> parseCsv(MultipartFile file, Integer userId) {
        List<Bill> bills = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // 跳过前8行的标题信息
                if (lineNumber <= 8) {
                    continue;
                }
                
                // 跳过空行
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                Bill bill = parseLine(line, userId);
                if (bill != null) {
                    bills.add(bill);
                }
            }
            
        } catch (Exception e) {
            throw new RuntimeException("解析招商银行CSV文件失败: " + e.getMessage(), e);
        }
        
        return bills;
    }

    /**
     * 解析单行数据
     * 招商银行CSV格式：交易日期,交易时间,收入,支出,余额,交易类型,交易备注
     */
    private Bill parseLine(String line, Integer userId) {
        try {
            // 使用逗号分割，但需要处理交易备注中可能包含逗号的情况
            String[] parts = splitCsvLine(line);
            
            // 确保有足够的列
            if (parts.length < 7) {
                return null;
            }
            
            Bill bill = new Bill();
            bill.setUserId(userId);
            bill.setSource(SOURCE_TYPE);
            
            // 解析交易日期和时间
            String dateStr = parts[0].trim();
            String timeStr = parts[1].trim();
            if (!dateStr.isEmpty() && !timeStr.isEmpty()) {
                String datetimeStr = dateStr + " " + timeStr;
                LocalDateTime transactionDate = LocalDateTime.parse(
                        datetimeStr, DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss"));
                bill.setTransactionDate(transactionDate);
            }
            
            // 解析收入和支出
            String incomeStr = parts[2].trim();
            String expenseStr = parts[3].trim();
            
            BigDecimal amount;
            String billType;
            
            if (!incomeStr.isEmpty() && !incomeStr.equals("0")) {
                amount = new BigDecimal(incomeStr);
                billType = "INCOME";
            } else if (!expenseStr.isEmpty() && !expenseStr.equals("0")) {
                amount = new BigDecimal(expenseStr).negate();
                billType = "EXPENSE";
            } else {
                return null;
            }
            
            bill.setAmount(amount);
            bill.setBillType(billType);
            
            // 解析余额
            String balanceStr = parts[4].trim();
            if (!balanceStr.isEmpty()) {
                bill.setBalance(new BigDecimal(balanceStr));
            }
            
            // 解析交易类型
            bill.setTransactionType(parts[5].trim());
            
            // 解析交易备注
            bill.setRemark(parts[6].trim());
            
            // 自动分类
            bill.setCategory(autoCategory(bill.getRemark(), bill.getTransactionType(), billType));
            
            return bill;
            
        } catch (Exception e) {
            // 解析失败的行跳过
            return null;
        }
    }

    /**
     * 智能分割CSV行（处理引号内的逗号）
     */
    private String[] splitCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                parts.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        parts.add(current.toString());
        
        return parts.toArray(new String[0]);
    }

    /**
     * 根据备注和交易类型自动分类
     */
    private String autoCategory(String remark, String transactionType, String billType) {
        if (remark == null) {
            remark = "";
        }
        if (transactionType == null) {
            transactionType = "";
        }
        
        String lowerRemark = remark.toLowerCase();
        String lowerType = transactionType.toLowerCase();
        
        // 收入分类
        if ("INCOME".equals(billType)) {
            if (lowerRemark.contains("工资") || lowerRemark.contains("salary")) {
                return "工资";
            } else if (lowerRemark.contains("转账") && lowerRemark.contains("转入")) {
                return "转账收入";
            } else if (lowerRemark.contains("退款")) {
                return "退款";
            } else if (lowerRemark.contains("红包")) {
                return "红包";
            }
            return "其他收入";
        }
        
        // 支出分类
        if (lowerRemark.contains("餐饮") || lowerRemark.contains("外卖") || 
            lowerRemark.contains("美团") || lowerRemark.contains("饿了么")) {
            return "餐饮";
        } else if (lowerRemark.contains("滴滴") || lowerRemark.contains("打车") || 
                   lowerRemark.contains("地铁") || lowerRemark.contains("公交")) {
            return "交通";
        } else if (lowerRemark.contains("超市") || lowerRemark.contains("购物") ||
                   lowerRemark.contains("淘宝") || lowerRemark.contains("京东")) {
            return "购物";
        } else if (lowerRemark.contains("微信") || lowerRemark.contains("话费")) {
            return "通讯";
        } else if (lowerRemark.contains("电影") || lowerRemark.contains("娱乐")) {
            return "娱乐";
        } else if (lowerRemark.contains("医疗") || lowerRemark.contains("医院")) {
            return "医疗";
        } else if (lowerRemark.contains("教育") || lowerRemark.contains("培训")) {
            return "教育";
        } else if (lowerRemark.contains("转账") && lowerRemark.contains("转出")) {
            return "转账支出";
        } else if (lowerType.contains("还款")) {
            return "还款";
        }
        
        return "其他支出";
    }
}
