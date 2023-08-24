package jp.gihyo.projava.tasklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class HomeRestController {//データを返す方式、スマホゲームなどで使用

    private final TaskListDao dao;

    @Autowired
    HomeRestController(TaskListDao dao) {
        this.dao = dao;
    }
    @PostMapping("/rest_add")
    List<HomeController.TaskItem> addItem(@RequestParam("task") String task,
                                          @RequestParam("deadline") String deadLine,
                                          @RequestParam("memo") String memo){
        String id = UUID.randomUUID().toString().substring(0, 8);
        HomeController.TaskItem item = new HomeController.TaskItem(id, task, deadLine,"",  false);
        this.dao.add(item);
        return this.dao.findAll();
    }
}




    /*record TaskItem(String id, String task, String deadline, String memo, boolean done) {//プログラムで使うデータ
    }

    private List<TaskItem> taskItems = new ArrayList<>();//入れ物

    @RequestMapping(value = "/resthello")
    String hello() {
        return """
                hello.
                It works!
                現在時刻は%sです
                """.formatted((LocalDateTime.now()));
    }

    @GetMapping("/restadd")
    String addItem(@RequestParam("task") String task,
                   @RequestParam("deadline") String deadline) {
        String id = UUID.randomUUID().toString().substring(0, 8);
        TaskItem item = new TaskItem(id, task, deadline, "",false);
        taskItems.add(item);

        return "タスクを追加しました";
    }


    @GetMapping("/restlist")
    String listItems(){
        String result = taskItems.stream()
                .map(TaskItem::toString)
                .collect(Collectors.joining(","));
        return result;
    }
}
*/