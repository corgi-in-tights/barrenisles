package ca.thecorgi.barrenisles.client.gui;



import ca.thecorgi.barrenisles.utils.registry.GUIRegistry;
import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerContext;

public class GoldVaseGUI extends SyncedGuiDescription {
    public static final int INVENTORY_SIZE = 5;
    public final ScreenHandlerContext context;

    public GoldVaseGUI(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(GUIRegistry.GOLD_VASE_GUI, syncId, inventory, getBlockInventory(context, INVENTORY_SIZE), getBlockPropertyDelegate(context));
        this.context = context;

        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(170, 150);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot itemSlot = WItemSlot.of(blockInventory,0);
        root.add(itemSlot, 4, 1);
        WItemSlot itemSlot2 = WItemSlot.of(blockInventory,1);
        root.add(itemSlot2, 3, 1);
        WItemSlot itemSlot3 = WItemSlot.of(blockInventory,2);
        root.add(itemSlot3, 5, 1);

        WItemSlot itemSlot4 = WItemSlot.of(blockInventory,3);
        root.add(itemSlot4, 6, 1);
        WItemSlot itemSlot5 = WItemSlot.of(blockInventory,4);
        root.add(itemSlot5, 2, 1);

        root.add(this.createPlayerInventoryPanel(), 0, 3);

        root.validate(this);
    }
}