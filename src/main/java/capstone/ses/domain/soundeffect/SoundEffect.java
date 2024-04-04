package capstone.ses.domain.soundeffect;

import capstone.ses.domain.system.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoundEffect extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sound_effect_id")
    private Long id;
    private String name;
    private String description;

    @Builder
    public SoundEffect(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
