package com.crab.service.impl;

import com.crab.entity.Type;
import com.crab.mapper.TypeMapper;
import com.crab.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.crab.utils.Result;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author crab
 * @since 2023-09-02
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public Result findAllTypes() {
        List<Type> types = typeMapper.selectList(null);
        return Result.ok(types);
    }
}
