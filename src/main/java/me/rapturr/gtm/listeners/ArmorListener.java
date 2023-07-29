package me.rapturr.gtm.listeners;

import me.rapturr.gtm.GTM;
import me.rapturr.gtm.events.ArmorEquipEvent;
import me.rapturr.gtm.utilities.ArmorType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.List;

public class ArmorListener implements Listener {

    private final List<String> blockedMaterials;

    /**
     * @param blockedMaterials
     */
    public ArmorListener(List<String> blockedMaterials) {
        this.blockedMaterials = blockedMaterials;
    }

    /**
    Event Priority is highest because other plugins might cancel the events before we check.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public final void inventoryClick(final InventoryClickEvent event) {

        HumanEntity whoClicked = event.getWhoClicked();

        Inventory inventory = event.getInventory();
        Inventory clickedInventory = event.getClickedInventory();

        boolean shift = false;
        boolean numberkey = false;

        InventoryAction action = event.getAction();
        ClickType click = event.getClick();
        InventoryType.SlotType slotType = event.getSlotType();

        ItemStack cursorItemStack = event.getCursor();
        ItemStack currentItemStack = event.getCurrentItem();

        int slot = event.getSlot();
        int rawSlot = event.getRawSlot();


        if (action == InventoryAction.NOTHING) {
            return;
        }

        if (click == ClickType.SHIFT_LEFT || click == ClickType.SHIFT_RIGHT) {
            shift = true;
        }

        if (click == ClickType.NUMBER_KEY) {
            numberkey = true;
        }

        GTM.sendConsoleMessage("========================================================");
        GTM.sendConsoleMessage(ChatColor.GREEN + "INVENTORY CLICK EVENT");
        GTM.sendConsoleMessage(ChatColor.GREEN + "SHIFT: " + shift);
        GTM.sendConsoleMessage(ChatColor.GREEN + "NUMBERKEY: " + numberkey);
        GTM.sendConsoleMessage(ChatColor.GREEN + "ACTION: " + action);
        GTM.sendConsoleMessage(ChatColor.GREEN + "CLICK: " + click);
        GTM.sendConsoleMessage(ChatColor.GREEN + "SLOTTYPE: " + slotType);
        GTM.sendConsoleMessage("========================================================");

        if ((slotType != InventoryType.SlotType.ARMOR) && (slotType != InventoryType.SlotType.QUICKBAR) && (slotType != InventoryType.SlotType.CONTAINER)) {
            return;
        }

        if ((clickedInventory != null) && (clickedInventory.getType() != InventoryType.PLAYER)) {
            return;
        }

        if ((inventory.getType() == InventoryType.CRAFTING) && (inventory.getType() != InventoryType.PLAYER)) {
            return;
        }

        if (!(whoClicked instanceof Player)) {
            return;
        }

        ArmorType newArmorType = ArmorType.matchType(shift ? currentItemStack : cursorItemStack);

        // Used for drag and drop checking to make sure you aren't trying to place a helmet in the boots slot.
        if ((!shift) && (newArmorType != null) && (rawSlot != newArmorType.getSlot())) {
            return;
        }
        if (shift) {

            newArmorType = ArmorType.matchType(currentItemStack);

            if (newArmorType != null) {
                boolean equipping = true;
                if (rawSlot == newArmorType.getSlot()) {
                    equipping = false;
                }
                if (newArmorType.equals(ArmorType.HELMET) && (equipping == isAirOrNull(whoClicked.getInventory().getHelmet()))
                        || newArmorType.equals(ArmorType.CHESTPLATE) && (equipping == isAirOrNull(whoClicked.getInventory().getChestplate()))
                        || newArmorType.equals(ArmorType.LEGGINGS) && (equipping == isAirOrNull(whoClicked.getInventory().getLeggings()))
                        || newArmorType.equals(ArmorType.BOOTS) && (equipping == isAirOrNull(whoClicked.getInventory().getBoots())))

                {
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) whoClicked, ArmorEquipEvent.EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : currentItemStack, equipping ? currentItemStack : null);
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        event.setCancelled(true);
                    }
                }
            }
        } else {
            ItemStack newArmorPiece = event.getCursor();
            ItemStack oldArmorPiece = event.getCurrentItem();
            if (numberkey) {
                if (event.getClickedInventory().getType().equals(InventoryType.PLAYER)) {// Prevents shit in the 2by2 crafting
                    // e.getClickedInventory() == The players inventory
                    // e.getHotBarButton() == key people are pressing to equip or unequip the item to or from.
                    // e.getRawSlot() == The slot the item is going to.
                    // e.getSlot() == Armor slot, can't use e.getRawSlot() as that gives a hotbar slot ;-;
                    ItemStack hotbarItem = event.getClickedInventory().getItem(event.getHotbarButton());
                    if (!isAirOrNull(hotbarItem)) {// Equipping
                        newArmorType = ArmorType.matchType(hotbarItem);
                        newArmorPiece = hotbarItem;
                        oldArmorPiece = event.getClickedInventory().getItem(event.getSlot());
                    } else {// Unequipping
                        newArmorType = ArmorType.matchType(!isAirOrNull(event.getCurrentItem()) ? event.getCurrentItem() : event.getCursor());
                    }
                }
            } else {
                if (isAirOrNull(event.getCursor()) && !isAirOrNull(event.getCurrentItem())) {// unequip with no new item going into the slot.
                    newArmorType = ArmorType.matchType(event.getCurrentItem());
                }
                // e.getCurrentItem() == Unequip
                // e.getCursor() == Equip
                // newArmorType = ArmorType.matchType(!isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
            }
            if (newArmorType != null && event.getRawSlot() == newArmorType.getSlot()) {
                ArmorEquipEvent.EquipMethod method = ArmorEquipEvent.EquipMethod.PICK_DROP;
                if (event.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey)
                    method = ArmorEquipEvent.EquipMethod.HOTBAR_SWAP;
                ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.useItemInHand().equals(Event.Result.DENY)) return;
        //
        if (e.getAction() == Action.PHYSICAL) return;
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            if (!e.useInteractedBlock().equals(Event.Result.DENY)) {
                if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK && !player.isSneaking()) {// Having both of these checks is useless, might as well do it though.
                    // Some blocks have actions when you right click them which stops the client from equipping the armor in hand.
                    Material mat = e.getClickedBlock().getType();
                    for (String s : blockedMaterials) {
                        if (mat.name().equalsIgnoreCase(s)) return;
                    }
                }
            }
            ArmorType newArmorType = ArmorType.matchType(e.getItem());
            if (newArmorType != null) {
                if (newArmorType.equals(ArmorType.HELMET) && isAirOrNull(e.getPlayer().getInventory().getHelmet()) || newArmorType.equals(ArmorType.CHESTPLATE) && isAirOrNull(e.getPlayer().getInventory().getChestplate()) || newArmorType.equals(ArmorType.LEGGINGS) && isAirOrNull(e.getPlayer().getInventory().getLeggings()) || newArmorType.equals(ArmorType.BOOTS) && isAirOrNull(e.getPlayer().getInventory().getBoots())) {
                    ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(e.getPlayer(), ArmorEquipEvent.EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), null, e.getItem());
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                        player.updateInventory();
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void inventoryDrag(InventoryDragEvent event) {
        System.out.println("INVENTORY DRAG");
        // getType() seems to always be even.
        // Old Cursor gives the item you are equipping
        // Raw slot is the ArmorType slot
        // Can't replace armor using this method making getCursor() useless.
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (event.getRawSlots().isEmpty()) return;// Idk if this will ever happen
        if (type != null && type.getSlot() == event.getRawSlots().stream().findFirst().orElse(0)) {
            ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent((Player) event.getWhoClicked(), ArmorEquipEvent.EquipMethod.DRAG, type, null, event.getOldCursor());
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                event.setResult(Event.Result.DENY);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent event) {
        ArmorType armorType = ArmorType.matchType(event.getBrokenItem());

        if (armorType == null) {
            return;
        }

        Player player = event.getPlayer();
        ArmorEquipEvent armorEquipEvent = new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.BROKE, armorType, event.getBrokenItem(), null);
        callEvent(armorEquipEvent);

        if (!armorEquipEvent.isCancelled()) {
            return;
        }

        ItemStack itemStack = event.getBrokenItem().clone();
        itemStack.setDurability((short) (itemStack.getDurability() -1));
        PlayerInventory inventory = player.getInventory();
        inventory.setItem(armorType.getSlot(), itemStack);
    }



    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (event.getKeepInventory()) {
            return;
        }
        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (isAirOrNull(itemStack)) {
                continue;
            }
            Bukkit.getServer().getPluginManager().callEvent(new ArmorEquipEvent(player, ArmorEquipEvent.EquipMethod.DEATH, ArmorType.matchType(itemStack), itemStack, null));
        }
    }

    /**
     * A utility method to support versions that use null or air ItemStacks.
     */
    public static boolean isAirOrNull(ItemStack item) {
        return (item == null) || (item.getType() == Material.AIR) || (item.getAmount() == 0);
    }

    public void callEvent(Event event) {
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
