package uz.pdp.transactionreports.service.telegramService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import uz.pdp.transactionreports.dto.TelegramResultDto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Service
public class BotService {
    private static final String BASE_URL = "https://api.telegram.org/bot";
    private static final String FILE_URL = "https://api.telegram.org/file/bot";
    private static final RestTemplate restTemplate = new RestTemplate();

    private final String token;

    public BotService(@Value("${telegram.token}") String token) {
        this.token = token;
        System.out.println(this.token);
    }

    public <T extends Serializable, Method extends BotApiMethod<T>> void send(Method method) {
        restTemplate.postForObject(BASE_URL + token + "/" + method.getMethod(), method, TelegramResultDto.class);
    }

    public void sendFile(Long chatId, String filePath) {
        RestTemplate restTemplate = new RestTemplate();
        String url = BASE_URL + token + "/sendDocument";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("chat_id", chatId);
        body.add("document", new FileSystemResource(filePath));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println("File uploaded successfully!");
    }

    public void send(SendPhoto method) {
        Map<String, String> map = new HashMap<>();
        map.put("chat_id", method.getChatId());
        map.put("file", method.getFile().getAttachName());
        map.put("caption", method.getCaption());
        HttpEntity<?> requestEntity = new HttpEntity<>(map);
        restTemplate.exchange(BASE_URL + token + "/" + method.getMethod(), HttpMethod.POST, requestEntity, Void.class);
    }


}
