package com.usc.renting.web;

import com.sun.org.apache.regexp.internal.RE;
import com.usc.renting.pojo.*;
import com.usc.renting.pojo.Collection;
import com.usc.renting.service.*;
import com.usc.renting.util.Page4Navigator;
import com.usc.renting.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import com.usc.renting.pojo.Notification;


import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;
import java.util.*;

@RestController
public class ForeRESTController {
    @Autowired
    UserService userService;
    @Autowired
    HouseService houseService;
    @Autowired
    HouseImageService houseImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    HouseholderService householderService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentFirstService commentFirstService;
    @Autowired
    CommentSecondService commentSecondService;
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    CollectionService collectionService;
    @Autowired
    ScanService scanService;
    @Autowired
    NotificationService notificationService;

//    多线程
    private Object object = new Object();

//    //1.修改密码
//    @PutMapping("update")
//    public Object update(@RequestBody User bean) throws Exception {
//        String name= bean.getName();
//        String password= bean.getPassword();
////        通过HtmlUtils.htmlEscape(name);把账号里的特殊符号进行转义
//        name = HtmlUtils.htmlEscape(name);
//        bean.setName(name);
//        boolean exist=userService.isExist(name);
//        if(!exist){
//            String message="此用户不存在";
//            return Result.fail(message);
//        }
//        else {
//            bean = userService.getByName(name);
//        }
////        重新设置新的密码
//        bean.setPassword(password);
//        userService.update(bean);
//        return Result.success();
//    }

    //1.普通用户注册    OK
    @PostMapping("/foreregister")
    public Object register(@RequestBody User user) {
        //        判断该手机号是否已经注册
        boolean exist = userService.isExist(user.getTel());
        if(exist){
            String message ="该电话已经被注册";
            return Result.fail(message);
        }
        //    防止恶意输入
        user.setUsername(HtmlUtils.htmlEscape( user.getUsername()));
        user.setPosition("普通用户");
        userService.add(user);
        return Result.success();
    }

    //2.户主信息注册  OK
    @PostMapping("/hregister")
    public Object householderregister(@RequestBody User user) {
        //        判断该手机号是否已经注册
        boolean exist = userService.isExist(user.getTel());
        if(exist){
            String message ="该电话已经被注册";
            return Result.fail(message);
        }
//        防止恶意注册
        user.setUsername(HtmlUtils.htmlEscape(user.getUsername()));
        user.setPosition("户主");
        user.setUsername(user.getName());
//        new户主信息
        Householder householder = new Householder();
        householder.setName(user.getName());
        householder.setTel(user.getTel());
        householder.setVx(user.getVx());
        householder.setQq(user.getQq());
        householderService.add(householder);
        userService.add(user);
        return Result.success();
    }

    //3.登录
    @PostMapping("/forelogin")
    public Object login(@RequestBody User user1, HttpSession session) {
        User user =userService.get(user1.getTel(),user1.getPassword());
        if(null==user){
            String message ="账号密码错误";
            return Result.fail(message);
        }
        else{
//            登录成功，设置session
            session.setAttribute("user", user);
            session.setAttribute("position",user.getPosition());
            return Result.success(user.getPosition());
        }
    }

    //4.检查前台是否登录
    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return Result.success();
        return Result.fail("未登录");
    }

    //5.根据房源的hid获取单个房源信息
    @GetMapping("/forehouse/{hid}")
    public Object house(@PathVariable("hid") int hid) {
        House house = houseService.get(hid);
//        接收一个house获取对应的单个图片集合
        List<HouseImage> houseSingleImages = houseImageService.listSingleHouseImages(house);
        //        接收一个house获取对应的详情图片集合
        List<HouseImage> houseDetailImages = houseImageService.listDetailHouseImages(house);
//        把两个集合加到house对象中
        house.setHouseSingleImages(houseSingleImages);
        house.setHouseDetailImages(houseDetailImages);
//        接收House对象，获取对应的Review集合
        List<Review> reviews = reviewService.list(house);
//        把reviews中的前端用不上的数据置为null
        reviews=reviewService.getReviews(reviews);
        houseService.setReviewNumber(house);
        houseImageService.setFirstHouseImage(house);
//        因为有两个不同的对象或对象集合，改用Map存储
        Map<String,Object> map= new HashMap<>();
        map.put("house",house);
        map.put("reviews", reviews);
        return Result.success(map);
    }

   //6.搜索-随机排序
    @GetMapping("foresearch")
    public Object search( String keyword){
//        如果传来的keyword为空，就把它赋值为空串
        if(null==keyword)
            keyword = "";
//        获取房源集合
        List<House> ps= houseService.search(keyword);
//        设置每个房源的第一张图片，因为要在前台展示出来
        houseImageService.setFirstHouseImages(ps);
//        设置每个房源的评论数，因为要展示出来
        houseService.setReviewNumber(ps);
        return ps;
    }

