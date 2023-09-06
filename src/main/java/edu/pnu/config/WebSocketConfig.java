package edu.pnu.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.PredResult;
import edu.pnu.domain.Predict;
import edu.pnu.service.PredResultService;
import edu.pnu.service.PredictService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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
@RequiredArgsConstructor
class Scheduler {
	@Value("${flow.file-save-path}")
	private String saveFilePath;
	@Value("${fcst.url}")
	private String fcstUrl;
	@Value("${fcst.service-key}")
	private String serviceKey;
	@Value("${flask.url}")
	private String flaskUrl;
	private String[] xys = new String[] { "64", "82", "65", "85", "67", "79" };
	private String[] names = new String[] {"섬진강댐", "호암교", "요천대교"};
	private String[] filenames = new String[3];
	private final PredictService predictService;
	private final PredResultService predResultService;
	private String waterfile;
	private LocalDateTime refTime;
	private LocalDateTime base;
	private DateTimeFormatter ymdhr;

//	@Scheduled(fixedDelay = 5000)			// scheduler 끝나는 시간 기준으로 1000 간격으로 실행
	@Scheduled(fixedRate = 180000) // scheduler 시작하는 시간 기준으로 1000 간격으로 실행
//  @Scheduled(cron = "*/1 * * * * *")	// crontab에 따라 1초마다 실행
	public void task() {
		refTime = LocalDateTime.now();
		int minute = refTime.getMinute();
		if (minute < 45) {
			base = refTime.minusMinutes(minute + 30);
		} else {
			base = refTime.minusMinutes(minute - 30);
		}
		ymdhr = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		DateTimeFormatter ymdh = DateTimeFormatter.ofPattern("yyyyMMddHH");
		DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyyMMdd");
		DateTimeFormatter hhmm = DateTimeFormatter.ofPattern("HHmm");
		String baseDate = base.format(ymd);
		String baseTime = base.format(hhmm);
		for (int i = 0; i < 3; i++) {
			String url = String.format(fcstUrl, serviceKey, baseDate, baseTime, xys[i * 2], xys[i * 2 + 1]);
			filenames[i] = saveFilePath + names[i] + refTime.format(ymdhr) + ".json";
			downloadFile(url, filenames[i]);
		}
		waterfile = saveFilePath + refTime.format(ymdh) + ".json";
		downloadFile(String.format("http://www.wamis.go.kr:8080/wamis/openapi/wkw/wl_hrdata?obscd=4009638&startdt=%s&output=json", refTime.minusHours(7).format(ymd)), waterfile);
//		Integer id = predictService.insert(new Predict(refTime)).getIdpredict();
//		pipe(id);
		pipe();
	}

//	private void pipe(Integer id) {
	private void pipe() {
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
				JsonNode responseJson = objectMapper.readTree(responseBody);
				JsonNode resultNode = responseJson.get("result");
				List<Double> values = new ArrayList<>();
				values.addAll(getLastSix());
				for (int i = 0; i < resultNode.size(); i++) {
				    JsonNode node = resultNode.get(i);
			        double wl = node.asDouble();
					values.add(wl);
//					predResultService.insert(new PredResult(id, i + 1, wl));
			    }
				WaterData wd = new WaterData(values, refTime.minusHours(5).minusMinutes(refTime.getMinute()).format(ymdhr), base.plusMinutes(30).format(ymdhr));
				WebSocketHandler.sendData(objectMapper.writeValueAsString(wd));
			} catch (IOException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}, error -> System.err.println("Error: " + error.getMessage()));
	}

	public void downloadFile(String url, String saveFilePath) {
		try {
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

			System.out.println("다운로드 완료: " + saveFilePath);
		} catch (IOException e) {
			System.out.println("파일 다운로드 중 오류가 발생했습니다: " + e.getMessage());
		}
	}
	
	public List<Double> getLastSix() throws IOException {
        // ObjectMapper 초기화
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(waterfile));

        // "list" 항목에서 마지막 6개 요소 가져오기
        JsonNode listNode = jsonNode.get("list");
        int size = listNode.size();
        List<Double> lastSixItems = new ArrayList<>();
        for (int i = size - 6; i < size; i++) {
            lastSixItems.add(listNode.get(i).get("wl").asDouble());
        }
        return lastSixItems;
    }
}

@Getter
@Setter
class WaterData {
	@JsonProperty("list")
	private List<Double> list;
	@JsonProperty("pastStart")
	private String pastStart;
	@JsonProperty("predStart")
	private String predStart;
	
	public WaterData() {}
	public WaterData(List<Double> list, String pastStart, String predStart) {
		this.list = list;
		this.pastStart = pastStart;
		this.predStart = predStart;
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