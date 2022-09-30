/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gc.vp.web;

import com.gc.vp.entity.vo.TransDto;
import com.gc.vp.entity.vo.code.ComTreeReq;
import com.gc.vp.service.GenCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/code")
@RestController
public class GenCodeController {
    @Autowired
    private GenCodeService genCodeService;

    @PostMapping("/gen")
    public TransDto<String> genCode(@RequestBody List<ComTreeReq> coms) {
        return TransDto.success(genCodeService.genCode(coms));
    }
}
