package capstone.ses.dto.soundeffect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SoundEffectPaginationDto {
    List<SoundEffectDto> soundEffectDtos;
    Integer totalPages;
}
