package model;

public class Todo {
    String todoTitle;
    String todoDescription;
    String todoDate;
    String todoKey;

    public Todo(){

    }

    public Todo(String todoTitle, String todoDescription, String todoDate, String todoKey) {
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.todoDate = todoDate;
        this.todoKey = todoKey;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public void setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }

    public String getTodoKey() {
        return todoKey;
    }

    public void setTodoKey(String todoKey) {
        this.todoKey = todoKey;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }
}
