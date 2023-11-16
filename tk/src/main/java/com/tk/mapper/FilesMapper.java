package com.tk.mapper;

import java.util.List;

import com.tk.domain.Files;
import org.apache.ibatis.annotations.Mapper;

/**
 * 对局Mapper接口
 *
 * @author ysl
 * @date 2023-11-09
 */

@Mapper
public interface FilesMapper {
    /**
     * 查询对局
     *
     * @param filesId 对局主键
     * @return 对局
     */
    Files selectFilesByFilesId(Long filesId);

    /**
     * 查询对局列表
     *
     * @param files 对局
     * @return 对局集合
     */
    List<Files> selectFilesList(Files files);

    /**
     * 新增对局
     *
     * @param files 对局
     * @return 结果
     */
    int insertFiles(Files files);

    /**
     * 修改对局
     *
     * @param files 对局
     * @return 结果
     */
    int updateFiles(Files files);

    /**
     * 删除对局
     *
     * @param filesId 对局主键
     * @return 结果
     */
    int deleteFilesByFilesId(Long filesId);

    /**
     * 批量删除对局
     *
     * @param filesIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteFilesByFilesIds(Long[] filesIds);

}
