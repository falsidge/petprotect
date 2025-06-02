package lol.sylvie.petprotect;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PetProtect.MOD_ID)
public class PetProtect{
    public static final String MOD_ID = "petprotect";
    public static Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public PetProtect(IEventBus modEventBus, ModContainer modContainer){
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class EventHandler {
        @SubscribeEvent
        public static void onPlayerAttack(AttackEntityEvent event)
        {
            if (!Config.preventPetDamage) return;
            if (event.getEntity() instanceof Player player)
            {
                if (player.isSpectator()) return;
                if (Config.ignoreCreative && player.isCreative()) return;
            }

            if (event.getTarget() instanceof TamableAnimal tameable) {
                if (tameable.getOwner() == null) return;
                if (event.getEntity() instanceof Player  && !Config.allowPlayerDamage)
                {
                    event.setCanceled(true);
                    return;
                }
                if ((tameable.isOwnedBy(event.getEntity()) && !Config.allowOwnerDamage)) {
                    event.setCanceled(true);
                    return;
                }
            }
            if (event.getTarget() instanceof OwnableEntity ownable) {
                if (ownable.getOwner() == null) return;
                if (event.getEntity()  instanceof Player  && !Config.allowPlayerDamage)
                {
                    event.setCanceled(true);
                    return;
                }
                if ((ownable.getOwner().is(event.getEntity()) && !Config.allowOwnerDamage)) {
                    event.setCanceled(true);
                }
            }
        }
        @SubscribeEvent
        public static void OnEntityHurt(LivingDamageEvent.Pre event)
        {
            if (!Config.preventPetDamage) return;
            if (event.getSource().getEntity() instanceof Player player)
            {
                if (player.isSpectator()) return;
                if (Config.ignoreCreative && player.isCreative()) return;

            }

            if (event.getEntity() instanceof TamableAnimal tameable) {
                if (tameable.getOwner() == null) return;

                if (event.getSource().getEntity() instanceof Player && !Config.allowPlayerDamage)
                {
                    event.setNewDamage(0);
                    return;
                }

                if ((event.getSource().getEntity() instanceof LivingEntity living && (tameable.isOwnedBy(living) && !Config.allowOwnerDamage))) {
                    event.setNewDamage(0);
                }
            }
            if (event.getEntity() instanceof OwnableEntity ownable)
            {
                if (ownable.getOwner() == null) return;


                if (event.getSource().getEntity() instanceof Player && !Config.allowPlayerDamage)
                {
                    event.setNewDamage(0);
                    return;
                }

                if (event.getSource().getEntity() != null && (event.getSource().getEntity().is(ownable.getOwner()) && !Config.allowOwnerDamage))
                {
                    event.setNewDamage(0);
                }
            }
        }
    }
}
