import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Jackson is working: " + mapper.toString());
    }
}
