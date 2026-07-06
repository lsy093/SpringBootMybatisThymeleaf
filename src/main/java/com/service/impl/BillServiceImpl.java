package com.service.impl;

import com.mapper.BillMapper;
import com.pojo.Bill;
import com.service.BillImportStrategy;
import com.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 账单服务实现类
 */
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired(required = false)
    private List<BillImportStrategy> strategies;

    /**
     * 策略缓存映射
     */
    private Map<String, BillImportStrategy> strategyMap;

    /**
     * 初始化策略映射
     */
    private void initStrategyMap() {
        if (strategyMap == null && strategies != null) {
            strategyMap = new HashMap<>();
            for (BillImportStrategy strategy : strategies) {
                strategyMap.put(strategy.getSourceType(), strategy);
            }
        }
    }

    @Override
    public int importCsv(MultipartFile file, String sourceType, Integer userId) {
        initStrategyMap();
        
        BillImportStrategy strategy = strategyMap.get(sourceType);
        if (strategy == null) {
            throw new IllegalArgumentException("不支持的导入来源类型: " + sourceType);
        }
        
        List<Bill> bills = strategy.parseCsv(file, userId);
        
        if (bills.isEmpty()) {
            return 0;
        }
        
        // 批量插入
        int count = billMapper.batchSave(bills);
        return count;
    }

    @Override
    public List<Bill> findByUserId(Integer userId) {
        return billMapper.findByUserId(userId);
    }

    @Override
    public List<Bill> findByUserIdAndSource(Integer userId, String source) {
        return billMapper.findByUserIdAndSource(userId, source);
    }

    @Override
    public Bill findById(Integer id) {
        return billMapper.findById(id);
    }

    @Override
    public int save(Bill bill) {
        return billMapper.save(bill);
    }

    @Override
    public int update(Bill bill) {
        return billMapper.update(bill);
    }

    @Override
    public int deleteById(Integer id) {
        return billMapper.deleteById(id);
    }

    @Override
    public List<BillImportStrategy> getAvailableSources() {
        initStrategyMap();
        if (strategies == null) {
            return java.util.Collections.emptyList();
        }
        return strategies.stream()
                .sorted((a, b) -> a.getSourceType().compareTo(b.getSourceType()))
                .collect(Collectors.toList());
    }
}
