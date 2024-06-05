package capstone.ses.dto.soundeffect;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SoundEffectDto {
    private Long soundEffectId;
    private String soundEffectName;
    private String description;
    private String summary;
    private Boolean isLiked;
    private String createBy;
    private LocalDateTime createdAt;
    private List<SoundEffectTagDto> soundEffectTags;
    private List<SoundEffectTypeDto> soundEffectTypes;

    @Builder
    public SoundEffectDto(
            Long soundEffectId,
            String soundEffectName,
            String description,
            String summary,
            Boolean isLiked,
            String createBy,
            LocalDateTime createdAt,
            List<SoundEffectTagDto> soundEffectTags,
            List<SoundEffectTypeDto> soundEffectTypes) {
        this.soundEffectId = soundEffectId;
        this.soundEffectName = soundEffectName;
        this.description = description;
        this.summary = summary;
        this.isLiked = isLiked;
        this.createBy = createBy;
        this.createdAt = createdAt;
        this.soundEffectTags = soundEffectTags;
        this.soundEffectTypes = soundEffectTypes;
    }
}
