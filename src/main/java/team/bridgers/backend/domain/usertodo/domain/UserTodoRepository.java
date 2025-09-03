package team.bridgers.backend.domain.usertodo.domain;

public interface UserTodoRepository {

    void save(UserTodo userTodo);

    UserTodo findByUserTodoId(Long userTodoId);

}
