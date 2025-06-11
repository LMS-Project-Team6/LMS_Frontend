package http;

import org.json.JSONArray;
import org.json.JSONObject;
import vo.Book;
import vo.Mem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MemHttp {
    private static final String BASE_API_URL = "http://43.200.55.59:9090/LMS-Backend/mem";

    public static List<Mem> searchMems(String category, String keyword) throws IOException {
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String url = BASE_API_URL + "/memSearch?category=" + category + "&keyword=" + encodedKeyword;
        try {
            return fetchMemList(url); // 여기가 핵심
        } catch (Exception e) {
            throw new IOException("서버 오류 또는 파싱 실패", e);
        }
    }

    public static List<Mem> findAll() throws Exception {
        String apiUrl = BASE_API_URL + "/findAll";
        return fetchMemList(apiUrl);
    }

    private static List<Mem> fetchMemList(String apiUrl) throws Exception {
        List<Mem> memList = new ArrayList<>();

        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();

        // JSON 파싱
        JSONArray arr = new JSONArray(sb.toString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            Mem mem = new Mem();
            mem.setMemId(obj.optString("memId"));
            mem.setMemName(obj.optString("memName"));
            mem.setMemEmail(obj.optString("memEmail"));
            mem.setMemBirth(obj.optString("memBirth"));
            mem.setMemPNum(obj.optString("memPNum"));
            mem.setCreatedDate(obj.optString("createdDate"));
            mem.setModifiedDate(obj.optString("modifiedDate"));

            memList.add(mem);
        }

        return memList;
    }
}
