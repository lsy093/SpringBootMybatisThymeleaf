package com.service;

import com.pojo.Bill;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 账单导入策略接口
 * 定义不同来源账单的解析方法
 */
public interface BillImportStrategy {

    /**
     * 获取来源类型标识
     *
     * @return 来源类型（如：CMB, WECHAT, ALIPAY）
     */
    String getSourceType();

    /**
     * 获取显示名称
     *
     * @return 显示名称（如：招商银行, 微信支付, 支付宝）
     */
    String getDisplayName();

    /**
     * 解析CSV文件
     *
     * @param file     上传的CSV文件
     * @param userId   用户ID
     * @return 解析后的账单列表
     */
    List<Bill> parseCsv(MultipartFile file, Integer userId);
}
