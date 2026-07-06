package com.controller;

import com.pojo.Bill;
import com.pojo.SysUser;
import com.service.BillImportStrategy;
import com.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 账单管理控制器
 */
@Controller
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    /**
     * 进入账单导入页面
     */
    @GetMapping("/import")
    public String importPage(Model model) {
        List<BillImportStrategy> sources = billService.getAvailableSources();
        model.addAttribute("sources", sources);
        return "bill-import";
    }

    /**
     * 处理CSV文件上传
     */
    @PostMapping("/import")
    public String doImport(@RequestParam("file") MultipartFile file,
                          @RequestParam("sourceType") String sourceType,
                          Model model) {
        try {
            // 获取当前登录用户
            SysUser user = (SysUser) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            
            int count = billService.importCsv(file, sourceType, user.getId());
            
            model.addAttribute("success", true);
            model.addAttribute("message", "导入成功！共导入 " + count + " 条记录");
            
        } catch (Exception e) {
            model.addAttribute("success", false);
            model.addAttribute("message", "导入失败: " + e.getMessage());
        }
        
        List<BillImportStrategy> sources = billService.getAvailableSources();
        model.addAttribute("sources", sources);
        return "bill-import";
    }

    /**
     * 查看账单列表
     */
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "source", required = false) String source) {
        SysUser user = (SysUser) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        
        List<Bill> bills;
        if (source != null && !source.isEmpty()) {
            bills = billService.findByUserIdAndSource(user.getId(), source);
            model.addAttribute("selectedSource", source);
        } else {
            bills = billService.findByUserId(user.getId());
        }
        
        model.addAttribute("bills", bills);
        model.addAttribute("sources", billService.getAvailableSources());
        
        // 统计数据
        double totalIncome = bills.stream()
                .filter(b -> "INCOME".equals(b.getBillType()))
                .mapToDouble(b -> b.getAmount().doubleValue())
                .sum();
        
        double totalExpense = bills.stream()
                .filter(b -> "EXPENSE".equals(b.getBillType()))
                .mapToDouble(b -> Math.abs(b.getAmount().doubleValue()))
                .sum();
        
        model.addAttribute("totalIncome", String.format("%.2f", totalIncome));
        model.addAttribute("totalExpense", String.format("%.2f", totalExpense));
        model.addAttribute("balance", String.format("%.2f", totalIncome - totalExpense));
        
        return "bill-list";
    }

    /**
     * 删除账单
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        billService.deleteById(id);
        return "redirect:/bill/list";
    }

    /**
     * 进入编辑页面
     */
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        Bill bill = billService.findById(id);
        model.addAttribute("bill", bill);
        return "bill-edit";
    }

    /**
     * 保存编辑
     */
    @PostMapping("/edit")
    public String doEdit(Bill bill) {
        billService.update(bill);
        return "redirect:/bill/list";
    }
}
