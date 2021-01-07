package api;

import internals.RSClient;

import java.util.stream.Stream;

import static utils.Reflection.getInt;

public class Chatbox {

    public static int chatMode() {
        return getInt(RSClient.CLASS_NAME, "publicChatMode", null);
    }

    public static Stream<String> messages() {
        throw new UnsupportedOperationException("not implemented");
    }
}
