package com.usc.tms.web;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.usc.tms.pojo.Fixture;
import com.usc.tms.service.FixtureService;
import com.usc.tms.util.Result;
import com.usc.tms.util.Page4Navigator;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class FixtureController {
    @Autowired FixtureService fixtureService;

    //    分页查询数据
    @GetMapping("/fixtures")
    public Page4Navigator<Fixture> list(@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Fixture> page = fixtureService.list(start,size,5);
        return page;
    }

    //    增加数据
    @PostMapping("/fixtures")
    public Object add(@RequestBody Fixture bean) throws Exception{
        Fixture fixture = fixtureService.getByid(bean.getId());
        if (fixture==null)
        {
            bean.setRegdate(new Date());
            bean.setUsedCount("0");
            fixtureService.add(bean);
            return Result.success();
        }
        else {
            return Result.fail("已存在");
        }


    }
    //删除数据
    @DeleteMapping("/fixtures/{id}")
    public void delete(@PathVariable("id") String id)throws Exception {
        fixtureService.delete(id);
    }
    //根据id数据
    @GetMapping("/fixtures/{id}")
    public Fixture get(@PathVariable("id") String id) throws Exception {
        Fixture bean=fixtureService.get(id);
        return bean;
    }

    //修改数据
    @PutMapping("/fixtures")
    public Object update(@RequestBody Fixture bean) throws Exception {
        fixtureService.update(bean);
        return bean;
    }


    @GetMapping("/select/{num}")
    public Object getAll(@PathVariable("num") int num){
        List<Fixture> fixtures=fixtureService.getAll();
        List<Fixture> fixtures1= new ArrayList<>();
        for (Fixture fixture:fixtures){
            if (Integer.parseInt(fixture.getUsedCount())>=num)
                fixtures1.add(fixture);
        }
        return fixtures1;
    }



}