package com.zcy.web;


import com.zcy.service.BlogService;
import com.zcy.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        /*根目录下主页面的展示
        *
        * */
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(4));
//        model.addAttribute("countBlog",blogService.getBlogTotal());
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                 Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }

    @GetMapping("/blog/{id}")  //获取请求，并传递id参数
    public String blog(@PathVariable Long id,Model model){  //@PathVariable定义的变量这里的id，接收@GetMapping注解传递的参数
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/blogmessage")
    public String blogMessage(Model model){
        Integer countBlog = blogService.getBlogTotal();
        Integer countViews = blogService.getViewTotal();
        Integer countComment =blogService.getCommentTotal();
        Integer countMessage = blogService.getMessageTotal();

        model.addAttribute("countBlog",countBlog);
        model.addAttribute("countViews",countViews);
        model.addAttribute("countComment",countComment);
        model.addAttribute("countMessage",countMessage);

        return "_fragments :: blogMessage";
    }

}
