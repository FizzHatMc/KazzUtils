package de.kazzutils.mixin;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.spongepowered.asm.mixin.Mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BooleanSupplier;

@Mixin({EntityLivingBase.class})
public class RenderLivingEntityHelper {

    private static final Map<EntityLivingBase, Integer> entityColorMap = new HashMap<>();
    private static final Map<EntityLivingBase, BooleanSupplier> entityColorCondition = new HashMap<>();
    private static final Map<EntityLivingBase, BooleanSupplier> entityNoHurTimeCondition = new HashMap<>();

    public static <T extends EntityLivingBase> void removeEntityColor(T entity) {
        entityColorMap.remove(entity);
        entityColorCondition.remove(entity);
    }

    public static <T extends EntityLivingBase> void setEntityColor(T entity, int color, BooleanSupplier condition) {
        entityColorMap.put(entity, color);
        entityColorCondition.put(entity, condition);
    }

    public static <T extends EntityLivingBase> void setNoHurtTime(T entity, BooleanSupplier condition) {
        entityNoHurTimeCondition.put(entity, condition);
    }

    public static <T extends EntityLivingBase> void setEntityColorWithNoHurtTime(T entity, int color, BooleanSupplier condition) {
        setEntityColor(entity, color, condition);
        setNoHurtTime(entity, condition);
    }

    public static <T extends EntityLivingBase> void removeNoHurtTime(T entity) {
        entityNoHurTimeCondition.remove(entity);
    }

    public static <T extends EntityLivingBase> void removeCustomRender(T entity) {
        removeEntityColor(entity);
        removeNoHurtTime(entity);
    }

    public static <T extends EntityLivingBase> int internalSetColorMultiplier(T entity) {
        if (entityColorMap.containsKey(entity)) {
            BooleanSupplier condition = entityColorCondition.get(entity);
            if (condition != null && condition.getAsBoolean()) {
                return entityColorMap.get(entity);
            }
        }
        return 0;
    }

    public static <T extends EntityLivingBase> int internalChangeHurtTime(T entity) {
        BooleanSupplier condition = entityNoHurTimeCondition.get(entity);
        if (condition != null && condition.getAsBoolean()) {
            return 0;
        }
        return entity.hurtTime;
    }

    // Example EntityLivingBase (replace with your actual class)
    public static class EntityLivingBase {
        public int hurtTime;
    }

    public static void main(String[] args) {
        EntityLivingBase entity1 = new EntityLivingBase();
        EntityLivingBase entity2 = new EntityLivingBase();

        setEntityColor(entity1, 0xFF0000, () -> true); // Red
        setNoHurtTime(entity2, () -> false);

        System.out.println("Entity1 Color Multiplier: " + internalSetColorMultiplier(entity1)); // Output: -65536 (0xFF0000 in decimal)
        System.out.println("Entity2 Hurt Time: " + internalChangeHurtTime(entity2)); // Output: 0

        entity2.hurtTime = 10;
        System.out.println("Entity2 Hurt Time after change: " + internalChangeHurtTime(entity2)); //Output: 10

        removeEntityColor(entity1);
        System.out.println("Entity1 Color Multiplier after removal: " + internalSetColorMultiplier(entity1)); // Output: 0
    }
}
