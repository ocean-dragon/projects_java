package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    List<Long> getSetmealIdsByDishId(List<Long> dishIds);

    /**
     * 批量保存套餐与菜品的关联信息
     *
     * @param dishes
     */
    void insertBatch(List<SetmealDish> dishes);

    /**
     * 根据套餐ID批量删除关联信息
     *
     * @param SetmealIds
     */
    void deleteBatchBySetmealId(List<Long> SetmealIds);

    /**
     * 根据套餐id删除套餐和菜品的关联关系
     *
     * @param setmealId
     */
    @Delete("delete from sky_take_out.setmeal_dish where setmeal_id = #{setmealId}")
    void deleteBySetmealId(Long setmealId);

    /**
     * 根据套餐Id查询套餐的菜品信息
     *
     * @param setmealId
     * @return
     */
    @Select("select * from sky_take_out.setmeal_dish where setmeal_id = #{setmealId}")
    List<SetmealDish> getBySetmealId(Long setmealId);
}
