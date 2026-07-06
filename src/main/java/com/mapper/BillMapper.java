package com.mapper;

import com.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账单数据访问接口
 */
public interface BillMapper {

    /**
     * 保存账单记录
     *
     * @param bill 账单对象
     * @return 影响行数
     */
    int save(Bill bill);

    /**
     * 批量保存账单记录
     *
     * @param bills 账单列表
     * @return 影响行数
     */
    int batchSave(@Param("bills") List<Bill> bills);

    /**
     * 根据用户ID查询账单列表
     *
     * @param userId 用户ID
     * @return 账单列表
     */
    List<Bill> findByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID和来源查询账单
     *
     * @param userId 用户ID
     * @param source 来源类型
     * @return 账单列表
     */
    List<Bill> findByUserIdAndSource(@Param("userId") Integer userId, @Param("source") String source);

    /**
     * 根据ID查询账单
     *
     * @param id 账单ID
     * @return 账单对象
     */
    Bill findById(@Param("id") Integer id);

    /**
     * 更新账单记录
     *
     * @param bill 账单对象
     * @return 影响行数
     */
    int update(Bill bill);

    /**
     * 删除账单记录
     *
     * @param id 账单ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 统计用户账单数量
     *
     * @param userId 用户ID
     * @return 账单数量
     */
    int countByUserId(@Param("userId") Integer userId);
}
