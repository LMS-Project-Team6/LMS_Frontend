package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import vo.Book;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BookHttp {
    private static final String BASE_API_URL = "http://43.200.55.59:9090/LMS-Backend/book";

    public static List<Book> findAllAvailability() throws Exception {
        String apiUrl = BASE_API_URL + "/findAllAvailability";
        return fetchBookList(apiUrl);
    }

    public static boolean deleteBook(String bookId) throws Exception {
        String apiUrl = BASE_API_URL + "/deleteBook?bookId=" + URLEncoder.encode(bookId, "UTF-8");
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("DELETE");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int responseCode = conn.getResponseCode();
        conn.disconnect();

        return responseCode == HttpURLConnection.HTTP_OK;
    }

    public static boolean updateBook(Book book) throws Exception {
        String apiUrl = BASE_API_URL + "/updateBook";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String params =
                "bookId=" + URLEncoder.encode(book.getBookId(), "UTF-8") +
                        "&bookTitle=" + URLEncoder.encode(book.getBookTitle(), "UTF-8") +
                        "&bookWriter=" + URLEncoder.encode(book.getBookWriter(), "UTF-8") +
                        "&bookPublisher=" + URLEncoder.encode(book.getBookPublisher(), "UTF-8") +
                        "&bookCNum=" + URLEncoder.encode(book.getBookCNum(), "UTF-8") +
                        "&bookIntrd=" + URLEncoder.encode(book.getBookIntrd(), "UTF-8");

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
            writer.write(params);
            writer.flush();
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorMsg = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorMsg.append(line);
                }
                throw new RuntimeException(errorMsg.toString());
            }
        }
    }

    public static boolean addBook(Book book) throws Exception {
        String apiUrl = BASE_API_URL + "/addBook";
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        // 파라미터 구성
        String params =
                "bookId=" + URLEncoder.encode(book.getBookId(), "UTF-8") +
                        "&bookTitle=" + URLEncoder.encode(book.getBookTitle(), "UTF-8") +
                        "&bookWriter=" + URLEncoder.encode(book.getBookWriter(), "UTF-8") +
                        "&bookPublisher=" + URLEncoder.encode(book.getBookPublisher(), "UTF-8") +
                        "&bookCNum=" + URLEncoder.encode(book.getBookCNum(), "UTF-8") +
                        "&bookIntrd=" + URLEncoder.encode(book.getBookIntrd(), "UTF-8") +
                        "&lendNY=" + URLEncoder.encode(String.valueOf(book.getLendNY()), "UTF-8");

        // 데이터 전송
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"))) {
            writer.write(params);
            writer.flush();
        }

        // 응답 처리
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return true;
        } else {
            // ⚠️ 에러 메시지 내용 수집
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                StringBuilder errorMsg = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    errorMsg.append(line);
                }
                throw new RuntimeException(errorMsg.toString());
            }
        }
    }

    public static Book getBookDetails(String bookId) throws IOException {
        String encodedId = URLEncoder.encode(bookId, StandardCharsets.UTF_8);
        String url = BASE_API_URL + "/getBookDetails?bookId=" + encodedId;

        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(response.toString(), Book.class);
            }
        } else {
            throw new IOException("API 요청 실패: " + responseCode);
        }
    }

    public static List<Book> findAll() throws Exception {
        String apiUrl = BASE_API_URL + "/findAll";
        return fetchBookList(apiUrl);
    }

    public static List<Book> searchBooks(String category, String keyword) throws IOException {
        String encodedKeyword = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
        String url = BASE_API_URL + "/searchBooks?searchType=" + category + "&searchValue=" + encodedKeyword;
        try {
            return fetchBookList(url); // 여기가 핵심
        } catch (Exception e) {
            throw new IOException("서버 오류 또는 파싱 실패", e);
        }
    }

    private static List<Book> fetchBookList(String apiUrl) throws Exception {
        List<Book> bookList = new ArrayList<>();

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

            Book book = new Book();
            book.setBookId(obj.optString("bookId"));
            book.setBookTitle(obj.optString("bookTitle"));
            book.setBookWriter(obj.optString("bookWriter"));
            book.setBookPublisher(obj.optString("bookPublisher"));
            book.setBookCNum(obj.optString("bookCNum"));
            book.setLendNY(obj.optInt("lendNY", 0));
            book.setCreatedDate(obj.optString("createdDate"));
            book.setModifiedDate(obj.optString("modifiedDate"));

            bookList.add(book);
        }

        return bookList;
    }
}
