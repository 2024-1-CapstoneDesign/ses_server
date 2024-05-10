package capstone.ses.domain.soundeffect;

import capstone.ses.domain.system.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private String summary;

    @Builder
    public SoundEffect(Long id, String name, String description, String summary) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.summary = summary;
    }

    public void updateInfo(String name, String description, String summary) {
        this.name = name;
        this.description = description;
        this.summary = summary;
    }
}
