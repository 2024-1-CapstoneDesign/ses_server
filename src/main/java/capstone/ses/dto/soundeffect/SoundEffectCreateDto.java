package capstone.ses.dto.soundeffect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SoundEffectCreateDto {
    private String file;
    private String soundEffectName;
    private String description;
    private String summary;
    private LocalDateTime createdAt;
    private SoundEffectTypeDto soundEffectTypes;
}
