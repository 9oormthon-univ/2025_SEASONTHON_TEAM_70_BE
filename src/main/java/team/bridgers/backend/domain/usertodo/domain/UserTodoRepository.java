package team.bridgers.backend.domain.usertodo.domain;

import team.bridgers.backend.domain.user.domain.User;

import java.util.List;

public interface UserTodoRepository {

    void save(UserTodo userTodo);

    UserTodo findByUserTodoId(Long userTodoId);

    List<UserTodo> findAllByUser(User user, String sortBy);

}
