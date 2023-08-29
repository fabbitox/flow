package edu.pnu.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
class WebSocketHandler extends TextWebSocketHandler {
	private static Map<String, WebSocketSession> map = new HashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("user is conntected![" + session.getId() + "]");
		map.put(session.getId(), session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("user is disconntected![" + session.getId() + "]");
		map.remove(session.getId());
	}

	public static void sendData(String sendMessage) {
		Set<String> keys = map.keySet();
		System.out.println(String.format("==>%s[%d]", sendMessage, keys.size()));
		synchronized (map) { // 블럭안에 코드를 수행하는 동안 map 객체에 대한 다른 스레드의 접근을 방지한다.
			for (String key : keys) {
				WebSocketSession ws = map.get(key);
				try {
					ws.sendMessage(new TextMessage(sendMessage));
				} catch (IOException e) {
				}
			}
		}
	}
}

// 설정한 주기에 따라 자동으로 실행되어야 하는 경우 설정
@Component
class Scheduler {
	static int id = 1;
	@Value("${flow.file-save-path}")
	private String saveFilePath;
	@Value("${kma.auth-key}")
	private String authKey;
	private String[] stns = new String[] { "762", "764", "768" };
	@Value("${flask.url}")
	private String flaskUrl;
	private String[] filenames = new String[3];

//	@Scheduled(fixedDelay = 5000)			// scheduler 끝나는 시간 기준으로 1000 간격으로 실행
//	@Scheduled(fixedRate = 60000) // scheduler 시작하는 시간 기준으로 1000 간격으로 실행
//  @Scheduled(cron = "*/1 * * * * *")	// crontab에 따라 1초마다 실행
	public void fixedDelayTask() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		String tm2 = now.format(formatter);
		String tm1 = now.minusHours(12).format(formatter);
		for (int i = 0; i < 3; i++) {
			String stn = stns[i];
			String url = String.format(
					"https://apihub.kma.go.kr/api/typ01/cgi-bin/url/nph-aws2_min?tm1=%s&tm2=%s&stn=%s&disp=1&help=2&authKey=%s",
					tm1, tm2, stn, authKey);
			try {
				String filename = saveFilePath + stn + "-" + tm2 + ".csv";
				filenames[i] = filename;
				downloadFile(url, filename);
			} catch (IOException e) {
				System.out.println("파일 다운로드 중 오류가 발생했습니다: " + e.getMessage());
			}
		}
		String url = flaskUrl + "/aws/update";
		File file1 = new File(filenames[0]);
		File file2 = new File(filenames[1]);
		File file3 = new File(filenames[2]);

		WebClient webClient = WebClient.builder().baseUrl(url).build();

		// 멀티파트 요청 본문 데이터
		MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
		requestBody.add("file1", new FileSystemResource(file1));
		requestBody.add("file2", new FileSystemResource(file2));
		requestBody.add("file3", new FileSystemResource(file3));

		// POST 요청을 보내고 응답을 Mono로 받음
		Mono<String> responseMono = webClient.post().uri("").contentType(MediaType.MULTIPART_FORM_DATA)
				.body(BodyInserters.fromMultipartData(requestBody)).retrieve().bodyToMono(String.class);

		// 응답 처리
		responseMono.subscribe(responseBody -> {
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				WebSocketHandler.sendData(objectMapper.writeValueAsString(responseBody));
			} catch (JsonProcessingException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}, error -> System.err.println("Error: " + error.getMessage()));
	}

	public static void downloadFile(String url, String saveFilePath) throws IOException {
		URL fileUrl = new URL(url); // 다운로드할 파일의 URL 생성
		URLConnection connection = fileUrl.openConnection(); // URL에 대한 연결 열기 및 URLConnection 객체 생성

		InputStream inputStream = connection.getInputStream(); // 연결된 URL로부터 데이터를 읽어오기 위한 입력 스트림 생성
		FileOutputStream outputStream = new FileOutputStream(saveFilePath); // 지정된 파일 경로에 파일을 쓰기 위한 출력 스트림 생성

		byte[] buffer = new byte[1024]; // 데이터를 읽어올 버퍼 생성
		int bytesRead; // 읽어온 바이트 수를 저장하는 변수

		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead); // 버퍼에 있는 데이터를 파일에 기록
		}

		outputStream.close(); // 출력 스트림 닫기
		inputStream.close(); // 입력 스트림 닫기
	}
}

// 클라이언트에서 연결할 웹소켓 설정 : ws://localhost:8080/pushservice
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	private final WebSocketHandler webSocketHandler;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketHandler, "pushservice").setAllowedOrigins("*");
	}
}