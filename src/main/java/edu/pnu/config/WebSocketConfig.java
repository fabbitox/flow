package edu.pnu.config;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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
		synchronized (map) { // 블럭 안의 코드를 수행하는 동안 map 객체에 대한 다른 스레드의 접근을 방지
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
	@Value("${flask.url}")
	private String flaskUrl;
	@Value("${water.url}")
	private String waterUrl;
	@Value("${rain.url}")
	private String rainUrl;
	private String[] obscds = new String[] {"4002605", "4009640"};
	
//	private final PredictService predictService;
//	private final PredResultService predResultService;
	private DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyyMMdd");
	private DateTimeFormatter ymdhm = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
	private LocalDateTime reqTime;
	private Double[][] waterLevels = new Double[6][2];
	private Double rain;
	private String pastYmdh;
	private Double[] results = new Double[3];

	@Scheduled(fixedRate = 3600000)
	public void task() {
		reqTime = LocalDateTime.now();
		try {
			getWater(0);
			getWater(1);
			getRain();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(waterLevels[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println(rain);
	}

	private void requestPred(LocalDateTime base) {
		String url = flaskUrl + "/aws/update";
		WebClient webClient = WebClient.builder().baseUrl(url).build();
	}

	private void getWater(int i) throws IOException, InterruptedException {
		String url = String.format(waterUrl, obscds[i], reqTime.minusMinutes(10).minusHours(6).format(ymd));
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode data = objectMapper.readTree(response.body()).get("list");
			int startIndex = data.size() - 6;
			for (int j = 0; j < 6; j++) {
				waterLevels[j][i] = data.get(startIndex + j).get("wl").asDouble();
			}
			pastYmdh = data.get(startIndex).get("ymdh").asText();
		} else {
			System.out.println("오류 응답: " + response.statusCode());
			return;
		}
	}
	
	private void getRain() throws IOException, InterruptedException {
		String url = String.format(rainUrl, reqTime.minusMinutes(10).minusHours(6).format(ymd));
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode data = objectMapper.readTree(response.body()).get("list");
			rain = data.get(data.size() - 1).get("rf").asDouble();
		} else {
			System.out.println("오류 응답: " + response.statusCode());
			return;
		}
	}
	
//	@Scheduled(fixedRate = 3000)
	private void pushPredict() {
		List<Double> values = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
		try {
			for (int i = 0; i < 6; i++) {
				values.add(waterLevels[i][1]);
			}
			for (Double result: results) {
				values.add(result);
			}
			String pastStart = pastYmdh + "00";
			LocalDate datePart = LocalDate.parse(pastStart.substring(0, 8), ymd);
			int hour = Integer.parseInt(pastStart.substring(8, 10));
			if (hour == 24) {
				datePart = datePart.plusDays(1);
				hour = 0;
			}
			LocalDateTime start = datePart.atStartOfDay().withHour(hour);
			String predStart = start.plusHours(6).format(ymdhm);
			WaterData wd = new WaterData(values, pastStart, predStart, reqTime.format(ymdhm));
			WebSocketHandler.sendData(objectMapper.writeValueAsString(wd));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	@JsonProperty("reqTime")
	private String reqTime;
	
	public WaterData() {}
	public WaterData(List<Double> list, String pastStart, String predStart, String reqTime) {
		this.list = list;
		this.pastStart = pastStart;
		this.predStart = predStart;
		this.reqTime = reqTime;
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