package jp.gihyo.projava.tasklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;

@Controller
public class HomeController {
    private final TaskListDao dao;
    @Autowired
    HomeController(TaskListDao dao){
        this.dao = dao;
    }

    @RequestMapping(value = "/hello")
    String hello(Model model) {
        model.addAttribute("time", LocalDateTime.now());
        return "hello";
    }

    record TaskItem(String id, String task, String deadline, String memo, boolean done) {
    }
    //private List<TaskItem> taskItems = new ArrayList<>();
    @GetMapping("/list")
    String listItems(Model model){
        List<TaskItem> taskItems = dao.findAll();
        model.addAttribute("taskList",taskItems);
        return "home";
    }
    @GetMapping("/add")
    String addItem(@RequestParam("task")String task,
                   @RequestParam("deadline")String deadline){
        String id = UUID.randomUUID().toString().substring(0,8);
        TaskItem item = new TaskItem(id,task,deadline,"",false);
        dao.add(item);
        return "redirect:/list";
    }
    @GetMapping("/delete")
    String delete(@RequestParam("id")String id){
        dao.delete(id);
        return "redirect:/list";
    }
    @GetMapping("/update")
    String update(@RequestParam("id")String id,
                  @RequestParam("task")String task,
                  @RequestParam("deadline")String deadline,
                  @RequestParam("memo")String memo,
                  @RequestParam("done")boolean done){
        TaskItem item = new TaskItem(id, task, deadline, memo, done);
        int i = this.dao.update(item);
        return "redirect:/list";
    }
    @GetMapping("/search_month")
    String serchMonth(Model model, @RequestParam("month") String month){
        List<TaskItem>taskItems = this.dao.searchMonth(month);
        model.addAttribute("taskList", taskItems);
        return "home";
    }

    @GetMapping("/find_incomplete")
    String findIncomplete(Model model, @RequestParam("done")String done){
        List<TaskItem>taskItems = this.dao.findIncomplete(done);
        model.addAttribute("taskList", taskItems);
        return "home";
    }
//    String searchMonth(Model model, @RequestParam("month") String month){
//        List<TaskItem> taskItems = this.dao.searchMonth(month);
//        model.addAttribute("taskList", taskItems);
//        return "home";
//    }
}


//    @GetMapping("/restlist")
//    String listItem(){
//        String result = taskItems.stream()
//                .map(HomeRestController.TaskItem::toString)
//                .collect(Collectors.joining(" , "));
//        return result;
//}