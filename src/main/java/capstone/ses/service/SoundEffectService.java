package capstone.ses.service;

import capstone.ses.domain.member.Member;
import capstone.ses.domain.soundeffect.*;
import capstone.ses.dto.soundeffect.*;
import capstone.ses.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundEffectService {
    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectTagRepository soundEffectTagRepository;
    private final SoundEffectTypeRepository soundEffectTypeRepository;
    private final SoundEffectSoundEffectTagRepository soundEffectSoundEffectTagRepository;
    private final SoundEffectTagQueryRepository soundEffectTagQueryRepository;
    private final MemberRepository memberRepository;
    private final LikeSoundEffectRepository likeSoundEffectRepository;
    private final MemberService memberService;

    @Autowired
    private RestTemplate restTemplate;

    private final SoundEffectSoundEffectTagRelRepository soundEffectSoundEffectTagRelRepository;

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
                .createBy(memberRepository.findById(soundEffect.getCreatedBy()).get().getName())
                .createdAt(soundEffect.getCreatedDate())
                .soundEffectTags(soundEffectTagDtos)
                .soundEffectTypes(soundEffectTypeDtos)
                .build();
    }

    public SoundEffectPaginationDto searchSoundEffects(SoundEffectCondition soundEffectCondition, String accessToken, Pageable pageable) throws JsonProcessingException {

        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();
        Long memberId = accessToken != null ? memberService.findMemberByAccessToken(accessToken).getId() : null;

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
//                    .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffect, memberId))
//                    .createBy(memberRepository.findById(soundEffect.getCreatedBy()).get().getName())
                    .createdAt(soundEffect.getCreatedDate())
                    .soundEffectTags(soundEffectTagDtos)
                    .soundEffectTypes(soundEffectTypeDtos)
                    .build());
        }

        return new SoundEffectPaginationDto(soundEffectDtos, soundEffects.getTotalPages());
    }

    public List<SoundEffectDto> searchRelativeSoundEffects(Long soundEffectId, String accessToken) throws JsonProcessingException {
        Long memberId = accessToken != null ? memberService.findMemberByAccessToken(accessToken).getId() : null;
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
                    .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffect, memberId))
                    .createBy(memberRepository.findById(soundEffect.getCreatedBy()).get().getName())
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
    public List<SoundEffectDto> searchByDirect(MultipartFile file, String accessToken) throws IOException, UnsupportedAudioFileException {
        Long memberId = null;

        if (accessToken != null) {
            memberId = memberService.findMemberByAccessToken(accessToken).getId();
        }

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new InputStreamResource(file.getInputStream()))
                .header("Content-Disposition", "form-data; name=file; filename=" + file.getOriginalFilename());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<?> entity = new HttpEntity<>(builder.build(), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://model-o5rcbmo3sq-du.a.run.app/predict",
                entity,
                String.class
        );

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());

        List<Map<String, Object>> results = objectMapper.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {
        });

        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();

        for (Map<String, Object> result : results) {
            List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();
            List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

            SoundEffect soundEffectByName = soundEffectRepository.findByName((String) result.get("sound_effect_type_name"));
            if (soundEffectByName == null) {
                SoundEffect soundEffect = soundEffectRepository.save(
                        SoundEffect.builder()
                                .name((String) result.get("sound_effect_type_name"))
                                .description((String) result.get("description"))
                                .summary((String) result.get("summary"))
                                .build()
                );

                String name = (String) result.get("name");
                String[] split = name.split("\\."); // 점(.)을 이스케이프하여 사용
                String lastValue = split[split.length - 1];

                String[] parts = ((String) result.get("length")).split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                int seconds = Integer.parseInt(parts[2]);
                int totalTimeInSeconds = hours * 3600 + minutes * 60 + seconds;

                SoundEffectType soundEffectType = soundEffectTypeRepository.save(
                        SoundEffectType.builder()
                                .soundEffect(soundEffect)
                                .soundEffectTypeName(lastValue)
                                .length(totalTimeInSeconds)
                                .url((String) result.get("gcs_url"))
                                .sampleRate(new BigDecimal((Integer) result.get("sample_rate")))
                                .bitDepth((Integer) result.get("bit_depth"))
                                .channels((String) result.get("channels"))
                                .fileSize(new BigDecimal((Double) result.get("file_size")))
                                .build()
                );

                soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
                List<String> tagNames = new ArrayList<>();

                for (String s : ((String) result.get("tag")).split("\\s+|\\|")) {
                    if (s.equals(" ") || s.isEmpty() || s.equals("|")) {
                        continue;
                    }
                    tagNames.add(s);
                }

                for (String tagName : tagNames) {
                    SoundEffectTag byName = soundEffectTagRepository.findByName(tagName);
                    if (byName == null) {
                        SoundEffectTag soundEffectTag = soundEffectTagRepository.save(
                                new SoundEffectTag(tagName)
                        );

                        soundEffectSoundEffectTagRelRepository.save(
                                new SoundEffectSoundEffectTagRel(soundEffect, soundEffectTag)
                        );

                        soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectTag));
                    } else {
                        if (soundEffectSoundEffectTagRelRepository.findBySoundEffectAndSoundEffectTag(soundEffect, byName) == null) {
                            soundEffectSoundEffectTagRelRepository.save(
                                    new SoundEffectSoundEffectTagRel(soundEffect, byName)
                            );
                        }
                        soundEffectTagDtos.add(SoundEffectTagDto.of(byName));
                    }
                }

                soundEffectDtos.add(
                        SoundEffectDto.builder()
                                .soundEffectId(soundEffect.getId())
                                .soundEffectName(soundEffect.getName())
                                .description(soundEffect.getDescription())
                                .summary(soundEffect.getSummary())
                                .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffect, memberId))
                                .createdAt(soundEffect.getCreatedDate())
                                .soundEffectTags(soundEffectTagDtos)
                                .soundEffectTypes(soundEffectTypeDtos)
                                .build()
                );

            } else {

                for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffectByName)) {
                    soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
                }


                for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffectByName)) {
                    soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
                }

                soundEffectDtos.add(SoundEffectDto.builder()
                        .soundEffectId(soundEffectByName.getId())
                        .soundEffectName(soundEffectByName.getName())
                        .description(soundEffectByName.getDescription())
                        .summary(soundEffectByName.getSummary())
                        .createBy(memberRepository.findById(soundEffectByName.getCreatedBy()).get().getName())
                        .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffectByName, memberId))
                        .createdAt(soundEffectByName.getCreatedDate())
                        .soundEffectTags(soundEffectTagDtos)
                        .soundEffectTypes(soundEffectTypeDtos)
                        .build());
            }

        }

        return soundEffectDtos;
    }

    public List<SoundEffectDto> searchLikedSoundEffects(String accessToken) throws JsonProcessingException {
        Member member = memberService.findMemberByAccessToken(accessToken);
        Long memberId = member.getId();

        List<SoundEffect> soundEffects = soundEffectRepository.searchLikedSoundEffects(memberId);
        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();

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
                    .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffect, memberId))
                    .createBy(memberRepository.findById(soundEffect.getCreatedBy()).get().getName())
                    .createdAt(soundEffect.getCreatedDate())
                    .soundEffectTags(soundEffectTagDtos)
                    .soundEffectTypes(soundEffectTypeDtos)
                    .build());
        }

        return soundEffectDtos;
    }

    @Transactional
    public List<SoundEffectDto> getYoutudeAudio(String url, String startTime, String endTime, String accessToken) throws JsonProcessingException {
        Long memberId = null;

        if (accessToken != null) {
            memberId = memberService.findMemberByAccessToken(accessToken).getId();
        }
        // 파이썬 서버의 URL
        String pythonServerUrl = "https://soundeffect-search.p-e.kr:8443/download/?url=" + url + "&from=" + startTime + "&to=" + endTime;

        // HTTP GET 요청을 보내서 오디오 파일을 받아옴
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(pythonServerUrl, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS.mappedFeature());

        List<Map<String, Object>> results = objectMapper.readValue(response, new TypeReference<List<Map<String, Object>>>() {
        });

        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();

        for (Map<String, Object> result : results) {
            List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();
            List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

            SoundEffect soundEffectByName = soundEffectRepository.findByName((String) result.get("sound_effect_type_name"));
            if (soundEffectByName == null) {
                SoundEffect soundEffect = soundEffectRepository.save(
                        SoundEffect.builder()
                                .name((String) result.get("sound_effect_type_name"))
                                .description((String) result.get("description"))
                                .summary((String) result.get("summary"))
                                .build()
                );

                String name = (String) result.get("name");
                String[] split = name.split("\\."); // 점(.)을 이스케이프하여 사용
                String lastValue = split[split.length - 1];

                String[] parts = ((String) result.get("length")).split(":");
                int hours = Integer.parseInt(parts[0]);
                int minutes = Integer.parseInt(parts[1]);
                int seconds = Integer.parseInt(parts[2]);
                int totalTimeInSeconds = hours * 3600 + minutes * 60 + seconds;

                SoundEffectType soundEffectType = soundEffectTypeRepository.save(
                        SoundEffectType.builder()
                                .soundEffect(soundEffect)
                                .soundEffectTypeName(lastValue)
                                .length(totalTimeInSeconds)
                                .url((String) result.get("gcs_url"))
                                .sampleRate(new BigDecimal((Integer) result.get("sample_rate")))
                                .bitDepth((Integer) result.get("bit_depth"))
                                .channels((String) result.get("channels"))
                                .fileSize(new BigDecimal((Double) result.get("file_size")))
                                .build()
                );

                soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
                List<String> tagNames = new ArrayList<>();

                for (String s : ((String) result.get("tag")).split("\\s+|\\|")) {
                    if (s.equals(" ") || s.isEmpty() || s.equals("|")) {
                        continue;
                    }
                    tagNames.add(s);
                }

                for (String tagName : tagNames) {
                    SoundEffectTag byName = soundEffectTagRepository.findByName(tagName);
                    if (byName == null) {
                        SoundEffectTag soundEffectTag = soundEffectTagRepository.save(
                                new SoundEffectTag(tagName)
                        );

                        soundEffectSoundEffectTagRelRepository.save(
                                new SoundEffectSoundEffectTagRel(soundEffect, soundEffectTag)
                        );

                        soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectTag));
                    } else {
                        if (soundEffectSoundEffectTagRelRepository.findBySoundEffectAndSoundEffectTag(soundEffect, byName) == null) {
                            soundEffectSoundEffectTagRelRepository.save(
                                    new SoundEffectSoundEffectTagRel(soundEffect, byName)
                            );
                        }
                        soundEffectTagDtos.add(SoundEffectTagDto.of(byName));
                    }
                }

                soundEffectDtos.add(
                        SoundEffectDto.builder()
                                .soundEffectId(soundEffect.getId())
                                .soundEffectName(soundEffect.getName())
                                .description(soundEffect.getDescription())
                                .summary(soundEffect.getSummary())
                                .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffect, memberId))
                                .createdAt(soundEffect.getCreatedDate())
                                .soundEffectTags(soundEffectTagDtos)
                                .soundEffectTypes(soundEffectTypeDtos)
                                .build()
                );

            } else {

                for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffectByName)) {
                    soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
                }


                for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffectByName)) {
                    soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
                }

                soundEffectDtos.add(SoundEffectDto.builder()
                        .soundEffectId(soundEffectByName.getId())
                        .soundEffectName(soundEffectByName.getName())
                        .description(soundEffectByName.getDescription())
                        .summary(soundEffectByName.getSummary())
                        .isLiked(soundEffectRepository.checkLikedSoundEffecet(soundEffectByName, memberId))
                        .createBy(memberRepository.findById(soundEffectByName.getCreatedBy()).get().getName())
                        .createdAt(soundEffectByName.getCreatedDate())
                        .soundEffectTags(soundEffectTagDtos)
                        .soundEffectTypes(soundEffectTypeDtos)
                        .build());
            }

        }

        return soundEffectDtos;
    }

    @Transactional
    public Boolean updateLikedSoundEffect(Long soundEffectId, String accessToken) throws JsonProcessingException {
        Member member = memberService.findMemberByAccessToken(accessToken);
        SoundEffect soundEffect = soundEffectRepository.findById(soundEffectId).orElseThrow(() -> new EntityNotFoundException("not exists soundEffect"));
        LikeSoundEffect bySoundEffectAndMember = likeSoundEffectRepository.findBySoundEffectAndMember(soundEffect, member);
        if (bySoundEffectAndMember == null) {
            likeSoundEffectRepository.save(new LikeSoundEffect(soundEffect, member));
        } else {
            bySoundEffectAndMember.updateActive();
        }
        return true;
    }

    @Transactional
    public Boolean updateUnlikedSoundEffect(Long soundEffectId, String accessToken) throws JsonProcessingException {
        Member member = memberService.findMemberByAccessToken(accessToken);
        SoundEffect soundEffect = soundEffectRepository.findById(soundEffectId).orElseThrow(() -> new EntityNotFoundException("not exists soundEffect"));
        LikeSoundEffect bySoundEffectAndMember = likeSoundEffectRepository.findBySoundEffectAndMember(soundEffect, member);
        bySoundEffectAndMember.updateInactive();
        return false;
    }


//    private File convertToWav(MultipartFile file) throws IOException, UnsupportedAudioFileException {
//        File tempFile = File.createTempFile("upload", ".tmp");
//        file.transferTo(tempFile);
//
//        File wavFile = new File(tempFile.getParent(), FilenameUtils.getBaseName(file.getOriginalFilename()) + ".wav");
//
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(tempFile);
//        AudioFormat baseFormat = audioInputStream.getFormat();
//        AudioFormat targetFormat = new AudioFormat(
//                AudioFormat.Encoding.PCM_SIGNED,
//                baseFormat.getSampleRate(),
//                16,
//                baseFormat.getChannels(),
//                baseFormat.getChannels() * 2,
//                baseFormat.getSampleRate(),
//                false
//        );
//
//        AudioInputStream convertedAudioInputStream = AudioSystem.getAudioInputStream(targetFormat, audioInputStream);
//        AudioSystem.write(convertedAudioInputStream, AudioFileFormat.Type.WAVE, wavFile);
//
//        tempFile.delete();
//        return wavFile;
//    }
}
