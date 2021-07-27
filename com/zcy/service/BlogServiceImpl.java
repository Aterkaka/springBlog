package com.zcy.service;

import com.zcy.NotFoundException;
import com.zcy.dao.BlogRepository;
import com.zcy.dao.CommentRepository;
import com.zcy.dao.MessageRepository;
import com.zcy.po.Blog;
import com.zcy.po.Type;
import com.zcy.util.MarkdownUtils;
import com.zcy.util.MyBeanUtils;
import com.zcy.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @author: 张诚耀
 * @create: 2021-02-26
 */

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Blog getBlog(Long id) {
        //通过id获取博客
        return blogRepository.getBlogById(id);
    }

    @Override
    public Blog getAndConvert(Long id) {    //获取博客的内容
        Blog blog = blogRepository.getBlogById(id);   //通过id找到博客
        if (blog == null){          //判断博客是否为空，空则抛出异常
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();  //new一个新的博客
        BeanUtils.copyProperties(blog, b);  //将前面查到的博客复制给b对象（对象复制）
        String content = b.getContent();  //获取博客的内容
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //通过Markdown初始化博客内容。
        blogRepository.updateViews(id);
        return b;  //返回修改后的对象
    }


    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        /*页面展示
        * 传入参数：分页Pageable和查询BlogQuery
        *
        * */
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                /*参数分析：
                * Root<Blog> root ：把Blog对象映射为一个Root，从中可以获取t_blog表中的一些字段等
                * CriteriaQuery<?> cq ：查询容器，存放查询的条件
                * CriteriaBuilder cb ：查询工具或者说查询器
                * */

                List<Predicate> predicates = new ArrayList<>();   //存放Predicate的列表数组

                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    //如果title不为空字符串 and title不为空
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                    //查询条件组合：
                }
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    } //通过传入分页参数对博客进行展示

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        //通过查询语句和分页来展示博客
        return blogRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        //推荐博客的展示
        Pageable pageable = PageRequest.of(0,size,Sort.by(Sort.Direction.DESC, "updateTime"));
        return blogRepository.findTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year, blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        //博客的更新，传入博客id和更新的博客对象
        Blog b = blogRepository.getBlogById(id);//获取原来（未更新）的博客对象
        if (b == null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b,MyBeanUtils.getNullPropertyNames(blog));
        //过滤属性值为空的属性/字段，然后把blog对象赋值给b.
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public Integer getBlogTotal() {
        return blogRepository.getblogTotal();
    }

    @Override
    public Integer getCommentTotal() {
        return commentRepository.getCommentTotal();
    }

    @Override
    public Integer getViewTotal() {
        return blogRepository.getViewTotal();
    }

    @Override
    public Integer getMessageTotal() {
        return messageRepository.getMessageTotal();
    }

//    @Override
//    public Integer getCommentbyId() {
//        return null;
//    }


}
