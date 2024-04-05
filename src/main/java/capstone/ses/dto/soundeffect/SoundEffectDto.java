package capstone.ses.dto.soundeffect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class SoundEffectDto {
    private Long soundEffectId;
    private String soundEffectName;
    private String description;
    private String createBy;
    private LocalDateTime createdAt;
    private String url;
    private String soundEffectTypeName;
    private List<SoundEffectTagDto> soundEffectTags;
    private List<SoundEffectTypeDto> soundEffectTypes;
}
