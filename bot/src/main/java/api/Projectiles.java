package api;

import internals.RSClient;
import internals.RSNode;
import internals.RSNodeDeque;
import internals.RSProjectile;

import java.util.stream.Stream;

import static utils.Reflection.getRef;

public class Projectiles {

    public static Stream<RSProjectile> getAll() {
        Object projectilesRef = getRef(RSClient.CLASS_NAME, "projectiles", null);
        Stream.Builder<RSProjectile> builder = Stream.builder();

        if (projectilesRef != null) {
            RSNodeDeque deque = new RSNodeDeque(projectilesRef);

            RSNode head = deque.head();
            if (head != null) {
                for (RSNode node = head.next(); (node != null) && (node.ref != head.ref); node = node.next()) {
                    builder.accept((RSProjectile) node);
                }
            }
        }

        return builder.build();
    }
}
