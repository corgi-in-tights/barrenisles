package ca.thecorgi.barrenisles.utils.registry;

import ca.thecorgi.barrenisles.BarrenIsles;
import ca.thecorgi.barrenisles.client.gui.GoldVaseGUI;
import ca.thecorgi.barrenisles.client.gui.VaseGUI;
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class GUIRegistry {
    public static ScreenHandlerType<VaseGUI> VASE_GUI;
    public static ScreenHandlerType<GoldVaseGUI> GOLD_VASE_GUI;

    public static void register() {
        VASE_GUI = ScreenHandlerRegistry.registerExtended(BarrenIsles.id("vase_gui"), (syncId, inventory, buf) -> new VaseGUI(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, buf.readBlockPos())));
        GOLD_VASE_GUI = ScreenHandlerRegistry.registerExtended(BarrenIsles.id("gold_vase_gui"), (syncId, inventory, buf) -> new GoldVaseGUI(syncId, inventory, ScreenHandlerContext.create(inventory.player.world, buf.readBlockPos())));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ScreenRegistry.<VaseGUI, CottonInventoryScreen<VaseGUI>>register(VASE_GUI, (gui, inventory, title) -> new CottonInventoryScreen<>(gui, inventory.player, title));
        ScreenRegistry.<GoldVaseGUI, CottonInventoryScreen<GoldVaseGUI>>register(GOLD_VASE_GUI, (gui, inventory, title) -> new CottonInventoryScreen<>(gui, inventory.player, title));
    }

}
