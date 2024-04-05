package capstone.ses.service;

import capstone.ses.repository.SoundEffectRepository;
import capstone.ses.repository.SoundEffectTagRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundEffectService {
    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectTagRepository soundEffectTagRepository;
}
