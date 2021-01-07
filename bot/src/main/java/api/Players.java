package api;

import internals.RSClient;
import internals.RSPlayer;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Players {

    public static Stream<RSPlayer> getAll() {
        Object[] refs = RSClient.playersRefArray();

        return Arrays.stream(RSClient.playersIndices())
                .limit(RSClient.playerCount())
                .mapToObj(index -> refs[index] != null ? new RSPlayer(refs[index]) : null);
    }

    public static Stream<RSPlayer> getAll(Predicate<RSPlayer> predicate) {
        return getAll().filter(predicate);
    }

    public static RSPlayer get(String name) {
        return getAll(player -> player.name().matches(name))
                .findAny()
                .orElse(null);
    }

    public static RSPlayer get(int index) {
        Object[] refs = RSClient.NPCRefArray();
        return (index > -1 && refs[index] != null) ? new RSPlayer(refs[index]) : null;
    }

    public static RSPlayer localPlayer() {
        return RSClient.localPlayer();
    }
}
