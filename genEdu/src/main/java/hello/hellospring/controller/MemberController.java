package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class MemberController {

    private final MemberService memberService;
    Member target;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());
        memberService.join(member);
        return "redirect:/members/login";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

    @GetMapping(value = "/members/login")
    public String loginForm() {
        return "members/loginMemberForm";
    }

    @PostMapping(value = "/members/login")
    public String login(MemberForm form) {
        String id = form.getName();
        String pw = form.getPassword();
        System.out.print(form.getName());
        MemoryMemberRepository repository = new MemoryMemberRepository();
        target = repository.findByName(id).get();
        if (target.getPassword().equals(pw) == true)
            return "redirect:/home_login";
        else
            return "members/memberList";
    }

    @GetMapping(value = "home_login")
    public String home_login(Model model) {
        model.addAttribute("name", target.getName());
        return "home_login";
    }
}
