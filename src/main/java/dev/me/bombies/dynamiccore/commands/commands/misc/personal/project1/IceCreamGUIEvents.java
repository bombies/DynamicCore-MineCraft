package dev.me.bombies.dynamiccore.commands.commands.misc.personal.project1;

import dev.me.bombies.dynamiccore.DynamicCore;
import dev.me.bombies.dynamiccore.constants.Config;
import dev.me.bombies.dynamiccore.events.GUIEvents;
import dev.me.bombies.dynamiccore.utils.GeneralUtils;
import dev.me.bombies.dynamiccore.utils.guibuilder.ItemBuilder;
import dev.me.bombies.dynamiccore.utils.signgui.SignGUI;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class IceCreamGUIEvents implements GUIEvents {
    @EventHandler (priority = EventPriority.NORMAL)
    public void onItemMove(InventoryClickEvent e) {
        GUIEvents.super.onItemMove(e, GeneralUtils.getColoredString("&b&lIce-Cream &f&lShop"));
    }

    @Override
    @EventHandler (priority = EventPriority.NORMAL)
    public void onItemClick(InventoryClickEvent e) {
         if (!checkGUI(e, GeneralUtils.getColoredString("&b&lIce-Cream &f&lShop")))
             return;


        Material typeClicked = e.getCurrentItem().getType();
        HumanEntity player = e.getWhoClicked();

        if (typeClicked == Material.SUGAR) {
            player.closeInventory();
            openSignSearchGUI(player);
        }

    }

    private void openSignSearchGUI(HumanEntity player) {
        var signGUIFactory = DynamicCore.getSignGUI();

        SignGUI.Menu menu = signGUIFactory.newMenu(
                    Arrays.asList(
                            GeneralUtils.getColoredString("&f&lWhich ice-cream&r"),
                            GeneralUtils.getColoredString("&f&ldo you want?&r"),
                            GeneralUtils.getColoredString("&8&ovvvvvvvvv&r")
                    )
                )
                .reopenIfFail(true)
                .response((target, strings) -> {
                    String iceCream = strings[3];
                    // If the input provided isn't blank or null
                    if (iceCream.isBlank() || iceCream.isEmpty())
                        target.sendMessage(GeneralUtils.getPrefixedString("You must provide the name of an ice-cream!"));
                    // Else if the input provided is an ice cream flavour
                    else if (linearSearch(iceCream))
                        // The ice cream is generated and given to the user
                        target.getInventory().addItem(generateIceCream(iceCream, target));
                    // Else if the input provided is not an ice cream flavour
                    else
                        target.sendMessage(GeneralUtils.getPrefixedString("\"" + iceCream + "\" is not a valid flavour!"));

                    return true;
                });

        menu.open((Player) player);
    }

    private boolean linearSearch(String flavour) {
        // For each flavour in the list of ice-cream flavours
        for (IceCreamFlavours validFlavour : IceCreamFlavours.values())
            /* If the valid flavour is equal to the flavour passed
            *  that means the flavour has been found hence true is
            *  returned to signify the occurrence.
            * */
            if (validFlavour.toString().equalsIgnoreCase(flavour))
                return true;
        /* If the loop has ended before returning true
        *  that means the flavour was not found hence
        *  false will be sent to signify the occurrence
        * */
        return false;
    }


    private ItemStack generateIceCream(String flavour, Player target) {
        var f = IceCreamFlavours.parse(flavour);
        if (f == null)
            throw new NullPointerException(flavour + " is an invalid flavour!");

        ItemBuilder iceCreamBuilder = null;
        switch (f) {
            case FUDGE -> iceCreamBuilder = new ItemBuilder(Material.NETHERITE_SCRAP, "&6&lFudge &e&lIce Cream");
            case VANILLA -> iceCreamBuilder = new ItemBuilder(Material.END_ROD, "&f&lVanilla &e&lIce Cream");
            case GRAPENUT -> iceCreamBuilder = new ItemBuilder(Material.PURPLE_DYE, "&5&lGrapenut &e&lIce Cream");
            case CHOCOLATE -> iceCreamBuilder = new ItemBuilder(Material.BROWN_DYE, "&6&lChocolate &e&lIce Cream");
            case STRAWBERRY -> iceCreamBuilder = new ItemBuilder(Material.RED_DYE, "&c&lStrawberry &e&lIce Cream");
            case CHOCOLATE_CHIP -> iceCreamBuilder = new ItemBuilder(Material.CRIMSON_FUNGUS, "&6&lChocolate Chip &e&lIce Cream");
        }

        iceCreamBuilder.setLore(GeneralUtils.getColoredLore("&8&m----------------------", "&eEnjoy your ice-cream!", "&8&m----------------------"));
        iceCreamBuilder.setGlowing(true);

        target.sendTitle(
                GeneralUtils.getColoredString("&e&lIce cream received!"),
                GeneralUtils.getColoredString("&7You have received the "+iceCreamBuilder.getName()+" &7flavour!"),
                10, 60, 20
        );

        return iceCreamBuilder.build();
    }
}
