package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 新增一组口味数据
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品dishId删除口味数据
     *
     * @param dishId
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id的集合批量删除口味数据
     *
     * @param dishIds
     */
    void deleteBatchDishId(List<Long> dishIds);

    /**
     * 获取菜品id对应的所有口味
     *
     * @param dishId
     * @return
     */
    @Select("select  * from sky_take_out.dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
