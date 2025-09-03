package team.bridgers.backend.domain.board.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.bridgers.backend.domain.board.infrastructure.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

}
