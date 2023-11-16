package com.tk.mapper;

import java.util.List;

import com.tk.domain.Tk;
import org.apache.ibatis.annotations.Mapper;

/**
 * tankeMapper接口
 *
 * @author ysl
 * @date 2023-11-08
 */
@Mapper

public interface TkMapper {
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
     * 删除tanke
     *
     * @param tkId tanke主键
     * @return 结果
     */
    int deleteTkByTkId(Long tkId);

    /**
     * 批量删除tanke
     *
     * @param tkIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteTkByTkIds(Long[] tkIds);

}
