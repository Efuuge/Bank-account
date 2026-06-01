import java.io.*;
import java.net.*;
import java.util.*;

public class AIAssistant {
    private List<String[]> history = new ArrayList<>();

    private String loadApiKey() {
        try {
            BufferedReader r = new BufferedReader(new FileReader("api_key.txt"));
            String key = r.readLine().trim();
            r.close();
            return key;
        } catch (Exception e) {
            return null;
        }
    }

    public void startConversation() {
        String apiKey = loadApiKey();
        if (apiKey == null) {
            System.out.println(Lang.get("ai_key_missing"));
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println(Lang.get("ai_greeting"));
        System.out.println();

        while (true) {
            System.out.print(Lang.get("ai_you"));
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("выход") || input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("chiqish")) {
                break;
            }

            history.add(new String[]{"user", input});
            System.out.print(Lang.get("ai_prefix"));
            String response = sendMessage(apiKey);
            history.add(new String[]{"assistant", response});
            System.out.println(response);
            System.out.println();
        }
    }

    private String sendMessage(String apiKey) {
        try {
            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + apiKey);
            conn.setDoOutput(true);
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(30000);

            String system = "You are a helpful banking assistant. Answer questions about banking and finance. Reply in the same language the user writes in.";

            StringBuilder msgs = new StringBuilder("[");
            msgs.append("{\"role\":\"system\",\"content\":\"").append(escape(system)).append("\"}");
            for (String[] msg : history) {
                msgs.append(",{\"role\":\"").append(msg[0]).append("\",\"content\":\"").append(escape(msg[1])).append("\"}");
            }
            msgs.append("]");

            String body = "{\"model\":\"llama3-8b-8192\",\"max_tokens\":1000,\"messages\":" + msgs + "}";

            OutputStream os = conn.getOutputStream();
            os.write(body.getBytes("UTF-8"));
            os.close();

            int code = conn.getResponseCode();
            InputStream is = code == 200 ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder resp = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) resp.append(line);
            br.close();

            if (code != 200) return "Error " + code + ": " + resp;
            return extractText(resp.toString());

        } catch (Exception e) {
            return "Connection error: " + e.getMessage();
        }
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    private String extractText(String json) {
        String marker = "\"content\":\"";
        int start = json.indexOf(marker);
        if (start == -1) return "Could not parse response";
        start += marker.length();
        StringBuilder result = new StringBuilder();
        boolean esc = false;
        for (int i = start; i < json.length(); i++) {
            char c = json.charAt(i);
            if (esc) {
                if (c == 'n') result.append('\n');
                else if (c == 't') result.append('\t');
                else if (c == '"') result.append('"');
                else if (c == '\\') result.append('\\');
                else result.append(c);
                esc = false;
            } else if (c == '\\') {
                esc = true;
            } else if (c == '"') {
                break;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}