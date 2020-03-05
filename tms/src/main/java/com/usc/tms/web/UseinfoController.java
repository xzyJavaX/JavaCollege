package com.usc.tms.web;
import com.sun.org.apache.regexp.internal.RE;
import com.usc.tms.pojo.Fixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.usc.tms.pojo.Useinfo;
import com.usc.tms.pojo.User;
import com.usc.tms.service.UseinfoService;
import com.usc.tms.util.Result;
import com.usc.tms.util.Page4Navigator;
import org.springframework.web.util.HtmlUtils;
import javax.servlet.http.HttpSession;
import com.usc.tms.service.FixtureService;

import java.util.Date;
import java.util.List;


@RestController
public class UseinfoController {
    @Autowired UseinfoService useinfoService;
    @Autowired FixtureService fixtureService;

    //    分页查询数据
    @GetMapping("/useinfos")
    public Page4Navigator<Useinfo> list(@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Useinfo> page = useinfoService.list(start,size,5);
        return page;
    }

    //    增加数据
    @PostMapping("/useinfos")
    public Object add(@RequestBody Useinfo bean, HttpSession session) throws Exception{
//        判断工夹具是否存在
        Fixture x1=fixtureService.getByid(bean.getFid());
        if(null==x1){
            String message ="不存在";
            return Result.fail(message);
        }
//        判断是否已借出
        List<Useinfo> useinfos=useinfoService.getAllbyFid(bean.getFid());
        for (Useinfo useinfo:useinfos){
            if (useinfo.getIndate()==null)
            {
                String message ="已借出";
                return Result.fail(message);
            }
        }

//        增加相应的登记人
        User user = (User)session.getAttribute("user");
        String x =user.getName();
        bean.setWritename(x);
//        增加相应的存放位置
        bean.setLocation(fixtureService.get(bean.getFid()).getLocation());
//        设置出库时间
        bean.setOutdate(new Date());
//        工具夹使用次数+1
        Fixture fixture=fixtureService.get(bean.getFid());
        int num = Integer.parseInt(fixture.getUsedCount());
        num=num+1;
        fixture.setUsedCount(String.valueOf(num));
        fixtureService.update(fixture);
        useinfoService.add(bean);
        return Result.success();
    }




    //删除数据
    @DeleteMapping("/useinfos/{id}")
    public void delete(@PathVariable("id") int id)throws Exception {
        useinfoService.delete(id);
    }
    //根据id获取数据
    @GetMapping("/useinfos/{id}")
    public Useinfo get(@PathVariable("id") int id) throws Exception {
        Useinfo bean=useinfoService.get(id);
        return bean;
    }

    //修改数据
    @PutMapping("/useinfos")
    public Object update(@RequestBody Useinfo bean) throws Exception {
        if(bean.getIndate()==null) {
            bean.setIndate(new Date());
            useinfoService.update(bean);
        }
        else
        {
            useinfoService.update(bean);
        }
        return bean;
    }

    @GetMapping("/searchs/{keyword}")
    public Object search(@PathVariable("keyword") String id) throws Exception{
        List<Useinfo> useinfos=useinfoService.getAllbyFid(id);
        if (useinfos.isEmpty()){
            return Result.fail("不存在");
        }
        else
        {
            return useinfos;
        }
    }




}