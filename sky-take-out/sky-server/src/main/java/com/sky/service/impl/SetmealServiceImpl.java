package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void saveWithDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //向数据库套餐表插入套餐数据
        setmealMapper.insert(setmeal);

        //给套餐菜品关联表添加关联数据
        Long id = setmeal.getId();
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        dishes.forEach(dish -> {
            dish.setSetmealId(id);
        });
        setmealDishMapper.insertBatch(dishes);
    }

    /**
     * 套餐分页查询
     *
     * @param queryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(SetmealPageQueryDTO queryDTO) {
        PageHelper.startPage(queryDTO.getPage(), queryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.queryPage(queryDTO);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除套餐
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        //判断是否处于出售状态：是-不能删，否-能删
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal != null && setmeal.getStatus() == StatusConstant.ENABLE) {
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }

        //执行删除
        setmealMapper.deleteBatch(ids);
        setmealDishMapper.deleteBatchBySetmealId(ids);
    }
}
