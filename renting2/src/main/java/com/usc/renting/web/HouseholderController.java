package com.usc.renting.web;

import com.usc.renting.pojo.Householder;
import com.usc.renting.service.HouseholderService;
import com.usc.renting.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class HouseholderController {
    @Autowired HouseholderService householderService;

//    1、start的意思是第0页
    @GetMapping("/householders")
    public Page4Navigator<Householder> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Householder> page =householderService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }
//2
    @PostMapping("/householders")
    public Object add(@RequestBody Householder bean) throws Exception {
        householderService.add(bean);
        return bean;
    }
//3
    @DeleteMapping("/householders/{id}")
    public String delete(@PathVariable("id") int id)  throws Exception {
        householderService.delete(id);
        return null;
    }
//4
    @GetMapping("/householders/{id}")
    public Householder get(@PathVariable("id") int id) throws Exception {
        Householder bean=householderService.get(id);
        return bean;
    }
//5
    @PutMapping("/householders")
    public Object update(@RequestBody Householder bean) throws Exception {
        householderService.update(bean);
        return bean;
    }

    //6.搜索
    @PostMapping("householdersearch")
    public Object search( String name){
        return householderService.search(name);
    }
}
