package lol.sylvie.petprotect;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = PetProtect.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue PREVENT_PET_DAMAGE = BUILDER
            .define("preventPetDamage", true);
    private static final ModConfigSpec.BooleanValue PREVENT_PET_ATTACK = BUILDER
            .define("preventPetAttack", true);
    private static final ModConfigSpec.BooleanValue ALLOW_OWNER_DAMAGE = BUILDER
            .define("allowOwnerDamage", false);
    private static final ModConfigSpec.BooleanValue ALLOW_PLAYER_DAMAGE = BUILDER
            .define("allowPlayerDamage", false);
    private static final ModConfigSpec.BooleanValue IGNORE_CREATIVE = BUILDER
            .define("ignoreCreative", true);


    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean preventPetDamage;
    public static boolean preventPetAttack;
    public static boolean allowOwnerDamage;
    public static boolean allowPlayerDamage;
    public static boolean ignoreCreative;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        preventPetDamage = PREVENT_PET_DAMAGE.get();
        preventPetAttack = PREVENT_PET_ATTACK.get();
        allowOwnerDamage = ALLOW_OWNER_DAMAGE.get();
        allowPlayerDamage = ALLOW_PLAYER_DAMAGE.get();
        ignoreCreative = IGNORE_CREATIVE.get();
    }
}