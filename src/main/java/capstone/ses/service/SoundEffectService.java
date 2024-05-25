package capstone.ses.service;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import capstone.ses.domain.soundeffect.SoundEffectType;
import capstone.ses.dto.soundeffect.*;
import capstone.ses.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.Encoder;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundEffectService {
    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectTagRepository soundEffectTagRepository;
    private final SoundEffectTypeRepository soundEffectTypeRepository;
    private final SoundEffectSoundEffectTagRepository soundEffectSoundEffectTagRepository;
    private final SoundEffectTagQueryRepository soundEffectTagQueryRepository;

    public SoundEffectDto searchSoundEffect(Long soundEffectId) {
        SoundEffect soundEffect = soundEffectRepository.findById(soundEffectId).orElseThrow(() -> new EntityNotFoundException("not exist soundeffect."));

        List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();

        for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect)) {
            soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
        }

        List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

        for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffect)) {
            soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
        }

        return SoundEffectDto.builder()
                .soundEffectId(soundEffect.getId())
                .soundEffectName(soundEffect.getName())
                .description(soundEffect.getDescription())
                .summary(soundEffect.getSummary())
//                .createBy(soundEffect.getCreatedBy())
                .createdAt(soundEffect.getCreatedDate())
                .soundEffectTags(soundEffectTagDtos)
                .soundEffectTypes(soundEffectTypeDtos)
                .build();
    }

    public SoundEffectPaginationDto searchSoundEffects(SoundEffectCondition soundEffectCondition, Pageable pageable) {

        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();
        Page<SoundEffect> soundEffects = soundEffectRepository.searchSoundEffects(soundEffectCondition, pageable);

        for (SoundEffect soundEffect : soundEffects) {

            List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();

            for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect)) {
                soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
            }

            List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

            for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffect)) {
                soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
            }

            soundEffectDtos.add(SoundEffectDto.builder()
                    .soundEffectId(soundEffect.getId())
                    .soundEffectName(soundEffect.getName())
                    .description(soundEffect.getDescription())
                    .summary(soundEffect.getSummary())
//                    .createBy(soundEffect.get)
                    .createdAt(soundEffect.getCreatedDate())
                    .soundEffectTags(soundEffectTagDtos)
                    .soundEffectTypes(soundEffectTypeDtos)
                    .build());
        }

        return new SoundEffectPaginationDto(soundEffectDtos, soundEffects.getTotalPages());
    }

    public List<SoundEffectDto> searchRelativeSoundEffects(Long soundEffectId) {
        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();

        for (SoundEffect soundEffect : soundEffectRepository.searchRelativeSoundEffects(soundEffectTagQueryRepository.findAllBySoundEffectId(soundEffectId), soundEffectId)) {

            List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();

            for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect)) {
                soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
            }

            List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

            for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffect)) {
                soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
            }

            soundEffectDtos.add(SoundEffectDto.builder()
                    .soundEffectId(soundEffect.getId())
                    .soundEffectName(soundEffect.getName())
                    .description(soundEffect.getDescription())
                    .summary(soundEffect.getSummary())
//                    .createBy(soundEffect.get)
                    .createdAt(soundEffect.getCreatedDate())
                    .soundEffectTags(soundEffectTagDtos)
                    .soundEffectTypes(soundEffectTypeDtos)
                    .build());
        }

        return soundEffectDtos;
    }

    @Transactional
    public void updateSoundEffect(Long soundEffectId, SoundEffectForm soundEffectForm) {
        SoundEffect soundEffect = soundEffectRepository.findById(soundEffectId).orElseThrow(() -> new EntityNotFoundException("not exists soundeffect"));
        List<SoundEffectType> soundEffectTypes = soundEffectTypeRepository.findBySoundEffect(soundEffect);

        if (soundEffectTypes.isEmpty()) {
            throw new EntityNotFoundException("not exists soundeffect Type");
        }

        soundEffect.updateInfo(
                soundEffectForm.getName(),
                soundEffectForm.getDescription(),
                soundEffectForm.getSummary()
        );

        soundEffectTypes.get(0).updateInfo(
                soundEffectForm.getSoundEffectTypeName(),
                soundEffectForm.getLength(),
                soundEffectForm.getUrl(),
                soundEffectForm.getSampleRate(),
                soundEffectForm.getBitDepth(),
                soundEffectForm.getChannels(),
                soundEffectForm.getFileSize()
        );
    }

    @Transactional
    public String searchByDirect(MultipartFile file) throws IOException, UnsupportedAudioFileException {
        // 파일 확장자 확인
        String originalExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        //python에서 변환하는게 더 나은듯...ㅠ

        // .wav로 변환이 필요한 경우
        if (!"wav".equalsIgnoreCase(originalExtension)) {
            File convertedFile = convertToWav(file);
            return convertedFile.getName();
        }
        return file.getOriginalFilename();
    }

    private File convertToWav(MultipartFile file) throws IOException, UnsupportedAudioFileException {
        File tempFile = File.createTempFile("upload", ".tmp");
        file.transferTo(tempFile);

        File wavFile = new File(tempFile.getParent(), FilenameUtils.getBaseName(file.getOriginalFilename()) + ".wav");

        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(tempFile);
        AudioFormat baseFormat = audioInputStream.getFormat();
        AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
        );

        AudioInputStream convertedAudioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
        AudioSystem.write(convertedAudioInputStream, AudioFileFormat.Type.WAVE, wavFile);

        tempFile.delete();
        return wavFile;
    }
}
