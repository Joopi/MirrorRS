package api;

import internals.RSClient;
import internals.RSNPC;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class NPCs {
    public static Stream<RSNPC> getAll() {
        Object[] objects = RSClient.NPCRefArray();

        return Arrays.stream(RSClient.NPCIndices())
                .limit(RSClient.NPCCount())
                .mapToObj(i -> objects[i] != null ? new RSNPC(objects[i], i) : null);
    }

    public static Stream<RSNPC> getAll(Predicate<RSNPC> predicate) {
        return getAll()
                .filter(predicate);
    }

    public static RSNPC get(String npcName) {
        return getAll(npc -> npc.info().name().matches(npcName))
                .findAny()
                .orElse(null);
    }

    public static RSNPC get(int index) {
        Object[] refs = RSClient.NPCRefArray();
        return (index > -1 && refs[index] != null) ? new RSNPC(refs[index], index) : null;
    }
}
