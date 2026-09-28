package mod.emt.hyxcate.util.helpers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;

public class AttributeHelper {
    public static float getAttributeValue(EntityLivingBase entity, IAttribute attribute) {
        IAttributeInstance instance = entity.getEntityAttribute(attribute);
        if (instance != null) {
            return (float) instance.getAttributeValue();
        }
        return 0.0F;
    }
}
