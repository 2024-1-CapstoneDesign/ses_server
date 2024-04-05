package capstone.ses.dto.soundeffect;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SoundEffectDto {
    private Long soundEffectId;
    private String soundEffectName;
    private String description;
    private String createBy;
    private Date createdAt;
}
