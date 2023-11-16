package com.tk.service;

import java.util.List;

import com.tk.domain.Tk;

/**
 * tankeService接口
 *
 * @author ysl
 * @date 2023-11-08
 */
public interface ITkService {
    /**
     * 查询tanke
     *
     * @param tkId tanke主键
     * @return tanke
     */
    Tk selectTkByTkId(Long tkId);

    /**
     * 查询tanke列表
     *
     * @param tk tanke
     * @return tanke集合
     */
    List<Tk> selectTkList(Tk tk);

    /**
     * 新增tanke
     *
     * @param tk tanke
     * @return 结果
     */
    int insertTk(Tk tk);

    /**
     * 修改tanke
     *
     * @param tk tanke
     * @return 结果
     */
    int updateTk(Tk tk);

    /**
     * 批量删除tanke
     *
     * @param tkIds 需要删除的tanke主键集合
     * @return 结果
     */
    int deleteTkByTkIds(Long[] tkIds);

}
