package com.example.school1.controller;

import com.example.school1.model.*;
import com.example.school1.reposotory.*;
import com.example.school1.security.CurrentUser;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.dialect.CUBRIDDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticlePictureRepository articlePictureRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private QuestionRepository questionRepository;
    @Value("${image.folder}")
    private String imageUploadDir;

    @GetMapping("/image")
    public void getImage(HttpServletResponse response,
                         @RequestParam("fileName") String fileName) throws IOException {
        InputStream in = new FileInputStream(imageUploadDir + fileName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @PostMapping("/addQuestion")
    public String addquestion(@AuthenticationPrincipal CurrentUser currentUser,
                              @ModelAttribute Question question,
                              @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        User user = currentUser.getUser();
        question.setUser(user);
        question.setPicUrl(pictureName);
        questionRepository.save(question);
        return "redirect:/blog-sidebar-left";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        user.setUserType(UserType.GUEST);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/page-login";
    }

    @GetMapping("/")
    public String index2(ModelMap map,
                         @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        return "index-03";
    }
    @GetMapping("page-404-particle")
    public String pageParticle(ModelMap map,
                         @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        return "page-404-particle";
    }
    @GetMapping("page-404-video")
    public String pageVideo(ModelMap map,
                         @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        return "page-404-video";
    }

    @GetMapping("/page-login")
    public String pageLogin(ModelMap map,
                            @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("allUsers", new User());
        return "page-login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal UserDetails userDetails) {
        CurrentUser currentUser = (CurrentUser) userDetails;
        if (currentUser != null) {
            return "redirect:/blog-lg-post-grid";
        } else return "redirect:/page-login";
    }

    @GetMapping("/blog-lg-post-grid")
    public String bloglgpostgrid(ModelMap modelMap,
                                 @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("isLoggedIn", currentUser != null);
        modelMap.addAttribute("currentUser", currentUser.getUser());
        modelMap.addAttribute("alquestion", questionRepository.findAllByUserId(currentUser.getUser().getId()));
        modelMap.addAttribute("alluser", userRepository.findAll());
        User user = currentUser.getUser();
        modelMap.addAttribute("currentfollowing", user.getFriendsUser());
        List<User> followerUser = new ArrayList<>();
        List<User> all = userRepository.findAll();
        for (User user1 : all) {
            List<User> friendsUser = user1.getFriendsUser();
            for (User user2 : friendsUser) {
                if (user2.getId() == user.getId()) {
                    followerUser.add(user1);

                }
            }
        }
        modelMap.addAttribute("followers", followerUser);
        return "blog-lg-post-grid";
    }

    @GetMapping("/page-register")
    public String pageRegister(ModelMap modelMap,
                               @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("isLoggedIn", currentUser != null);
        modelMap.addAttribute("user", new User());
        return "page-register";
    }

    @GetMapping("/page-sec-navbar")
    public String pagesecnavbar() {
        return "page-sec-navbar";
    }

    @GetMapping("/page-team")
    public String pageTeam() {
        return "page-team";
    }

    @GetMapping("/blog-contained")
    public String blogContained(ModelMap modelMap,
                                @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("isLoggedIn", currentUser != null);
        modelMap.addAttribute("allLesson", lessonRepository.findAll());
        modelMap.addAttribute("question", new Question());
        return "blog-contained";
    }

    @GetMapping("/add-article")
    public String addArticle(ModelMap modelMap,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        modelMap.addAttribute("isLoggedIn", currentUser != null);
        modelMap.addAttribute("currentUser", currentUser.getUser());
        modelMap.addAttribute("article", new Article());
        modelMap.addAttribute("articlePictur", new ArticlePicture());

        return "add-article";
    }

    @PostMapping("/addArticle")
    public String addArticle(@AuthenticationPrincipal CurrentUser currentUser,
                             @ModelAttribute Article article,
                             @ModelAttribute ArticlePicture articlePicture,
                             @RequestParam("picture") MultipartFile multipartFile) throws IOException {
        String pictureName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File imageDir = new File(imageUploadDir);
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }
        File file = new File(imageUploadDir + pictureName);
        multipartFile.transferTo(file);
        User user = currentUser.getUser();
        article.setUser(user);
        articlePicture.setPicUrl(pictureName);
        articleRepository.save(article);
        articlePicture.setArticle(article);
        articlePictureRepository.save(articlePicture);
        return "redirect:/blog-masonry";
    }

    @GetMapping("/blog-sidebar-right")
    public String sidebarRight(ModelMap map,
                               @AuthenticationPrincipal CurrentUser currentUser,
                               @RequestParam(value = "id") Integer id) {
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("lessons", lessonRepository.findAll());
        map.addAttribute("questuonBylesson", questionRepository.findAllByLessonId(id));
        return "blog-sidebar-right";
    }

    @GetMapping("/blog-masonry")
    public String allArticle(ModelMap map,
                             @AuthenticationPrincipal CurrentUser currentUser) {
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("allarticlePicture", articlePictureRepository.findAll());
        return "blog-masonry";
    }

    @GetMapping("/post-gallery")
    public String postgallery(ModelMap map,
                              @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam(value = "id") Integer id) {
        map.addAttribute("currentUser", currentUser.getUser());
        map.addAttribute("comment", new Comment());
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("allarticlePicture", articlePictureRepository.findById(id));
        map.addAttribute("comentByArticleId", commentRepository.findAllByArticlePictureId(id));

        return "post-gallery";
    }

    @GetMapping("/question-readmore")
    public String questionReadMore(ModelMap map,
                                   @AuthenticationPrincipal CurrentUser currentUser,
                                   @RequestParam(value = "id") Integer id) {
        map.addAttribute("currentUser", currentUser.getUser());
        map.addAttribute("question", new Question());
        map.addAttribute("commentquestion", new Comment());
        map.addAttribute("isLoggedIn", currentUser != null);
        map.addAttribute("allquestion", questionRepository.findById(id));
        map.addAttribute("comentByQuestionId", commentRepository.findAllByQuestionId(id));
        return "question-readmore";
    }

    @PostMapping("/add-coment")
    public String addComment(@AuthenticationPrincipal CurrentUser currentUser,
                             @ModelAttribute Comment comment, @RequestParam(value = "id") Integer id) {
        User user = currentUser.getUser();
        Optional<ArticlePicture> byId = articlePictureRepository.findById(id);
        comment.setUser(user);
        comment.setArticlePicture(byId.get());
        commentRepository.save(comment);
        return "redirect:/blog-masonry";
    }

    @PostMapping("/add-question-coment")
    public String addQuestionComment(@AuthenticationPrincipal CurrentUser currentUser,
                                     @ModelAttribute Comment comment, @RequestParam(value = "id") Integer id) {
        User user = currentUser.getUser();
        Optional<Question> byId = questionRepository.findById(id);
        comment.setUser(user);
        comment.setQuestion(byId.get());
        commentRepository.save(comment);
        return "redirect:/blog-sidebar-left";
    }

    @GetMapping("/search")
    public String searchUser(ModelMap map,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam(value = "keyword") String text) {
        map.addAttribute("isLoggedIn", currentUser != null);
        String userNamae;
        String userSurname;
        String[] split = text.split(" ");
        if (split.length == 2) {
            userNamae = split[0];
            userSurname = split[1];
            map.addAttribute("searchUser", userRepository.findAllByNameOrSurname(userNamae, userSurname));
        }
        if (split.length == 1) {
            if (userRepository.findAllByName(split[0]).size() != 0) {
                map.addAttribute("searchUser", userRepository.findAllByName(split[0]));
            } else {
                map.addAttribute("searchUser", userRepository.findAllBySurname(split[0]));
            }
        }
        return "searchUser";
    }

    @GetMapping("/guestUser")
    public String guestuser(ModelMap modelMap,
                            @AuthenticationPrincipal CurrentUser currentUser,
                            @RequestParam(value = "id") Integer id) {
        modelMap.addAttribute("allU", new User());
        modelMap.addAttribute("isLoggedIn", currentUser != null);
        modelMap.addAttribute("guestUser", userRepository.findById(id));
        modelMap.addAttribute("allQuestion", questionRepository.findAllByUserId(id));
        return "guestUser";
    }

    @PostMapping("/following")
    public String following(@AuthenticationPrincipal CurrentUser currentUser,
                            @RequestParam(value = "id") Integer id) {
        User user = currentUser.getUser();
        Optional<User> byId = userRepository.findById(id);
        List<User> friendsUser = user.getFriendsUser();
        User user1 = byId.get();
        user1.setNote(true);
        userRepository.save(user1);
        friendsUser.add(byId.get());
        user.setFriendsUser(friendsUser);
        userRepository.save(user);
        return "redirect:/blog-lg-post-grid";
    }
//
//    @GetMapping("/followers")
//    public String followers(ModelMap modelMap,
//                            @AuthenticationPrincipal CurrentUser currentUser) {
//        User user = currentUser.getUser();
//        return "guestUser";
//    }

    @GetMapping("/message")
    public String message(ModelMap modelMap,
                          @AuthenticationPrincipal CurrentUser currentUser,
                          @RequestParam(value = "id") Integer id) {
        User user = currentUser.getUser();
        modelMap.addAttribute("toUser", userRepository.findById(id));
        modelMap.addAttribute("isLoggedIn", currentUser != null);
        modelMap.addAttribute("fromUser", userRepository.findById(currentUser.getUser().getId()));
        modelMap.addAttribute("message", new Message());
        Optional<User> byId = userRepository.findById(id);
        List<Message> allByFromUserAndToUser = messageRepository.findAllByFromUserAndToUser(user, byId.get());
        allByFromUserAndToUser.addAll(messageRepository.findAllByFromUserAndToUser(byId.get(), user));
        int size = allByFromUserAndToUser.size();
        Message[] arrayList = new Message[size];
        Message[] messages = allByFromUserAndToUser.toArray(arrayList);
        Message temp;
        allByFromUserAndToUser.addAll(messageRepository.findAllByFromUserAndToUser(byId.get(), user));
        for (int i = 0; i < messages.length; i++) {
            for (int j = 1; j < messages.length - i; j++) {
                if (messages[j - 1].getId() > messages[j].getId()) {
                    temp = messages[j - 1];
                    messages[j - 1] = messages[j];
                    messages[j] = temp;
                }
            }
        }
        modelMap.addAttribute("messages", messages);
        return "message";
    }

    @PostMapping("/sendmessage")
    public String sendmessage(ModelMap modelMap,
                              @AuthenticationPrincipal CurrentUser currentUser,
                              @RequestParam(value = "id") Integer id,
                              @ModelAttribute("message") Message message) {
        User fromuser = currentUser.getUser();
        Optional<User> byId = userRepository.findById(id);
        User touser = byId.get();
        message.setFromUser(fromuser);
        message.setToUser(touser);
        messageRepository.save(message);
        return "redirect:/blog-lg-post-grid";
    }

}