//    7.搜索-按收藏数排序
    @GetMapping("foresearchCol")
    public List<House> searchOrderByCol(String keyword){
        //        如果传来的keyword为空，就把它赋值为空串
        if(null==keyword)
            keyword = "";
        //        获取房源集合
        List<House> ps= houseService.findAllByKeyWordOrderByCol(keyword);
        //        设置每个房源的第一张图片，因为要在前台展示出来
        houseImageService.setFirstHouseImages(ps);
//        设置每个房源的评论数，因为要展示出来
        houseService.setReviewNumber(ps);
        return ps;
    }

//    8.搜索-按时间排序
    @GetMapping("foresearchTime")
    public List<House> searchOrderByTime(String keyword){
        //        如果传来的keyword为空，就把它赋值为空串
        if(null==keyword)
            keyword = "";
        //        获取房源集合
        List<House> ps= houseService.findAllByKeyWordOrderByTime(keyword);
        //        设置每个房源的第一张图片，因为要在前台展示出来
        houseImageService.setFirstHouseImages(ps);
//        设置每个房源的评论数，因为要展示出来
        houseService.setReviewNumber(ps);
        return ps;
    }

//    9.搜索，按浏览数排序
    @GetMapping("foresearchScan")
    public List<House> searchOrderByScan(String keyword){
        //        如果传来的keyword为空，就把它赋值为空串
        if(null==keyword)
            keyword = "";
        //        获取房源集合
        List<House> ps= houseService.findAllByKeyWordOrderByScan(keyword);
        //        设置每个房源的第一张图片，因为要在前台展示出来
        houseImageService.setFirstHouseImages(ps);
//        设置每个房源的评论数，因为要展示出来
        houseService.setReviewNumber(ps);
        return ps;
    }

    //10.房源信息评论
    @PostMapping("view")
    public Object view(HttpSession session,int hid,String content){
        House house=houseService.get(hid);
        content = HtmlUtils.htmlEscape(content);
        User user =(User)  session.getAttribute("user");
        if (reviewService.countByUserAndHouse(user,house)<3) {
            Review review = new Review();
            review.setContent(content);
            review.setHouse(house);
            review.setUser(user);
            review.setCreateDate(new Date());
            reviewService.add(review);
            return 0;
        }
        else{
            return 1;
        }
    }

    //11.首页获取所有上架的房源，按随机顺序排序
    @GetMapping("/houses")
    public List<House> list() throws Exception {
        //return houseService.list();
        List<House> houses =houseService.findAllByStateEquals("上架");
        houseService.setReviewNumber(houses);
        return houses;
    }
    //12.首页获取所有上架的房源，按收藏数排序
    @GetMapping("getHouseOrderByCol")
    public List<House> findAllOrderByCol(){
        List<House> houses = houseService.findAllOrderByCol("上架");
        houseService.setReviewNumber(houses);
        return houses;
    }

    //13.首页获取所有上架的房源，按时间排序
    @GetMapping("getHouseOrderByTime")
    public List<House> getHouseOrderByTime(){
        List<House> houses = houseService.findAllOrderByTime("上架");
        houseService.setReviewNumber(houses);
        return houses;
    }

    //14.首页获取所有上架的房源，按浏览排序
    @GetMapping("getHouseOrderByScan")
    public List<House> getHouseOrderByScan(){
        List<House> houses = houseService.findAllOrderByScan();
        houseService.setReviewNumber(houses);
        return houses;
    }

    //15.普通用户：获取用户信息
    @GetMapping("/users1")
    public User get(HttpSession session){
        User user = (User)session.getAttribute("user");
        return user;
    }

    //16.普通用户：修改用户信息
    @PutMapping("/fusers")
    public void update(@RequestBody User user,HttpSession session) throws Exception {
        userService.update(user);
        session.setAttribute("user",user);
    }

    //17.在聊天室发布消息
    @PostMapping("messages")
    public void addMessage(@RequestBody Message bean,HttpSession session){
        User user = (User)session.getAttribute("user");
        bean.setUser(user);
        bean.setCreatetime(new Date());
        messageService.add(bean);
    }

    //18.删除一条信息,包含二级评论和一级评论
    @DeleteMapping("messages/{id}")
    public void deleteMessage(@PathVariable("id") Integer id){
        messageService.delete(id);
    }

    //19.分页查询聊天室所有信息，按时间排序
    //    start的意思是第0页
    @GetMapping("/messages")
    public Page4Navigator<Message> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Page4Navigator<Message> page =messageService.list(start, size, 5);  //5表示导航分页最多有5个，像 [1,2,3,4,5] 这样
        return page;
    }

    //20.前台：信息点赞功能
    @PutMapping("/like/{id}")
    public void like(@PathVariable("id")Integer id){
        Message message =messageService.get(id);
        message.setLike_count(message.getLike_count()+1);
        messageService.update(message);
    }

    //21.前台：一级评论区点赞功能
    @PutMapping("/like1/{id}")
    public void like1(@PathVariable("id")Integer id){
        CommentFirst commentFirst =commentFirstService.get(id);
        commentFirst.setLike_count(commentFirst.getLike_count()+1);
        commentFirstService.update(commentFirst);
    }

    //22.前台：二级评论区点赞功能
    @PutMapping("/like2/{id}")
    public void like2(@PathVariable("id")Integer id){
        CommentSecond commentSecond = commentSecondService.get(id);
        commentSecond.setLike_count(commentSecond.getLike_count()+1);
        commentSecondService.update(commentSecond);
    }

    //23.前台：评论功能
    @PostMapping("/comment_first/{mid}")
    public void comment(@RequestBody CommentFirst bean,@PathVariable("mid")Integer mid,HttpSession session){
        Message message = messageService.get(mid);
        //该消息评论数加1
        message.setComment_count(message.getComment_count()+1);
        messageService.update(message);
//        设置bean所属的消息
        bean.setMessage(message);
        User user = (User)session.getAttribute("user");
//        设置bean所属的用户
        bean.setUser(user);
        bean.setCreatetime(new Date());
        commentFirstService.add(bean);
//        通知方与接收方不是同一个人
        if (user.getId()!=message.getUser().getId())
        notificationService.addCommentFirst(user.getId(),message.getUser().getId(),message.getId(),bean.getId());
    }

    //24.回复功能
    @PostMapping("/comment_second/{cid}")
    public void reply(@RequestBody CommentSecond bean,@PathVariable("cid")Integer cid,HttpSession session){
        CommentFirst commentFirst = commentFirstService.get(cid);
//        评论的回复数+1
        commentFirst.setComment_count(commentFirst.getComment_count()+1);
        commentFirstService.update(commentFirst);
        bean.setCommentFirst(commentFirst);
        User user = (User)session.getAttribute("user");
        bean.setUser(user);
        bean.setCreatetime(new Date());
        commentSecondService.add(bean);
//        通知方与接收方不是同一个人
        if (user.getId()!=commentFirst.getUser().getId())
        notificationService.addCommentSecond(user.getId(),commentFirst.getUser().getId(),commentFirst.getId(),bean.getId());
    }

    //25.前台：查询出某一个消息的所有评论
    @GetMapping("/comment_first/{mid}")
    public List<CommentFirst> getCommentFirst(@PathVariable("mid")Integer mid){
        Message message = messageService.get(mid);
        List<CommentFirst> commentFirsts =  commentFirstService.findAllByMessage(message);
        return commentFirsts;
    }

    //26.查询出某一条评论的所有回复
    @GetMapping("/comment_second/{cid}")
    public List<CommentSecond> getCommentSecond(@PathVariable("cid")Integer cid){
        CommentFirst commentFirst = commentFirstService.get(cid);
        List<CommentSecond> commentSeconds = commentSecondService.findAllByCommentFirst(commentFirst);
        return commentSeconds;
    }

