package lol.sylvie.petprotect.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import lol.sylvie.petprotect.Config;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerSweepAttackMixin
{
    @WrapOperation(method="Lnet/minecraft/world/entity/player/Player;attack(Lnet/minecraft/world/entity/Entity;)V", at = @At(value= "INVOKE",target="Lnet/minecraft/world/entity/player/Player;isAlliedTo(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean onAttackAllied(Player instance, Entity entity, Operation<Boolean> original)
    {
        if (!Config.preventPetDamage)  return original.call(instance, entity);

        if (instance.isSpectator())  return original.call(instance, entity);
        if (Config.ignoreCreative && instance.isCreative())  return original.call(instance, entity);

        if (entity instanceof TamableAnimal tameable) {
            if (tameable.getOwner() == null)  return original.call(instance, entity);

            if (!(tameable.isOwnedBy(instance) && Config.allowOwnerDamage)) {
                return true;
            }
        }
        return original.call(instance, entity);
    }
}
