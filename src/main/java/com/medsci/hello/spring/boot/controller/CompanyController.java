package com.medsci.hello.spring.boot.controller;

import com.medsci.hello.spring.boot.common.ResponseBean;
import com.medsci.hello.spring.boot.domain.Company;
import com.medsci.hello.spring.boot.mapper.CompanyMapper;
import com.medsci.hello.spring.boot.service.CompanyService;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @description:
 * @author: Senior
 * @date: 2020/5/13 17:51
 */
@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    @PostMapping("/findByCompanyNameAndId")
    @ResponseBody
    public ResponseBean findByCompanyNameAndId(
            @RequestParam(value = "companyName", required = true, defaultValue = "") String companyName,
            @RequestParam(value = "id", required = true, defaultValue = "0") Integer Id
            ){
        Company byCompanyNameAndId = companyService.findByCompanyNameAndId(companyName, Id);

        return ResponseBean.ok(byCompanyNameAndId);
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseBean add(@RequestBody Company company){
        ResponseBean responseBean = new ResponseBean();

        try {
            int insert = companyMapper.insert(company);

            if (insert == 1){
                responseBean.setReturnCode(200);
                responseBean.setReturnMsg("添加成功");
                return responseBean;
            }

            responseBean.setReturnCode(422);
            responseBean.setReturnMsg("添加失败");
            return responseBean;
        }catch (Exception e){
            responseBean.setReturnCode(500);
            responseBean.setReturnMsg(e.getMessage());
            return responseBean;
        }
    }

    @GetMapping("/limit/{limit}")
    @ResponseBody
    @Transactional
    public ResponseBean scan(@PathVariable("limit") int limit){
        try {
            Cursor<Company> cursor = companyMapper.scan(limit);

            ArrayList<Company> companies = new ArrayList<>();

            cursor.forEach(company -> {
                companies.add(company);
            });

            return ResponseBean.ok(companies);
        }catch (Exception e){
            return ResponseBean.error(e.getMessage());
        }

    }
}
