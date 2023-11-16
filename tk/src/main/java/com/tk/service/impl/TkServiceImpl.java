package com.tk.service.impl;

import java.util.List;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tk.mapper.TkMapper;
import com.tk.domain.Tk;
import com.tk.service.ITkService;

/**
 * tankeService业务层处理
 *
 * @author ysl
 * @date 2023-11-08
 */
@Service
public class TkServiceImpl implements ITkService {
    @Autowired
    private TkMapper tkMapper;

    /**
     * 查询tanke
     *
     * @param tkId tanke主键
     * @return tanke
     */
    @Override
    public Tk selectTkByTkId(Long tkId) {
        return tkMapper.selectTkByTkId(tkId);
    }

    /**
     * 查询tanke列表
     *
     * @param tk tanke
     * @return tanke
     */
    @Override
    public List<Tk> selectTkList(Tk tk) {
        List<Tk> list = tkMapper.selectTkList(tk);
        return list;
    }

    /**
     * 新增tanke
     *
     * @param tk tanke
     * @return 结果
     */
    @Override
    public int insertTk(Tk tk) {

        tk.setUpdateTime(new Date());

        return tkMapper.insertTk(tk);
    }

    /**
     * 修改tanke
     *
     * @param tk tanke
     * @return 结果
     */
    @Override
    public int updateTk(Tk tk) {


        tk.setUpdateTime(new Date());


        return tkMapper.updateTk(tk);
    }

    /**
     * 批量删除tanke
     *
     * @param tkIds 需要删除的tanke主键
     * @return 结果
     */
    @Override
    public int deleteTkByTkIds(Long[] tkIds) {
        return tkMapper.deleteTkByTkIds(tkIds);
    }
}
