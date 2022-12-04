package com.gc.vp.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gc.vp.entity.po.CodeCategoryPo;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.entity.vo.code.ListCodeCategoryReq;
import com.gc.vp.service.ICodeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 代码分类
 */
@RestController
@RequestMapping("/api/code/category")
public class CodeCategoryController {
    @Autowired
    private ICodeCategoryService codeCategoryService;

    /**
     * 查询代码分类列表
     *
     * @return
     */
    @PostMapping("/list")
    public TransDto<List<CodeCategoryPo>> list(@RequestBody ListCodeCategoryReq req) {
        LambdaQueryWrapper<CodeCategoryPo> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(req.getCategoryId())) {
            queryWrapper.eq(CodeCategoryPo::getParentId, req.getCategoryId());
        }
        return TransDto.success(codeCategoryService.list(queryWrapper));
    }

    /**
     * 新增代码分类配置
     *
     * @return
     */
    @PostMapping("/add")
    public TransDto<Boolean> add(
            @RequestHeader("loginUser") String loginUser,
            @RequestBody CodeCategoryPo req
    ) {
        if (Objects.nonNull(req.getId())){
            req.setUpdateUser(loginUser);
        }else {
            req.setCreateUser(loginUser);
        }
        return TransDto.success(codeCategoryService.saveOrUpdate(req));
    }

    /**
     * 删除分类
     * @param id 分类id
     * @return
     */
    @GetMapping("/del")
    public TransDto<Boolean> del(
            @RequestParam("id") Integer id
    ) {
        return TransDto.success(codeCategoryService.removeById(id));
    }
}
