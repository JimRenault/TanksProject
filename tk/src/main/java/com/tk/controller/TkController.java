package com.tk.controller;

import com.tk.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.domain.Tk;
import com.tk.service.ITkService;

/**
 * tankeController
 *
 * @author ysl
 * @date 2023-11-08
 */
@RestController
@RequestMapping("/tk")
public class TkController {
    @Autowired
    private ITkService tkService;

    /**
     * 查询tanke列表
     */
//  @RequiresPermissions("tk:tk:list")
    @GetMapping("/list")
    public AjaxResult list(Tk tk) {
        return AjaxResult.success(tkService.selectTkList(tk));
    }

    /**
     * 获取tanke详细信息
     */
    @GetMapping(value = "/{tkId}")
    public AjaxResult getInfo(@PathVariable("tkId") Long tkId) {
        return AjaxResult.success(tkService.selectTkByTkId(tkId));
    }

    /**
     * 新增tanke
     */
    @PostMapping
    public AjaxResult add(@RequestBody Tk tk) {
        tkService.insertTk(tk);
        return AjaxResult.success(tk);
    }

    /**
     * 修改tanke
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Tk tk) {
        tkService.updateTk(tk);
        return AjaxResult.success(tk);
    }

    /**
     * 删除tanke
     */
    @DeleteMapping("/{tkIds}")
    public AjaxResult remove(@PathVariable Long[] tkIds) {
        tkService.deleteTkByTkIds(tkIds);
        return AjaxResult.success();
    }
}
