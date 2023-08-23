package jp.gihyo.projava.tasklist;
import jp.gihyo.projava.tasklist.HomeController.TaskItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TaskListDao {
    private final static String TABLE_NAME = "tasklist";
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    TaskListDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public int add(HomeController.TaskItem item) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName(TABLE_NAME);
        return insert.execute(param);
    }
    //ここまで更新
    public List<TaskItem>findAll(){
        String query = "SELECT * FROM " + TABLE_NAME;



        List<Map<String, Object>> result = jdbcTemplate.queryForList(query);//SQL
        List<TaskItem> taskItems = result.stream()
                .map((Map<String, Object> row) -> new TaskItem(
                        row.get("id").toString(),
                        row.get("task").toString(),
                        row.get("deadline").toString(),
                        row.get("memo").toString(),
                        (Boolean)row.get("done")))
                .toList();
        return taskItems;
    }
    public int delete(String id){
        int number = jdbcTemplate.update("Delete FROM tasklist WHERE id = ?",id);
        return number;
    }
    public int update(TaskItem taskItem){
        int number = jdbcTemplate.update(
                "UPDATE tasklist SET task = ?, deadline = ?, ,memo= ?,done = ? WHERE id = ?",
                taskItem.task(),
                taskItem.deadline(),
                taskItem.deadline(),
                taskItem.done(),
                taskItem.id());
        return number;

    }
}
