package capstone.ses.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class YoutubeDownloadService {

    public byte[] getYoutudeAudio(String url, String startTime, String endTime) {
        // 파이썬 서버의 URL
        String pythonServerUrl = "http://localhost:8000/download/?url=" + url
                + "&start_time=" + startTime + "&end_time=" + endTime;

        // HTTP GET 요청을 보내서 오디오 파일을 받아옴
        RestTemplate restTemplate = new RestTemplate();
        byte[] audioBytes = restTemplate.getForObject(pythonServerUrl, byte[].class);

        // 받은 오디오 파일을 클라이언트에게 반환
        return audioBytes;
    }
}
