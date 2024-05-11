package capstone.ses.controller;

import capstone.ses.dto.soundeffect.SoundEffectCondition;
import capstone.ses.dto.soundeffect.SoundEffectDto;
import capstone.ses.dto.soundeffect.SoundEffectForm;
import capstone.ses.dto.soundeffect.SoundEffectPaginationDto;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.repository.SoundEffectTypeRepository;
import capstone.ses.service.SoundEffectService;
import capstone.ses.service.SoundEffectTagService;
import capstone.ses.service.YoutubeDownloadService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    //SOUNDEFFECT-001: 효과음 리스트 조회
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

            SoundEffectPaginationDto soundEffectPaginationDto = soundEffectService.searchSoundEffects(SoundEffectCondition.builder()
                    .fromLength(fromLength)
                    .toLength(toLength)
                    .sampleRate(sampleRate)
                    .bitDepth(bitDepth)
                    .channels(channels)
                    .soundEffectTagIds(soundEffectTagId)
                    .build(), pageable);

            return new Result(ResultCode.SUCCESS, soundEffectPaginationDto);
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
    public Result updateSoundEffect(@PathVariable Long soundEffectId, @RequestBody @Valid SoundEffectForm soundEffectForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(ResultCode.FAIL, bindingResult.getFieldError().getDefaultMessage());
        }

        try {
            soundEffectService.updateSoundEffect(soundEffectId, soundEffectForm);
            return new Result(ResultCode.SUCCESS, null);
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
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

    //SOUNDEFFECT-009: 유사한 효과음 조회
    @GetMapping("/soundeffect/{soundEffectId}/relative")
    public Result searchSoundEffectRelative(@PathVariable Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchRelativeSoundEffects(soundEffectId));
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }
}
