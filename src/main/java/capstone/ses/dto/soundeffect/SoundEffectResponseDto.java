package capstone.ses.dto.soundeffect;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class SoundEffectResponseDto {
    private Long soundEffectId;
    private String soundEffectName;
    private String description;
    private String createBy;
    private Date createdAt;

    private List<SoundEffectTagDto> soundEffectTags;
    private List<SoundEffectTypeDto> soundEffectTypes;
}
