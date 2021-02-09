package api;

import internals.RSVarbitInfo;
import internals.RSVarps;

public class Varbit {

    public static int get(int ID) {
        RSVarbitInfo composition = RSVarbitInfo.info(ID);

        if (composition != null) {
            int startBit = composition.startBit();
            int endBit = composition.endBit();
            int baseVar = composition.baseVar();

            if (startBit != 0 || endBit != 0 || baseVar != 0) {
                int value = RSVarps.varpsMain()[baseVar];
                int mask = (1 << ((endBit - startBit) + 1)) - 1;
                return (value >> startBit) & mask;
            }
        }

        return -1;
    }
}
