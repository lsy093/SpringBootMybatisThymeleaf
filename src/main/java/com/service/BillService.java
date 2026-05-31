package com.service;

import com.pojo.Bill;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 账单服务接口
 */
public interface BillService {

    /**
     * 导入CSV账单
     *
     * @param file     CSV文件
     * @param sourceType 来源类型（CMB/WECHAT/ALIPAY）
     * @param userId   用户ID
     * @return 导入的账单数量
     */
    int importCsv(MultipartFile file, String sourceType, Integer userId);

    /**
     * 查询用户账单列表
     *
     * @param userId 用户ID
     * @return 账单列表
     */
    List<Bill> findByUserId(Integer userId);

    /**
     * 根据来源查询账单
     *
     * @param userId 用户ID
     * @param source 来源类型
     * @return 账单列表
     */
    List<Bill> findByUserIdAndSource(Integer userId, String source);

    /**
     * 根据ID查询账单
     *
     * @param id 账单ID
     * @return 账单对象
     */
    Bill findById(Integer id);

    /**
     * 保存账单
     *
     * @param bill 账单对象
     * @return 影响行数
     */
    int save(Bill bill);

    /**
     * 更新账单
     *
     * @param bill 账单对象
     * @return 影响行数
     */
    int update(Bill bill);

    /**
     * 删除账单
     *
     * @param id 账单ID
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 获取所有可用的导入来源类型
     *
     * @return 来源类型列表
     */
    List<BillImportStrategy> getAvailableSources();
}