//    27.访问首页，数量+1
    @PutMapping("/addhome")
    public synchronized  void addHome(){
        Statistics statistics = statisticsService.get(1);
        statistics.setHome(statistics.getHome()+1);
        statisticsService.update(statistics);
    }

//    28.访问聊天区，数量+1
    @PutMapping("/addchatroom")
    public  void addChatroom(){
        synchronized(object) {//这样子，获得的就不是同一把锁了
            Statistics statistics = statisticsService.get(1);
            statistics.setChatroom(statistics.getChatroom() + 1);
            statisticsService.update(statistics);
        }
    }

//    29.获取首页和聊天区的访问总数量
    @GetMapping("/getStatistics")
    public Statistics getStatistics(){
        return statisticsService.get(1);
    }

    //30.获取总评论Top20
    @GetMapping("/getTop20")
    public List<Message> getTop20(){
        return messageService.findAllMessageTop20();
    }

    //31.获取该用户的发布的信息
    @GetMapping("getMessageByUser")
    public List<Message> getMessageByUser(HttpSession session){
        User user = (User)session.getAttribute("user");
        return messageService.findByUser(user);
    }

    //32.普通用户升级成户主
    @PutMapping("toHouseholder")
    public void toHouseholder(@RequestBody Householder householder, HttpSession session){
        User user =(User) session.getAttribute("user");
        householder.setTel(user.getTel());
        user.setPosition("户主");
        userService.update(user);
        householderService.add(householder);
        session.setAttribute("user",user);
        session.setAttribute("position",user.getPosition());
    }

