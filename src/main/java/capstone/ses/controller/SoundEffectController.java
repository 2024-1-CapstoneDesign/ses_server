package capstone.ses.controller;

import capstone.ses.domain.soundeffect.SoundEffectType;
import capstone.ses.dto.soundeffect.SoundEffectCondition;
import capstone.ses.dto.soundeffect.SoundEffectDto;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.repository.SoundEffectTypeRepository;
import capstone.ses.service.SoundEffectService;
import capstone.ses.service.SoundEffectTagService;
import capstone.ses.service.YoutubeDownloadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Subgraph;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SoundEffectController {

    private final SoundEffectService soundEffectService;
    private final SoundEffectTypeRepository soundEffectTypeRepository;
    private final SoundEffectTagService soundEffectTagService;
    private final YoutubeDownloadService youtubeDownloadService;

    //soundeffect-001: 효과음 리스트 조회
    @GetMapping("/soundeffect")
    public Result searchSoundEffects(
            @RequestParam(required = false) Integer fromLength,
            @RequestParam(required = false) Integer toLength,
            @RequestParam(required = false) BigDecimal sampleRate,
            @RequestParam(required = false) Integer bitDepth,
            @RequestParam(required = false) String channels,
            @RequestParam(required = false) List<Long> soundEffectTagId,
            Pageable pageable
            ) {

        try {

            List<SoundEffectDto> soundEffectDtos = soundEffectService.searchSoundEffects(SoundEffectCondition.builder()
                            .fromLength(fromLength)
                            .toLength(toLength)
                            .sampleRate(sampleRate)
                            .bitDepth(bitDepth)
                            .channels(channels)
                            .soundEffectTagIds(soundEffectTagId)
                    .build(), pageable);

            if (soundEffectDtos.isEmpty()) {
                throw new EntityNotFoundException("조건에 해당하는 효과음이 존재하지 않습니다.");
            }
            return new Result(ResultCode.SUCCESS, soundEffectDtos);
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-002: 효과음 세부 조회
    @GetMapping("/soundeffect/{soundEffectId}")
    public Result searchSoundEffect(@PathVariable Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchSoundEffect(soundEffectId));
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-007: 효과음 수정
    @PutMapping("/soundeffect/{soundEffectId}")
    public Result updateSoundEffect(@PathVariable Long soundEffectId, @RequestBody @Valid ) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.updateSoundEffect(soundEffectId));
        }
    }

    //SOUNDEFFECT-003: 효과음 태그 조회
    @GetMapping("/soundeffect/tag")
    public Result searchSoundEffectTag() {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectTagService.getAll());
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-005: 효과음 유튜브 구간 검색
    @GetMapping("/soundeffect/youtube")
    public ResponseEntity<byte[]> searchSoundEffectByYouTude(
            @RequestParam String url,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            return new ResponseEntity<>(youtubeDownloadService.getYoutudeAudio(url, from, to), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/soundeffect/{soundEffectId}/relative")
    public Result searchSoundEffectRelative(@PathVariable Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchRelativeSoundEffects(soundEffectId));
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }
}
