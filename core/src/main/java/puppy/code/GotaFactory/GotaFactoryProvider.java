package puppy.code.GotaFactory;

import java.util.HashMap;
import java.util.Map;

public class GotaFactoryProvider {
    private static final Map<TipoGota, GotaFactory> factories = new HashMap<>();

    static {
        factories.put(TipoGota.BUENA, new GotaBuenaFactory());
        factories.put(TipoGota.MALA, new GotaMalaFactory());
        factories.put(TipoGota.ESPECIAL, new GotaEspecialFactory());
        factories.put(TipoGota.VIDA_EXTRA, new GotaVidaExtraFactory());
    }

    public static GotaFactory getFactory(TipoGota tipo) {
        return factories.get(tipo);
    }
}