//    33.用户收藏房源
    @PostMapping("collection/{id}")
    public int collection(HttpSession session,@PathVariable("id")Integer id){
        User user =(User)session.getAttribute("user");
        System.out.println(id);
        House house = houseService.get(id);
        if (collectionService.getByUserAndHouse(user,house)==null){
            house.setColnum(house.getColnum()+1);
            collectionService.add(user,house);
            return 0;
        }
        else{
            return 1;
        }
    }

    //34.用户查看自己收藏的，且是上架状态的房源
    @GetMapping("getmycollection")
    public List<House> getMyCollection(HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Collection> collections = collectionService.getAllByUser(user);
        List<House> houses = new ArrayList<>();
        for (Collection collection : collections){
            House house = collection.getHouse();
            if (house.getState().equals("上架")){
                houses.add(house);
            }
        }
        houseImageService.setFirstHouseImages(houses);
        return houses;
    }

    //35.用户取消收藏房源
    @PutMapping("deletemycol/{id}")
    public void deleteMyCollection(HttpSession session,@PathVariable("id")Integer id){
        User user = (User)session.getAttribute("user");
        House house = houseService.get(id);
//        获取到该收藏
        Collection collection = collectionService.getByUserAndHouse(user,house);
//        删除该收藏
        collectionService.delete(collection);
//        收藏数减-1
        house.setColnum(house.getColnum()-1);
//        更新到数据库中
        houseService.update(house);
    }

//    36.随机跳转到一个房源信息界面
    @GetMapping("randomJump")
    public Integer randomJump(){
        List<House> houses = houseService.findAll();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (House house : houses){
            arrayList.add(house.getId());
        }
        Random r=new Random();
        return arrayList.get(r.nextInt(arrayList.size()));
    }

//    37.后台统计数据
    @GetMapping("staNum")
    public HashMap<String,Integer> staNum(){
        HashMap<String,Integer> hashMap = new HashMap<>();
//        放入首页访问量和聊天室访问量
        Statistics statistics = statisticsService.get(1);
        hashMap.put("num1",statistics.getHome());
        hashMap.put("num2",statistics.getChatroom());
        hashMap.put("num3",userService.countAll());
        hashMap.put("num4", householderService.countAll());
        hashMap.put("num5", houseService.countAll());
        hashMap.put("num6", houseService.countAll1());
        hashMap.put("num7", messageService.countAll());
        hashMap.put("num8", commentFirstService.countAll());
        hashMap.put("num9", commentSecondService.countAll());
        hashMap.put("num10",userService.countChatroom());
        hashMap.put("num11",scanService.countScan());
        return hashMap;
    }

//    38.浏览房源界面，浏览数+1
    @PostMapping("scan/{id}")
    public void addScan(HttpSession session,@PathVariable("id") int id){
        House house = houseService.get(id);
        house.setScannum(house.getScannum()+1);
        houseService.update(house);
        //如果是登录状态
        if (session.getAttribute("user")!=null){
            User user = (User) session.getAttribute("user");
            scanService.add(user,house);
        }
    }

