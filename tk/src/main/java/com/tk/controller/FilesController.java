package com.tk.controller;

import java.util.List;
import java.io.IOException;

import com.tk.AjaxResult;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.domain.Files;
import com.tk.service.IFilesService;


/**
 * 对局Controller
 *
 * @author ysl
 * @date 2023-11-08
 */
@RestController
@RequestMapping("/files")
public class FilesController {
    @Autowired
    private IFilesService filesService;

    /**
     * 查询对局列表
     */
//  @RequiresPermissions("tk:files:list")
    @GetMapping("/list")
    public AjaxResult list(Files files) {
        return AjaxResult.success(filesService.selectFilesList(files));
    }

    /**
     * 获取对局详细信息
     */
    @GetMapping(value = "/{filesId}")
    public AjaxResult getInfo(@PathVariable("filesId") Long filesId) {
        return AjaxResult.success(filesService.selectFilesByFilesId(filesId));
    }

    /**
     * 新增对局
     */
    @PostMapping
    public AjaxResult add(@RequestBody Files files) {
        filesService.insertFiles(files);
        return AjaxResult.success(files);
    }

    /**
     * 修改对局
     */
    @PutMapping
    public AjaxResult edit(@RequestBody Files files) {
        filesService.updateFiles(files);
        return AjaxResult.success(files);
    }

    /**
     * 删除对局
     */
    @DeleteMapping("/{filesIds}")
    public AjaxResult remove(@PathVariable Long[] filesIds) {
        filesService.deleteFilesByFilesIds(filesIds);
        return AjaxResult.success();
    }
}
