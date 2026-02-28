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
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
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
    @Autowired
    private DishMapper dishMapper;

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

    /**
     * 根据ID查询套餐信息包含套餐的菜品
     *
     * @param id
     * @return
     */
    @Override
    public SetmealVO getByIdWithDishes(Long id) {
        //获取套餐信息
        Setmeal setmeal = setmealMapper.getById(id);
        //获取套餐关联的菜品
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);

        //封装数据返回
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 修改套餐信息及其套餐中的菜品信息
     *
     * @param setmealDTO
     */
    @Override
    @Transactional
    public void updateWithDishes(SetmealDTO setmealDTO) {
        //先修改套餐表中的数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        //再修改与套餐关联菜品表的数据
        //1.清空
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());
        //2.重新添加
        List<SetmealDish> dishes = setmealDTO.getSetmealDishes();
        if (dishes != null && dishes.size() > 0) {
            dishes.forEach(dish -> {
                dish.setSetmealId(setmealDTO.getId());
            });
        }
        setmealDishMapper.insertBatch(dishes);
    }

    /**
     * 套餐启售状态
     *
     * @param status
     * @param id
     */
    @Override
    public void enable(Integer status, Long id) {
        //启售套餐时，若套餐的菜品有停售的，则不能启售当前套餐
        if (status == StatusConstant.ENABLE) {
            List<Dish> dishes = dishMapper.getBySetmealId(id);
            if (dishes != null && dishes.size() > 0) {
                dishes.forEach(dish -> {
                    if (dish.getStatus() == StatusConstant.DISABLE) {
                        System.out.println(dish);
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }

        Setmeal dish = Setmeal.builder().status(status).id(id).build();
        setmealMapper.update(dish);
    }

    /**
     * 条件查询
     *
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     *
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}