//    39.房源浏览记录,按时间顺序排序
    @GetMapping("scan")
    public ArrayList<House> getScan(HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Scan> scans = scanService.getScan(user);
//        用一个HashMap存储浏览的house以及最后一次浏览记录的id
        HashMap<House,Integer> houses1 = new HashMap<>();
        for (Scan scan : scans){
            House house = scan.getHouse();
            if (house.getState().equals("上架")){
                houses1.put(house,scan.getId());
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
//取出id
        for (Map.Entry<House, Integer> set : houses1.entrySet()) {
            list.add(set.getValue());
        }
//        排序
        Collections.sort(list);
        ArrayList<House> houses= new ArrayList<>();
//        按排序的逆序把house添加到houses中，这样就可以保证按时间最新的排在最前面
        for (int i = list.size()-1;i>=0;i--){
            for (Map.Entry<House, Integer> set : houses1.entrySet()) {
                if (set.getValue()==list.get(i)){
                    houses.add(set.getKey());
                }
            }
        }
        houseImageService.setFirstHouseImages(houses);
        return houses;
    }

//    40.分页获取该用户的全部通知数据
    @GetMapping("getNotification")
    public Page4Navigator<Notification> getNotification(HttpSession session,@RequestParam(value = "start", defaultValue = "0") int start,
        @RequestParam(value = "size", defaultValue = "4") int size,@RequestParam(value = "status", defaultValue ="all") String status){
        User user = (User)session.getAttribute("user");
//        最后输出的核心HashMap
        HashMap<String,Object> hashMap = new HashMap<>();
//        核心数据
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<>();
        Page4Navigator<Notification> page = notificationService.list(user.getId(),status,start,size,5);
        for (Notification notification : page.getContent()){
            HashMap hashMap1 = new HashMap();
            hashMap1.put("id",notification.getId());
            hashMap1.put("notifier",userService.get(notification.getNotifier()).getUsername());
            hashMap1.put("createtime",notification.getCreatetime());
            hashMap1.put("status",notification.getStatus());
            hashMap1.put("type",notification.getType());
            if (notification.getType().equals("评论")){
                hashMap1.put("fcontent",messageService.get(notification.getFatherid()).getContent());
                hashMap1.put("ccontent",commentFirstService.get(notification.getChildid()).getContent());
                hashMap1.put("mytype","消息");
            }
            else{
                hashMap1.put("fcontent",commentFirstService.get(notification.getFatherid()).getContent());
                hashMap1.put("ccontent",commentSecondService.get(notification.getChildid()).getContent());
                hashMap1.put("mytype","评论");
            }
            arrayList.add(hashMap1);
        }
        hashMap.put("data",arrayList);
        //消息数量
        int unread = notificationService.countByReceiverAndStatusEquals(user.getId(),"未读");
        int read = notificationService.countByReceiverAndStatusEquals(user.getId(),"已读");
        hashMap.put("unread",unread);
        hashMap.put("all",read+unread);
        hashMap.put("read",read);
        ArrayList arrayList1 = new ArrayList();
        arrayList1.add(hashMap);
        page.setContent(arrayList1);
        return page;
    }

//    41.未读消息数量
    @GetMapping("unreadnum")
    public int getUnreadNum(HttpSession session){
        if (session.getAttribute("user")==null) return 0;
        else {
            User user = (User)session.getAttribute("user");
            int unread = notificationService.countByReceiverAndStatusEquals(user.getId(),"未读");
            return unread;
        }
    }

//    42.删除一个通知
    @DeleteMapping("deleteNotifi/{id}")
    public void deleteNotifi(@PathVariable("id")Integer id){
        notificationService.delete(id);
    }

//    43.把一个通知标志为已读
    @PutMapping("readNotifi/{id}")
    public void readNotifi(@PathVariable("id")Integer id){
        Notification notification = notificationService.get(id);
        notification.setStatus("已读");
        notificationService.update(notification);
    }

//    44.删除该用户的所有通知
    @DeleteMapping("deteleAllNotifi")
    public void deleteAllNotifi(HttpSession session){
        User user = (User) session.getAttribute("user");
        notificationService.deleteAllNotifi(user.getId());
    }

   //45.把该用户的所有通知标记为已读
    @PutMapping("readAllNotifi")
    public void readAllNotifi(HttpSession session){
        User user = (User) session.getAttribute("user");
        notificationService.readAllNotifi(user.getId());
    }
}
