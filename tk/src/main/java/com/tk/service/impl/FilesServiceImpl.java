package com.tk.service.impl;

import java.util.List;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tk.mapper.FilesMapper;
import com.tk.domain.Files;
import com.tk.service.IFilesService;

/**
 * 对局Service业务层处理
 *
 * @author ysl
 * @date 2023-11-09
 */
@Service
public class FilesServiceImpl implements IFilesService {
    @Autowired
    private FilesMapper filesMapper;

    /**
     * 查询对局
     *
     * @param filesId 对局主键
     * @return 对局
     */
    @Override
    public Files selectFilesByFilesId(Long filesId) {
        return filesMapper.selectFilesByFilesId(filesId);
    }

    /**
     * 查询对局列表
     *
     * @param files 对局
     * @return 对局
     */
    @Override
    public List<Files> selectFilesList(Files files) {
        List<Files> list = filesMapper.selectFilesList(files);
        return list;
    }

    /**
     * 新增对局
     *
     * @param files 对局
     * @return 结果
     */
    @Override
    public int insertFiles(Files files) {

        files.setUpdateTine(new Date());

        return filesMapper.insertFiles(files);
    }

    /**
     * 修改对局
     *
     * @param files 对局
     * @return 结果
     */
    @Override
    public int updateFiles(Files files) {

        files.setUpdateTine(new Date());
        return filesMapper.updateFiles(files);
    }

    /**
     * 批量删除对局
     *
     * @param filesIds 需要删除的对局主键
     * @return 结果
     */
    @Override
    public int deleteFilesByFilesIds(Long[] filesIds) {
        return filesMapper.deleteFilesByFilesIds(filesIds);
    }
}
