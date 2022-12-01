package com.gc.vp.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gc.vp.entity.po.CodeSegmentPo;
import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.entity.vo.code.ListCodeSegReq;
import com.gc.vp.service.ICodeSegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 代码分类
 */
@RestController
@RequestMapping("/api/code/segment")
public class CodeSegmentController {
    @Autowired
    private ICodeSegmentService codeSegmentService;

    /**
     * 查询代码片段列表
     *
     * @return
     */
    @PostMapping("/list")
    public TransDto<List<CodeSegmentPo>> list(@RequestBody ListCodeSegReq req) {
        LambdaQueryWrapper<CodeSegmentPo> queryWrapper = new LambdaQueryWrapper<>();
        if (Objects.nonNull(req.getCategoryId())) {
            queryWrapper.eq(CodeSegmentPo::getCategoryId, req.getCategoryId());
        }
        return TransDto.success(codeSegmentService.list(queryWrapper));
    }

    /**
     * 新增代码片段配置
     *
     * @return
     */
    @PostMapping("/add")
    public TransDto<Boolean> add(
            @RequestHeader("loginUser") String loginUser,
            @RequestBody CodeSegmentPo req
    ) {
        req.setCreateUser(loginUser);
        req.setUpdateUser(loginUser);
        return TransDto.success(codeSegmentService.save(req));
    }
}
