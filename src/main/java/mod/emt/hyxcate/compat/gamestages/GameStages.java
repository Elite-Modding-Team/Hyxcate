package mod.emt.hyxcate.compat.gamestages;

import mod.emt.hyxcate.config.HyxcateConfig;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class GameStages {
    public static boolean checkGameStageLunarEvents(World world) {
        if (!isLoadedAndEnabled()) return true;
        boolean stage = false;
        for (EntityPlayer player : world.playerEntities) {
            if (GameStageHelper.hasStage(player, "hyxcateEventsLunar")) {
                stage = true;
                break;
            }
        }
        return stage;
    }

    public static boolean checkGameStageSolarEvents(World world) {
        if (!isLoadedAndEnabled()) return true;
        boolean stage = false;
        for (EntityPlayer player : world.playerEntities) {
            if (GameStageHelper.hasStage(player, "hyxcateEventsSolar")) {
                stage = true;
                break;
            }
        }
        return stage;
    }

    public static boolean checkGameStageMeteorEvents(EntityPlayer player) {
        if (!isLoadedAndEnabled()) return true;
        return GameStageHelper.hasStage(player, "hyxcateEventsMeteor");
    }

    public static boolean checkGameStageFallingStarEvents(EntityPlayer player) {
        if (!isLoadedAndEnabled()) return true;
        return GameStageHelper.hasStage(player, "hyxcateEventsFallingStar");
    }

    private static boolean isLoadedAndEnabled() {
        return HyxcateConfig.MOD_INTEGRATION.gameStagesIntegration && Loader.isModLoaded("gamestages");
    }
}
