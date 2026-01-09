package gwaze2.memo.service;

import gwaze2.memo.dto.CreateMemoRequest;
import gwaze2.memo.dto.CreateMemoResponse;
import gwaze2.memo.entity.Memo;
import gwaze2.memo.repository.MemoRepository;
import gwaze2.user.dto.SessionUser;
import gwaze2.user.entity.User;
import gwaze2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;
    private final UserRepository userRepository;

    @Transactional
    public CreateMemoResponse save(SessionUser sessionUser, CreateMemoRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new IllegalStateException("없는 유저입니다.")
        );

        Memo memo = new Memo(user, request.getText());
        Memo savedMemo = memoRepository.save(memo);
        return new CreateMemoResponse(
                savedMemo.getId(),
                savedMemo.getText()
        );
    }
}
